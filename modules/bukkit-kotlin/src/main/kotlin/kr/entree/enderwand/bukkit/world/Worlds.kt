package kr.entree.enderwand.bukkit.world

import org.bukkit.World

/**
 * Created by JunHyung Lim on 2020-03-15
 */
val World.isNether get() = environment == World.Environment.NETHER

val World.isNormal get() = environment == World.Environment.NORMAL

val World.isEnder get() = environment == World.Environment.THE_END