package kr.entree.enderwand.bukkit.serialization

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import org.bukkit.configuration.serialization.ConfigurationSerializable

@Serializer(forClass = ConfigurationSerializable::class)
object ConfigurationSerializer : KSerializer<ConfigurationSerializable> {
    override val descriptor: SerialDescriptor = ConfigurationDescriptor

    @UseExperimental(ImplicitReflectionSerializer::class)
    override fun serialize(encoder: Encoder, obj: ConfigurationSerializable) {
        encoder.encode(PolymorphicConfigurationSerializer, obj)
    }

    override fun deserialize(decoder: Decoder): ConfigurationSerializable {
        return decoder.decode(PolymorphicConfigurationSerializer) as ConfigurationSerializable
    }
}

object ConfigurationDescriptor : SerialDescriptor {
    override val kind: SerialKind = StructureKind.MAP
    override val name: String = "ConfigurationSerializable"

    override fun getElementIndex(name: String) =
        name.toIntOrNull() ?: throw IllegalArgumentException("$name is not a valid map index")

    override fun getElementName(index: Int) = index.toString()

    override fun getElementDescriptor(index: Int) = if (index % 2 == 0) StringDescriptor else PolymorphicClassDescriptor
}