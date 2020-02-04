package kr.entree.enderwand.bukkit.location

import kr.entree.enderwand.math.Point
import org.bukkit.Location
import org.bukkit.util.Vector
import java.text.MessageFormat

/**
 * Created by JunHyung Lim on 2020-01-06
 */
fun Location.toBlockLocation(): Location {
    toVector().toBlockVector()
    return Location(
        world,
        blockX.toDouble(),
        blockZ.toDouble(),
        blockY.toDouble()
    )
}

fun Location.toBlockVector() = Vector(
    blockX,
    blockY,
    blockZ
)

fun Vector.toChunkPoint() = Point(
    blockX shr 4,
    blockZ shr 4
)

fun Location.toChunkPoint() = toVector().toChunkPoint()

fun Vector.format(format: String = "x: {0}, y: {1}, z: {2}") =
    MessageFormat.format(format, x, y, z)

fun Vector.format2D() = format("x: {0}, z: {2}")

fun Vector.serializeToString() = format("{0},{1},{2}")

inline fun String.toVector(mapper: (String) -> Double = { it.toDouble() }) =
    split(",").run {
        Vector(mapper(get(0)), mapper(get(1)), mapper(get(2)))
    }