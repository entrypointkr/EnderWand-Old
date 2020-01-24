package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.bukkit.inventory.fill
import kr.entree.enderwand.bukkit.inventory.inventory
import kr.entree.enderwand.bukkit.inventory.slot
import kr.entree.enderwand.bukkit.item.item
import kr.entree.enderwand.bukkit.item.meta
import kr.entree.enderwand.bukkit.item.setName
import kr.entree.enderwand.bukkit.scheduler.scheduler
import kr.entree.enderwand.bukkit.view.handler.cancelViolationClick
import org.bukkit.Material
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * Created by JunHyung Lim on 2020-01-20
 */
private val BUTTONS = listOf(
    Material.OAK_BUTTON, Material.ACACIA_BUTTON, Material.BIRCH_BUTTON,
    Material.DARK_OAK_BUTTON, Material.JUNGLE_BUTTON, Material.STONE_BUTTON
)

fun ButtonContext<Paginator>.remove() {
    source.buttons.remove(button)
    update()
}

class Paginator(
    val title: String,
    var buttons: LinkedList<Button<Paginator>>,
    val row: Int = 6,
    val pageButtonFactory: Paginator.(left: Boolean) -> ItemStack = { left ->
        item(BUTTONS.random()) {
            amount = page
            meta {
                setName(
                    if (left) "&c<- &a[${page}/${maxPage}]"
                    else "&a[${page}/${maxPage}] &c->"
                )
            }
        }
    },
    var slots: List<Int> = (0 until ((row - 1) * 9)).toList(),
    var prevPageButtonSlot: Int = slot(3, row - 1),
    var nextPageButtonSlot: Int = slot(5, row - 1),
    val extraButtons: MutableMap<Int, Button<Paginator>> = mutableMapOf()
) : DynamicView {
    var page = 1
    val maxPage get() = buttons.size / slots.size + (buttons.size % slots.size).coerceAtMost(1)
    val isPageableToPrev get() = page > 1
    val isPageableToNext get() = page < maxPage

    override fun create() = inventory(title, row) { update(this) }

    override fun update(inventory: Inventory) = inventory.run {
        fill(null)
        var index = (page - 1) * slots.size
        for (slot in slots) {
            val button = buttons.getOrNull(index++) ?: break
            setItem(slot, button.item)
        }
        if (isPageableToPrev)
            setItem(prevPageButtonSlot, pageButtonFactory(this@Paginator, true))
        if (isPageableToNext)
            setItem(nextPageButtonSlot, pageButtonFactory(this@Paginator, false))
        extraButtons.forEach { (slot, button) ->
            setItem(slot, button.item)
        }
    }

    override fun onEvent(e: InventoryEvent) {
        e.cancelViolationClick()
        if (e is InventoryClickEvent && e.click != ClickType.DOUBLE_CLICK) {
            val slot = slots.indexOf(e.rawSlot)
            val button = if (slot >= 0) {
                val offset = (page - 1) * slots.size
                buttons.getOrNull(offset + slot)
            } else null
            if (button != null) {
                enderWand.scheduler {
                    button(e, this)
                }
            } else if (e.rawSlot == prevPageButtonSlot && isPageableToPrev) {
                page--
                update(e.inventory)
            } else if (e.rawSlot == nextPageButtonSlot && isPageableToNext) {
                page++
                update(e.inventory)
            } else {
                val extra = extraButtons[e.rawSlot]
                if (extra != null) {
                    enderWand.scheduler {
                        extra(e, this)
                    }
                }
            }
        }
    }
}