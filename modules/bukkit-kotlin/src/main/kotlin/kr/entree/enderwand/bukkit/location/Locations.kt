package kr.entree.enderwand.bukkit.location

import kr.entree.enderwand.math.Point
import kr.entree.enderwand.math.Point3D
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.util.Vector

/**
 * Created by JunHyung Lim on 2020-01-06
 */
object Locations {
    val entityTypeByClass = mutableMapOf<Class<out Entity>, EntityType>()
}

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

fun Location.format() = "x: $x, y: $y, z: $z, world: $world"

fun Location.highestY() = world!!.getHighestBlockYAt(this)

fun Location.highest() = Location(world, x, highestY().toDouble(), z)

fun Location.highestBlock() = highest().block

fun Location.add(x: Int, y: Int, z: Int) = add(x.toDouble(), y.toDouble(), z.toDouble())

inline fun <reified T : Entity> Location.spawnEntity(): T {
    return world!!.spawnEntity(this, Locations.entityTypeByClass.getOrPut(T::class.java) {
        EntityType.values().find { it.entityClass == T::class.java }!!
    }) as T
}

fun Vector.format() = "x: $x, y: $y, z: $z"

fun Vector.format2D() = "x: $x, z: $z"

fun Vector.serializeToString() = "$x,$y,$z"

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