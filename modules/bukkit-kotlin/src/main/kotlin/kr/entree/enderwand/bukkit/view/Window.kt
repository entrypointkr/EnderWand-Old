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
    configure: Window.() -> Unit = {}
) = Window(title, row, mutableMapOf()).apply(configure)

class Window(
    val title: String,
    val row: Int,
    val buttons: MutableMap<Int, Button<Window>>
) : DynamicView, ButtonMap<Window>, MutableMap<Int, Button<Window>> by buttons {
    override val size get() = row * 9
    override val context = ViewContextImpl(this)
    var handler: ViewEventContext<Window>.(InventoryEvent) -> Unit = {}

    override fun create() = inventory(title, row) { update(this) }

    override fun handle(event: InventoryEvent) {
        event.cancelViolationClick()
        if (event is InventoryClickEvent && event.isNotDoubleClick) {
            buttons[event.rawSlot]?.invokeLater(event, context)
        }
        handler(ViewEventContextImpl(event, ViewContextImpl(this)), event)
    }

    override fun update(inventory: Inventory) {
        for (i in 0 until inventory.size) {
            inventory.setItem(i, buttons[i]?.item?.invoke(context))
        }
    }

    fun onEvent(handler: ViewEventContext<Window>.(InventoryEvent) -> Unit): Window {
        this.handler = handler
        return this
    }

    override fun Button<Window>.at(slot: Int) = buttons.put(slot, this)
}