package kr.entree.enderwand.bukkit.serialization

import kotlinx.serialization.*
import org.bukkit.configuration.serialization.ConfigurationSerializable

@Serializer(forClass = ConfigurationSerializable::class)
object ConfigurationSerializer : KSerializer<ConfigurationSerializable> {
    override val descriptor: SerialDescriptor = PolymorphicConfigurationSerializer.descriptor

    @OptIn(ImplicitReflectionSerializer::class)
    override fun serialize(encoder: Encoder, value: ConfigurationSerializable) {
        encoder.encode(PolymorphicConfigurationSerializer, value)
    }

    override fun deserialize(decoder: Decoder): ConfigurationSerializable {
        return decoder.decode(PolymorphicConfigurationSerializer) as ConfigurationSerializable
    }
}