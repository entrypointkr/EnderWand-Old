package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.event.player
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryEvent

/**
 * Created by JunHyung Lim on 2020-02-22
 */
fun <T : Dynamic> ButtonContext<T>.update() =
    view.update(event.view.topInventory)

fun <T> ButtonContext<T>.close() =
    event.player.closeInventory()

class ButtonContext<T>(
    val button: Button<T>,
    val event: InventoryEvent,
    val delegate: ViewContext<T>
) : ViewContext<T> by delegate {
    val clicker get() = event.view.player
    val player get() = clicker as Player
}