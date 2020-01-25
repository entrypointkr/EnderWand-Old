package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.bukkit.scheduler.scheduler
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.ItemStack

/**
 * Created by JunHyung Lim on 2020-01-14
 */
class ButtonMapBuilder<T>(
    val map: MutableMap<Int, Button<T>> = mutableMapOf()
) {
    fun button(vararg slots: Int, item: () -> ItemStack) =
        Button<T>(item).also { button -> slots.forEach { map[it] = button } }

    fun button(slots: Collection<Int>, item: () -> ItemStack) =
        Button<T>(item).also { btn -> slots.forEach { map[it] = btn } }
}

fun <T> button(item: () -> ItemStack) = Button<T>(item)

class Button<T>(
    val item: () -> ItemStack,
    var click: ButtonContext<T>.() -> Unit = {}
) {
    operator fun invoke(event: InventoryEvent, source: T) {
        click(ButtonContext(this, event, source))
    }

    fun invokeLater(event: InventoryEvent, source: T) {
        enderWand.scheduler { invoke(event, source) }
    }

    fun onClick(click: ButtonContext<T>.() -> Unit): Button<T> {
        this.click = click
        return this
    }
}

class ButtonContext<T>(
    val button: Button<T>,
    event: InventoryEvent,
    source: T
) : ViewContext<T>(
    event,
    source
)