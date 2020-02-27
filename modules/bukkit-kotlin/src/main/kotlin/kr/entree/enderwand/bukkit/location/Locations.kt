package kr.entree.enderwand.bukkit.location

import kr.entree.enderwand.math.Point
import kr.entree.enderwand.math.Point3D
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

fun Location.toBlockPoint() = Point3D(
    blockX, blockY, blockZ
)

fun Location.toPoint() = Point3D(
    x, y, z
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

operator fun Location.component1() = x

operator fun Location.component2() = y

operator fun Location.component3() = z

operator fun Location.component4() = world

operator fun Location.component5() = yaw

operator fun Location.component6() = pitch