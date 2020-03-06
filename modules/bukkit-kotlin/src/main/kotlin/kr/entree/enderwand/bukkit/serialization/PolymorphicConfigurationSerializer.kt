package kr.entree.enderwand.bukkit.serialization

import kotlinx.serialization.*
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization

@OptIn(InternalSerializationApi::class)
object PolymorphicConfigurationSerializer : KSerializer<Any> {
    val delegate = PolymorphicSerializer(Any::class)
    override val descriptor = delegate.descriptor
    val map = MapSerializer(String.serializer(), PolymorphicConfigurationSerializer)

    override fun serialize(encoder: Encoder, value: Any) {
        if (value is ConfigurationSerializable) {
            val compositeEncoder = encoder.beginStructure(descriptor)
            compositeEncoder.encodeStringElement(descriptor, 0, ConfigurationSerialization.getAlias(value.javaClass))
            compositeEncoder.encodeSerializableElement(descriptor, 1, map, value.serialize())
            compositeEncoder.endStructure(descriptor)
        } else delegate.serialize(encoder, value)
    }

    fun findPolymorphicDeserializer(
        decoder: CompositeDecoder,
        klassName: String
    ): DeserializationStrategy<out Any> {
        val configurationClass = ConfigurationSerialization.getClassByAlias(klassName)
        return if (configurationClass != null) {
            ConfigurationDeserializer(configurationClass)
        } else delegate.findPolymorphicSerializer(decoder, klassName)
    }

    override fun deserialize(decoder: Decoder): Any {
        val compositeDecoder = decoder.beginStructure(descriptor)
        var klassName: String? = null
        var value: Any? = null
        if (compositeDecoder.decodeSequentially()) {
            klassName = compositeDecoder.decodeStringElement(descriptor, 0)
            val serializer = findPolymorphicDeserializer(compositeDecoder, klassName)
            value = compositeDecoder.decodeSerializableElement(descriptor, 1, serializer)
        } else {
            mainLoop@ while (true) {
                when (val index = compositeDecoder.decodeElementIndex(descriptor)) {
                    CompositeDecoder.READ_DONE -> {
                        break@mainLoop
                    }
                    0 -> {
                        klassName = compositeDecoder.decodeStringElement(descriptor, index)
                    }
                    1 -> {
                        klassName = requireNotNull(klassName) { "Cannot read polymorphic value before its type token" }
                        val serializer = findPolymorphicDeserializer(compositeDecoder, klassName)
                        value = compositeDecoder.decodeSerializableElement(descriptor, index, serializer)
                    }
                    else -> throw SerializationException(
                        "Invalid index in polymorphic deserialization of " +
                                (klassName ?: "unknown class") +
                                "\n Expected 0, 1 or READ_DONE(-1), but found $index"
                    )
                }
            }
        }

        compositeDecoder.endStructure(descriptor)
        @Suppress("UNCHECKED_CAST")
        return requireNotNull(value) { "Polymorphic value has not been read for class $klassName" }
    }
}