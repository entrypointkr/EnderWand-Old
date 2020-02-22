package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.event.player
import org.bukkit.event.inventory.InventoryEvent

/**
 * Created by JunHyung Lim on 2020-02-23
 */
fun <T : DynamicView> ViewEventContext<T>.update() =
    view.update(event.view.topInventory)

fun <T : View> ViewEventContext<T>.close() =
    event.player.closeInventory()

interface ViewEventContext<T : View> : ViewContext<T> {
    val event: InventoryEvent
    val delegate: ViewContext<T>
}

class ViewEventContextImpl<T : View>(
    override val event: InventoryEvent,
    override val delegate: ViewContext<T>
) : ViewEventContext<T>, ViewContext<T> by delegate