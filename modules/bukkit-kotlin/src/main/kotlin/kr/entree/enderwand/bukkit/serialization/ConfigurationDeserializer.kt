package kr.entree.enderwand.bukkit.serialization

import kotlinx.serialization.*
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization

class ConfigurationDeserializer<T : ConfigurationSerializable>(
    val type: Class<T>
) : DeserializationStrategy<ConfigurationSerializable> {
    override val descriptor: SerialDescriptor = SerialDescriptor("ConfigurationSerializable", StructureKind.OBJECT)

    override fun deserialize(decoder: Decoder): ConfigurationSerializable {
        val map = PolymorphicConfigurationSerializer.map.deserialize(decoder)
        return ConfigurationSerialization.deserializeObject(map, type) as ConfigurationSerializable
    }

    override fun patch(decoder: Decoder, old: ConfigurationSerializable): ConfigurationSerializable {
        throw UpdateNotSupportedException(descriptor.serialName)
    }
}