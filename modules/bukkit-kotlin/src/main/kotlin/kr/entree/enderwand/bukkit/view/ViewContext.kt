package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.event.player
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryEvent

/**
 * Created by JunHyung Lim on 2020-01-14
 */
fun <T : Dynamic> ViewContext<T>.update() =
    source.update(event.view.topInventory)

fun <T : View> ViewContext<T>.close() =
    event.player.closeInventory()

interface ViewContext<T> {
    val event: InventoryEvent
    val source: T
    val clicker get() = event.view.player
    val player get() = clicker as Player
}