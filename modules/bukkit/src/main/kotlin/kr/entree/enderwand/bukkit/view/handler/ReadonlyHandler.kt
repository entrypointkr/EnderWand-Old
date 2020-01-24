package kr.entree.enderwand.bukkit.view.handler

import kr.entree.enderwand.bukkit.event.hasTopSlot
import kr.entree.enderwand.bukkit.event.isTopClick
import kr.entree.enderwand.bukkit.view.ViewHandler
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryEvent

/**
 * Created by JunHyung Lim on 2020-01-04
 */
fun ViewHandler.readonly() = ReadonlyHandler(this::onEvent)

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

class ReadonlyHandler(
    private val handler: (InventoryEvent) -> Unit
) : ViewHandler {
    override fun onEvent(e: InventoryEvent) {
        if (!e.cancelViolationClick()) {
            handler(e)
        }
    }
}