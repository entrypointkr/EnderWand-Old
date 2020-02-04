package kr.entree.enderwand.bukkit.view

import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.Inventory

/**
 * Created by JunHyung Lim on 2020-01-06
 */
interface View {
    fun create(): Inventory

    fun onEvent(e: InventoryEvent)
}

interface ViewFlexible {
    fun update(inventory: Inventory)
}

interface DynamicView : View, ViewFlexible