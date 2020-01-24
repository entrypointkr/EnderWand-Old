package kr.entree.enderwand.bukkit.view

import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.ItemStack

/**
 * Created by JunHyung Lim on 2020-01-14
 */
class Button<T>(
    val item: ItemStack,
    val receiver: ButtonContext<T>.() -> Unit = {}
) {
    operator fun invoke(event: InventoryEvent, source: T) {
        receiver(ButtonContext(this, event, source))
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