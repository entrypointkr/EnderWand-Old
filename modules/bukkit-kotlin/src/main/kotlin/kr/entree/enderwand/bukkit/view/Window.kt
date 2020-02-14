package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.event.cancelViolationClick
import kr.entree.enderwand.bukkit.event.isNotDoubleClick
import kr.entree.enderwand.bukkit.inventory.inventory
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.Inventory

/**
 * Created by JunHyung Lim on 2020-01-25
 */
fun window(
    title: String,
    row: Int,
    configure: Window.() -> Unit
) = Window(title, row, mutableMapOf(), configure)

class Window(
    val title: String,
    val row: Int,
    val buttons: MutableMap<Int, Button<Window>>,
    val configure: Window.() -> Unit
) : DynamicView, ButtonMap<Window>, MutableMap<Int, Button<Window>> by buttons {
    override val slots get() = row * 9

    override fun create() = inventory(title, row) { update(this) }

    override fun onEvent(e: InventoryEvent) {
        e.cancelViolationClick()
        if (e is InventoryClickEvent && e.isNotDoubleClick) {
            buttons[e.rawSlot]?.invokeLater(e, this)
        }
    }

    override fun update(inventory: Inventory) {
        buttons.clear()
        configure()
        for (i in 0 until inventory.size) {
            inventory.setItem(i, buttons[i]?.item?.invoke())
        }
    }

    override infix fun Int.slotOf(button: Button<Window>) {
        buttons[this] = button
    }
}