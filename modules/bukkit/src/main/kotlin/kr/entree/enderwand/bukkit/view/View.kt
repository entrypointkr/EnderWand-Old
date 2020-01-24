package kr.entree.enderwand.bukkit.view

import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.Inventory

/**
 * Created by JunHyung Lim on 2020-01-06
 */
fun viewOf(handler: (InventoryEvent) -> Unit, factory: () -> Inventory) =
    View(handler, factory)

fun viewOf(handler: (InventoryEvent) -> Unit, inventory: Inventory) =
    viewOf(handler) { inventory }

interface ViewFactory {
    fun create(): Inventory
}

interface ViewHandler {
    fun onEvent(e: InventoryEvent)
}

interface ViewUpdater {
    fun update(inventory: Inventory)
}

interface ViewComponent : ViewFactory, ViewHandler

interface DynamicView : ViewComponent, ViewUpdater

class View(
    private val handler: (InventoryEvent) -> Unit,
    private val factory: () -> Inventory
) : ViewComponent {
    override fun onEvent(e: InventoryEvent) = handler(e)

    override fun create() = factory()
}