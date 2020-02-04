package enderwand.bukkit.view

import enderwand.bukkit.event.cancelViolationClick
import enderwand.bukkit.event.isNotDoubleClick
import enderwand.bukkit.inventory.inventory
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.Inventory

/**
 * Created by JunHyung Lim on 2020-01-25
 */
fun window(
    title: String,
    row: Int,
    configure: ButtonMapBuilder<Window>.() -> Unit
) = Window(title, row, ButtonMapBuilder<Window>().apply(configure).map)

class Window(
    val title: String,
    val row: Int,
    val buttons: Map<Int, Button<Window>>
) : DynamicView {
    override fun create() = inventory(title, row) { update(this) }

    override fun onEvent(e: InventoryEvent) {
        e.cancelViolationClick()
        if (e is InventoryClickEvent && e.isNotDoubleClick) {
            buttons[e.rawSlot]?.invokeLater(e, this)
        }
    }

    override fun update(inventory: Inventory) {
        for (i in 0 until inventory.size) {
            inventory.setItem(i, buttons[i]?.item?.invoke())
        }
    }
}