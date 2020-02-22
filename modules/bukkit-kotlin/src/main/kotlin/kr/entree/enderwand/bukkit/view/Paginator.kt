package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.event.cancelViolationClick
import kr.entree.enderwand.bukkit.event.isNotDoubleClick
import kr.entree.enderwand.bukkit.inventory.fill
import kr.entree.enderwand.bukkit.inventory.inventory
import kr.entree.enderwand.bukkit.item.emptyItem
import kr.entree.enderwand.bukkit.item.item
import kr.entree.enderwand.bukkit.item.meta
import kr.entree.enderwand.bukkit.item.setName
import kr.entree.enderwand.math.Point
import kr.entree.enderwand.math.to
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * Created by JunHyung Lim on 2020-01-20
 */
inline fun paginator(
    refer: Paginator,
    title: String = refer.title,
    row: Int = refer.row,
    configure: Paginator.() -> Unit
): Paginator = Paginator(
    title,
    row,
    refer.buttons,
    refer.slots,
    refer.staticButtons
).apply(configure)

inline fun paginator(
    title: String,
    row: Int = 6,
    configure: Paginator.() -> Unit = {}
) = Paginator(
    title = title,
    row = row,
    buttons = mutableListOf(),
    slots = (0 until ((row - 1) * 9)).toList(),
    staticButtons = buttonMapOf(row * 9, mutableMapOf())
).apply(configure)

class Paginator(
    val title: String,
    val row: Int = 6,
    var buttons: MutableList<Button<Paginator>>,
    var slots: List<Int>,
    val staticButtons: MutableMap<Int, Button<Paginator>>
) : DynamicView, ButtonMap<Paginator> {
    override val size = row * 9
    var page: Int = 1
    val maxPage get() = buttons.size / slots.size + (buttons.size % slots.size).coerceAtMost(1)
    val isPageableToPrev get() = page > 1
    val isPageableToNext get() = page < maxPage
    override val context = ViewContextImpl(this)
    var handler: ViewEventContext<Paginator>.(InventoryEvent) -> Unit = {}
    var updater: Paginator.() -> Unit = {}

    companion object {
        val DEFAULT_BUTTONS = listOf(
            Material.OAK_BUTTON, Material.ACACIA_BUTTON, Material.BIRCH_BUTTON,
            Material.DARK_OAK_BUTTON, Material.JUNGLE_BUTTON, Material.STONE_BUTTON
        )
    }

    override fun create() = inventory(title, row) { update(this) }

    override fun update(inventory: Inventory) {
        inventory.fill(null)
        buttons.clear()
        updater(this)
        var index = (page - 1) * slots.size
        for (slot in slots) {
            val button = buttons.getOrNull(index++) ?: break
            inventory.setItem(slot, button.item(context))
        }
        staticButtons.forEach { (slot, button) ->
            inventory.setItem(slot, button.item(context))
        }
    }

    override fun handle(event: InventoryEvent) {
        event.cancelViolationClick()
        if (event is InventoryClickEvent && event.isNotDoubleClick) {
            val slot = slots.indexOf(event.rawSlot)
            val button = if (slot >= 0) {
                val offset = (page - 1) * slots.size
                buttons.getOrNull(offset + slot)
            } else null
            if (button != null) {
                button.invokeLater(event, context)
            } else {
                staticButtons[event.rawSlot]?.invokeLater(event, context)
            }
        }
        handler(ViewEventContextImpl(event, ViewContextImpl(this)), event)
    }

    fun button(item: ViewContext<Paginator>.() -> ItemStack) = Button(item)

    inline fun prevPagingButton(
        slot: Point<Int> = 3 to row - 1,
        crossinline item: ViewContext<Paginator>.() -> ItemStack = {
            item(DEFAULT_BUTTONS.random()) {
                amount = view.page
                meta {
                    setName("&c<- &a[${view.page}/${view.maxPage}]")
                }
            }
        }
    ) = button {
        item()
    }.also { child ->
        button {
            if (isPageableToPrev) {
                child.item(this)
            } else emptyItem()
        }.onClick {
            if (isPageableToPrev) {
                view.page--
            }
            child.click(this)
            update()
        } at slot
    }

    inline fun nextPagingButton(
        slot: Point<Int> = 5 to row - 1,
        crossinline item: ViewContext<Paginator>.() -> ItemStack = {
            item(DEFAULT_BUTTONS.random()) {
                amount = view.page
                meta {
                    setName("&a[${view.page}/${view.maxPage}] &c->")
                }
            }
        }
    ) = button {
        item()
    }.also { child ->
        button {
            if (isPageableToNext) {
                child.item(this)
            } else emptyItem()
        }.onClick {
            if (isPageableToNext) {
                view.page++
            }
            child.click(this)
            update()
        } at slot
    }

    fun onEvent(handler: ViewEventContext<Paginator>.(InventoryEvent) -> Unit): Paginator {
        this.handler = handler
        return this
    }

    fun onUpdate(updater: Paginator.() -> Unit): Paginator {
        this.updater = updater
        return this
    }

    override fun Button<Paginator>.at(slot: Int) = staticButtons.put(slot, this)

    operator fun Button<Paginator>.unaryPlus() = buttons.add(this)

    operator fun Iterable<Button<Paginator>>.unaryPlus() = buttons.addAll(this)

    operator fun Button<Paginator>.unaryMinus() = buttons.remove(this)

    operator fun Iterable<Button<Paginator>>.unaryMinus() = buttons.removeAll(this)
}