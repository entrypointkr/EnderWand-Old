package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.bukkit.scheduler.scheduler
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.ItemStack

fun <T> button(item: () -> ItemStack) = Button<T>(item)

class Button<T>(
    var item: () -> ItemStack,
    var click: ButtonContext<T>.() -> Unit = {}
) {
    val metadata: MutableMap<String, Any> by lazy { mutableMapOf<String, Any>() }

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
    override val event: InventoryEvent,
    override val source: T
) : ViewContext<T>