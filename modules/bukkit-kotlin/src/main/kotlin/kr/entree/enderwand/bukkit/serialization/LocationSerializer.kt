package kr.entree.enderwand.bukkit.serialization

import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kr.entree.enderwand.bukkit.location.*
import org.bukkit.Bukkit
import org.bukkit.Location

@Serializer(forClass = Location::class)
object LocationSerializer : KSerializer<Location> {
    override val descriptor: SerialDescriptor =
        SerialDescriptor("org.bukkit.Location", StructureKind.OBJECT) {
            val doubleDescriptor = Double.serializer().descriptor
            val floatDescriptor = Float.serializer().descriptor
            element("x", doubleDescriptor)
            element("y", doubleDescriptor)
            element("z", doubleDescriptor)
            element("world", String.serializer().descriptor)
            element("yaw", floatDescriptor)
            element("pitch", floatDescriptor)
        }

    override fun serialize(encoder: Encoder, value: Location) {
        val (x, y, z, world, yaw, pitch) = value
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
            doLoopIndexes { index ->
                when (index) {
                    0 -> old.x = decodeDouble(index)
                    1 -> old.y = decodeDouble(index)
                    2 -> old.z = decodeDouble(index)
                    3 -> old.world = decodeString(index).run {
                        Bukkit.getWorld(this)
                    }
                    4 -> old.yaw = decodeFloat(index)
                    5 -> old.pitch = decodeFloat(index)
                }
            }
        }
        return old
    }
}