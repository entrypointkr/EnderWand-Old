package kr.entree.enderwand.bukkit.view

import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.Inventory

/**
 * Created by JunHyung Lim on 2020-01-06
 */
interface View {
    val context: ViewContext<out View>

    fun create(): Inventory

    fun handle(event: InventoryEvent)
}

interface DynamicView : View {
    override val context: ViewContext<out DynamicView>

    fun update(inventory: Inventory)
}