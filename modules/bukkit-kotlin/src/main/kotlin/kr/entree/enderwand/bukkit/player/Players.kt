package kr.entree.enderwand.bukkit.player

import kr.entree.enderwand.bukkit.command.BukkitSender
import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.bukkit.exception.UnknownPlayerException
import kr.entree.enderwand.bukkit.inventory.giveItemOrDrop
import kr.entree.enderwand.bukkit.inventory.takeItem
import kr.entree.enderwand.bukkit.item.emptyItem
import kr.entree.enderwand.bukkit.item.isAir
import kr.entree.enderwand.bukkit.item.isNotAir
import kr.entree.enderwand.bukkit.location.WorldPoint
import kr.entree.enderwand.bukkit.scheduler.scheduler
import kr.entree.enderwand.scheduler.Scheduler
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import java.util.*

/**
 * Created by JunHyung Lim on 2019-12-31
 */
fun String.toPlayer() = Bukkit.getPlayer(this)

@Suppress("DEPRECATION")
fun String.toOfflinePlayer() =
    runCatching {
        UUID.fromString(this)
    }.getOrNull()?.toOfflinePlayer() ?: Bukkit.getOfflinePlayer(this)

fun UUID.toPlayer() = Bukkit.getPlayer(this)

fun UUID.toOfflinePlayer() = Bukkit.getOfflinePlayer(this)

fun UUID.playerName(defaultName: String = "???") = toOfflinePlayer().name ?: defaultName

fun CommandSender.toPlayer() = this as? Player

fun CommandSender.toPlayerOrThrow() = toPlayer() ?: throw UnknownPlayerException(name)

fun BukkitSender.toPlayerOrThrow() = player ?: throw UnknownPlayerException(name)

val HumanEntity.itemOnHand get() = inventory.itemInMainHand

val HumanEntity.itemOnHandNotAir get() = itemOnHand.takeIf { it.isNotAir() }

fun HumanEntity.ensureCursorItem(scheduler: Scheduler = enderWand.scheduler) {
    val cursorItem = openInventory.cursor ?: emptyItem()
    if (cursorItem.isNotAir()) {
        openInventory.cursor = null
        inventory.giveItemOrDrop(cursorItem.clone())
        scheduler {
            openInventory.takeIf {
                it.topInventory.type != InventoryType.CRAFTING
            }?.apply {
                val cursorItemCurrent = cursor
                if (cursorItemCurrent.isAir() && inventory.takeItem(cursorItem).success) {
                    cursor = cursorItem
                }
            }
        }
    }
}

fun <T> Entity.teleport(worldPoint: WorldPoint<T>) where T : Number, T : Comparable<T> =
    teleport(worldPoint.toLocation(location))