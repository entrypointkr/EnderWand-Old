package kr.entree.enderwand.bukkit.location

import kr.entree.enderwand.bukkit.exception.Minecraft
import org.bukkit.Location

open class NotIdenticalWorldException(
    val locA: Location,
    val locB: Location
) : IllegalArgumentException(), Minecraft