package kr.entree.enderwand.bukkit.event

import org.bukkit.event.Cancellable
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.EquipmentSlot

/**
 * Created by JunHyung Lim on 2020-01-06
 */
val PlayerMoveEvent.isWalked: Boolean
    get() {
        val toLoc = to
        return toLoc != null
                && (from.blockX != toLoc.blockX
                || from.blockY != toLoc.blockY
                || from.blockZ != toLoc.blockZ)
    }

val PlayerInteractEvent.isMainHand
    get() = hand == EquipmentSlot.HAND

val PlayerInteractEvent.isLeft
    get() = action == Action.LEFT_CLICK_AIR
            || action == Action.LEFT_CLICK_BLOCK

val PlayerInteractEvent.isRight
    get() = action == Action.RIGHT_CLICK_AIR
            || action == Action.RIGHT_CLICK_BLOCK

val InventoryClickEvent.isTopClick
    get() = rawSlot in 0 until view.topInventory.size

//val InventoryClickEvent.isBottomClick
//    get() = TODO()

val InventoryDragEvent.hasTopSlot
    get() = rawSlots.any { it in 0 until view.topInventory.size }

val InventoryEvent.player get() = view.player

inline val Cancellable.isNotCancelled get() = !isCancelled