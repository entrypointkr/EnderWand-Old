package kr.entree.enderwand.bukkit.location

import kr.entree.enderwand.math.rangeTo
import org.bukkit.Location

operator fun Location.rangeTo(other: Location) =
    if (world != other.world)
        throw NotIdenticalWorldException(this, other)
    else
        toBlockPoint()..other.toBlockPoint()

fun regionOfNullable(from: Location, to: Location) = runCatching { from..to }.getOrNull()