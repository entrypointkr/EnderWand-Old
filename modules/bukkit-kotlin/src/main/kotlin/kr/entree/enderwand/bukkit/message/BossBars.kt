package kr.entree.enderwand.bukkit.message

import org.bukkit.boss.BossBar
import org.bukkit.entity.Player

/**
 * Created by JunHyung Lim on 2020-02-20
 */
operator fun BossBar.plusAssign(player: Player) = addPlayer(player)