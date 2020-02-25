package kr.entree.enderwand.bukkit.location

import kr.entree.enderwand.math.Point3D
import org.bukkit.Location
import org.bukkit.World

fun <T> Point3D<T>.toLocation(world: World) where T : Number, T : Comparable<T> =
    Location(world, x.toDouble(), y.toDouble(), z.toDouble())