package kr.entree.enderwand.bukkit.entity

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Arrow
import org.bukkit.entity.Entity

/**
 * Created by JunHyung Lim on 2020-02-02
 */
val Entity.orShooter
    get() = if (this is Arrow && shooter is Entity) {
        shooter as Entity
    } else this

fun Entity.teleportToSpawn(world: World = Bukkit.getWorlds()[0]) = teleport(world.spawnLocation)