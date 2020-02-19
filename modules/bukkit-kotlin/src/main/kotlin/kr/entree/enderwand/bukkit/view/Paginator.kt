package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.event.cancelViolationClick
import kr.entree.enderwand.bukkit.event.isNotDoubleClick
import kr.entree.enderwand.bukkit.inventory.fill
import kr.entree.enderwand.bukkit.inventory.inventory
import kr.entree.enderwand.bukkit.item.item
import kr.entree.enderwand.bukkit.item.meta
import kr.entree.enderwand.bukkit.item.setName
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
fun ButtonContext<Paginator>.remove() {
    source.buttons.remove(button)
    update()
}

inline fun paginator(
    refer: Paginator,
    title: String = refer.title,
    row: Int = refer.row,
    configure: Paginator.() -> Unit
): Paginator = Paginator(
    title,
    refer.buttons,
    row,
    refer.prevPagingButton,
    refer.nextPagingButton,
    refer.slots,
    refer.extraButtons
)

inline fun paginator(
    title: String,
    row: Int = 6,
    configure: Paginator.() -> Unit = {}
): Paginator {
    return Paginator(
        title,
        mutableListOf(),
        row,
        slotButtonOf(3 to row - 1) {
            button<Paginator> {
                item(Paginator.DEFAULT_BUTTONS.random()) {
                    amount = it.page
                    meta {
                        setName("&c<- &a[${it.page}/${it.maxPage}]")
                    }
                }
            }.onClick { it.page-- }
        },
        slotButtonOf(5 to row - 1) {
            button<Paginator> {
                item(Paginator.DEFAULT_BUTTONS.random()) {
                    amount = it.page
                    meta {
                        setName("&a[${it.page}/${it.maxPage}] &c->")
                    }
                }
            }.onClick { it.page++ }
        },
        (0 until ((row - 1) * 9)).toList(),
        buttonMapOf(mutableMapOf())
    ).apply(configure)
}

class Paginator(
    val title: String,
    var buttons: MutableList<Button<Paginator>>,
    val row: Int = 6,
    var prevPagingButton: SlotButton<Paginator>,
    var nextPagingButton: SlotButton<Paginator>,
    var slots: List<Int>,
    val extraButtons: ButtonMap<Paginator>
) : DynamicView<Paginator> {
    var page: Int = 1
    val maxPage get() = buttons.size / slots.size + (buttons.size % slots.size).coerceAtMost(1)
    val isPageableToPrev get() = page > 1
    val isPageableToNext get() = page < maxPage
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
            setItem(slot, button.item())
        }
        if (isPageableToPrev)
            setItem(prevPagingButton.slot, prevPagingButton.button(this@Paginator).item())
        if (isPageableToNext)
            setItem(nextPagingButton.slot, nextPagingButton.button(this@Paginator).item())
        extraButtons.forEach { (slot, button) ->
            setItem(slot, button.item())
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
                button.invokeLater(e, this)
            } else if (e.rawSlot == prevPagingButton.slot && isPageableToPrev) {
                prevPagingButton.button(this).invoke(e, this)
                update(e.inventory)
            } else if (e.rawSlot == nextPagingButton.slot && isPageableToNext) {
                nextPagingButton.button(this).invoke(e, this)
                update(e.inventory)
            } else {
                extraButtons[e.rawSlot]?.invokeLater(e, this)
            }
        }
    }

    fun button(item: () -> ItemStack) = Button<Paginator>(item)

    inline fun extra(builder: ButtonMap<Paginator>.() -> Unit) = extraButtons.apply(builder)

    operator fun Button<Paginator>.unaryPlus() = buttons.add(this)

    operator fun Iterable<Button<Paginator>>.unaryPlus() = buttons.addAll(this)

    operator fun Button<Paginator>.unaryMinus() = buttons.remove(this)

    operator fun Iterable<Button<Paginator>>.unaryMinus() = buttons.removeAll(this)
}