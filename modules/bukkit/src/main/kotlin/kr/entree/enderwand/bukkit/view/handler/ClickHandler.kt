package kr.entree.enderwand.bukkit.view.handler

import kr.entree.enderwand.bukkit.view.ViewHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryEvent

/**
 * Created by JunHyung Lim on 2020-01-04
 */
class ClickHandler(
    private val handlers: MutableMap<Int, (InventoryEvent) -> Unit> = mutableMapOf()
) : ViewHandler {
    fun handle(slot: Int, e: InventoryEvent) = handlers[slot]?.invoke(e)

    fun put(slot: Int, handler: (InventoryEvent) -> Unit) = handlers.put(slot, handler)

    override fun onEvent(e: InventoryEvent) {
        if (e is InventoryClickEvent) {
            handle(e.rawSlot, e)
        } else if (e is InventoryDragEvent) {
            e.rawSlots.forEach { handle(it, e) }
        }
    }
}