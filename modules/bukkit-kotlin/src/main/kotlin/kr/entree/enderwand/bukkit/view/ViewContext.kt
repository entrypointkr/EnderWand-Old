package kr.entree.enderwand.bukkit.view

import org.bukkit.event.inventory.InventoryEvent

/**
 * Created by JunHyung Lim on 2020-01-14
 */
fun <T : ViewFlexible> ViewContext<T>.update() =
    source.update(event.view.topInventory)

interface ViewContext<T> {
    val event: InventoryEvent
    val source: T
    val clicker get() = event.view.player
}