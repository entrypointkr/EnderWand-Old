package kr.entree.enderwand.bukkit.view

import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.Inventory

/**
 * Created by JunHyung Lim on 2020-01-06
 */
interface View {
    fun create(): Inventory

    fun handle(e: InventoryEvent)
}

interface Dynamic {
    fun update(inventory: Inventory)
}

interface Closable<T> {
    var closeHandler: (InventoryCloseEvent) -> Unit
    val instance: T

    fun onClose(handler: (InventoryCloseEvent) -> Unit): T {
        closeHandler = handler
        return instance
    }
}

interface DynamicView<T> : View, Dynamic, Closable<T>