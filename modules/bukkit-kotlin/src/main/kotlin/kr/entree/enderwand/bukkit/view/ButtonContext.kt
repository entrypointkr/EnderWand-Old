package kr.entree.enderwand.bukkit.view

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
) : ViewEventContext<T> by delegate