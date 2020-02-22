package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.event.cancelViolationClick
import kr.entree.enderwand.bukkit.event.isNotDoubleClick
import kr.entree.enderwand.bukkit.inventory.fill
import kr.entree.enderwand.bukkit.inventory.inventory
import kr.entree.enderwand.bukkit.item.item
import kr.entree.enderwand.bukkit.item.meta
import kr.entree.enderwand.bukkit.item.setName
import kr.entree.enderwand.math.Point
import kr.entree.enderwand.math.to
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
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
)

fun ButtonContext<Paginator>.remove() {
    view.buttons.remove(button)
    update()
}

class Paginator(
    val title: String,
    val row: Int = 6,
    var buttons: MutableList<Button<Paginator>>,
    var slots: List<Int>,
    val staticButtons: MutableMap<Int, Button<Paginator>>
) : DynamicView<Paginator>, ButtonMap<Paginator> {
    override val size = row * 9
    var page: Int = 1
    val maxPage get() = buttons.size / slots.size + (buttons.size % slots.size).coerceAtMost(1)
    val isPageableToPrev get() = page > 1
    val isPageableToNext get() = page < maxPage
    val context = ViewContextImpl(this)
    override var closeHandler: (InventoryCloseEvent) -> Unit = {}
    override val instance get() = this

    companion object {
        val DEFAULT_BUTTONS = listOf(
            Material.OAK_BUTTON, Material.ACACIA_BUTTON, Material.BIRCH_BUTTON,
            Material.DARK_OAK_BUTTON, Material.JUNGLE_BUTTON, Material.STONE_BUTTON
        )
    }

    override fun create() = inventory(title, row) { update(this) }

    override fun update(inventory: Inventory) = inventory.run {
        fill(null)
        var index = (page - 1) * slots.size
        for (slot in slots) {
            val button = buttons.getOrNull(index++) ?: break
            setItem(slot, button.item(context))
        }
        staticButtons.forEach { (slot, button) ->
            setItem(slot, button.item(context))
        }
    }

    override fun handle(e: InventoryEvent) {
        e.cancelViolationClick()
        if (e is InventoryClickEvent && e.isNotDoubleClick) {
            val slot = slots.indexOf(e.rawSlot)
            val button = if (slot >= 0) {
                val offset = (page - 1) * slots.size
                buttons.getOrNull(offset + slot)
            } else null
            if (button != null) {
                button.invokeLater(e, context)
            } else {
                staticButtons[e.rawSlot]?.invokeLater(e, context)
            }
        }
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
            child.item(this)
        }.onClick {
            view.page--
            child.click(this)
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
            child.item(this)
        }.onClick {
            view.page++
            child.click(this)
        } at slot
    }

    override fun Button<Paginator>.at(slot: Int) = staticButtons.put(slot, this)

    operator fun Button<Paginator>.unaryPlus() = buttons.add(this)

    operator fun Iterable<Button<Paginator>>.unaryPlus() = buttons.addAll(this)

    operator fun Button<Paginator>.unaryMinus() = buttons.remove(this)

    operator fun Iterable<Button<Paginator>>.unaryMinus() = buttons.removeAll(this)
}