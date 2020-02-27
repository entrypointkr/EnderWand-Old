@file:Suppress("UNCHECKED_CAST")

package kr.entree.enderwand.bukkit.serialization

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import org.bukkit.configuration.serialization.ConfigurationSerializable

abstract class TypedConfigurationSerializer<T : ConfigurationSerializable> : KSerializer<T> {
    override fun deserialize(decoder: Decoder): T {
        return ConfigurationSerializer.deserialize(decoder) as T
    }

    override fun serialize(encoder: Encoder, obj: T) {
        ConfigurationSerializer.serialize(encoder, obj)
    }
}