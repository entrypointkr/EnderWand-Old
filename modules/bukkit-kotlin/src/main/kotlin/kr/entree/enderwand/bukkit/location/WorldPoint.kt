package kr.entree.enderwand.bukkit.location

import kr.entree.enderwand.math.Point3D
import org.bukkit.Location
import org.bukkit.World

/**
 * Created by JunHyung Lim on 2020-03-16
 */
fun Location.toWorldPoint() = WorldPoint(world!!, toPoint())

fun Location.toBlockWorldPoint() = WorldPoint(world!!, toBlockPoint())

data class WorldPoint<T>(
    val world: World,
    val point: Point3D<T>
) where T : Number, T : Comparable<T> {
    fun toLocation() = point.toLocation(world)

    fun toLocation(base: Location) = toLocation().apply {
        yaw = base.yaw
        pitch = base.pitch
    }
}