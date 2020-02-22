package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.bukkit.scheduler.scheduler
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.ItemStack

fun <T> button(item: ViewContext<T>.() -> ItemStack) = Button(item)

class Button<T>(
    var item: ViewContext<T>.() -> ItemStack,
    var click: ButtonContext<T>.() -> Unit = {}
) {
    val metadata: MutableMap<String, Any> by lazy { mutableMapOf<String, Any>() }

    operator fun invoke(event: InventoryEvent, viewCtx: ViewContext<T>) {
        click(ButtonContext(this, event, viewCtx))
    }

    fun item(view: T) {
        item(ViewContextImpl(view))
    }

    fun invokeLater(event: InventoryEvent, viewCtx: ViewContext<T>) {
        enderWand.scheduler { click(ButtonContext(this, event, viewCtx)) }
    }

    fun onClick(click: ButtonContext<T>.() -> Unit): Button<T> {
        this.click = click
        return this
    }
}