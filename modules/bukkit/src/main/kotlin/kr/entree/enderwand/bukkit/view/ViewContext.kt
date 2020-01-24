package kr.entree.enderwand.bukkit.view

import org.bukkit.event.inventory.InventoryEvent

/**
 * Created by JunHyung Lim on 2020-01-14
 */
fun <T : ViewUpdater> ViewContext<T>.update() =
    source.update(event.view.topInventory)

open class ViewContext<T>(
    val event: InventoryEvent,
    val source: T
)