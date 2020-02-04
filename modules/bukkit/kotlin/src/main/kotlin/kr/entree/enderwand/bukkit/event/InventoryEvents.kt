package kr.entree.enderwand.bukkit.event

import org.bukkit.event.inventory.*

/**
 * Created by JunHyung Lim on 2020-01-25
 */
val InventoryClickEvent.isTopClick
    get() = rawSlot in 0 until view.topInventory.size

val InventoryClickEvent.isDoubleClick
    get() = click == ClickType.DOUBLE_CLICK

val InventoryClickEvent.isNotDoubleClick get() = !isDoubleClick

//val InventoryClickEvent.isBottomClick
//    get() = TODO()

val InventoryDragEvent.hasTopSlot
    get() = rawSlots.any { it in 0 until view.topInventory.size }

val InventoryEvent.player get() = view.player

fun InventoryEvent.cancelViolationClick(): Boolean {
    if (this is InventoryClickEvent) {
        if (action == InventoryAction.NOTHING
            || action == InventoryAction.MOVE_TO_OTHER_INVENTORY
            || action == InventoryAction.COLLECT_TO_CURSOR
            || isTopClick
        ) {
            isCancelled = true
            return true
        }
    } else if (this is InventoryDragEvent) {
        if (hasTopSlot) {
            isCancelled = true
            return true
        }
    }
    return false
}