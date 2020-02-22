package kr.entree.enderwand.bukkit.view

import org.bukkit.entity.Player

/**
 * Created by JunHyung Lim on 2020-02-22
 */
fun ButtonContext<Paginator>.remove() {
    view.buttons.remove(button)
    update()
}

class ButtonContext<T : View>(
    val button: Button<T>,
    override val delegate: ViewEventContext<T>
) : ViewEventContext<T> by delegate {
    val clicker get() = event.view.player
    val player get() = clicker as Player
}