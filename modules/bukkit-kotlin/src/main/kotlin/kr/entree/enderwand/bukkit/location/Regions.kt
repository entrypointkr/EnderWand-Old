package kr.entree.enderwand.bukkit.location

import kr.entree.enderwand.math.Region3D
import org.bukkit.Location

fun regionOf(from: Location, to: Location): Region3D<Int> {
    if (from.world != to.world) throw NotIdenticalWorldException(from, to)
    return Region3D(from.toBlockPoint(), to.toBlockPoint())
}

fun regionOfNullable(from: Location, to: Location) = runCatching { regionOf(from, to) }.getOrNull()