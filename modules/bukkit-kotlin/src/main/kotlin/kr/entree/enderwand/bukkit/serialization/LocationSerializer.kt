package kr.entree.enderwand.bukkit.serialization

import kotlinx.serialization.*
import kotlinx.serialization.internal.SerialClassDescImpl
import kr.entree.enderwand.bukkit.location.*
import org.bukkit.Bukkit
import org.bukkit.Location

@Serializer(forClass = Location::class)
object LocationSerializer : KSerializer<Location> {
    override val descriptor: SerialDescriptor = LocationDescriptor

    override fun serialize(encoder: Encoder, obj: Location) {
        val (x, y, z, world, yaw, pitch) = obj
        encoder.beginStructureDescriptive(descriptor) {
            encodeDouble(0, x)
            encodeDouble(1, y)
            encodeDouble(2, z)
            if (world != null)
                encodeString(3, world.name)
            if (yaw != 0F)
                encodeFloat(4, yaw)
            if (pitch != 0F)
                encodeFloat(5, pitch)
        }
    }

    override fun deserialize(decoder: Decoder): Location {
        val loc = Location(null, 0.0, 0.0, 0.0)
        patch(decoder, loc)
        return loc
    }

    override fun patch(decoder: Decoder, old: Location): Location {
        decoder.beginStructureDescriptive(descriptor) {
                old.x = decodeElement(0).toDouble()
            old.y = decodeElement(1).toDouble()
            old.z = decodeElement(2).toDouble()
            old.world = decodeNullableElement(3)?.run {
                Bukkit.getWorld(this)
            }
            old.yaw = decodeNullableElement(4)?.toFloat() ?: 0F
            old.pitch = decodeNullableElement(5)?.toFloat() ?: 0F
        }
        return old
    }
}

object LocationDescriptor : SerialClassDescImpl("Location") {
    init {
        addElement("x")
        addElement("y")
        addElement("z")
        addElement("world", true)
        addElement("yaw", true)
        addElement("pitch", true)
    }
}