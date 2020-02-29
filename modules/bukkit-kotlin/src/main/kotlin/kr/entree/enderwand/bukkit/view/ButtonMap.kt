package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.inventory.slot
import kr.entree.enderwand.bukkit.inventory.toSlot
import kr.entree.enderwand.math.Point
import org.bukkit.inventory.ItemStack

/**
 * Created by JunHyung Lim on 2020-01-14
 */
fun <T : View> buttonMapOf(
    size: Int,
    mutableMap: MutableMap<Int, Button<T>>
) =
    ButtonMapImpl(mutableMap, size)

interface ButtonMap<T : View> {
    val size: Int
    val indices get() = 0 until size

    infix fun Button<T>.at(slot: Int): Button<T>?

    infix fun Button<T>.at(point: Point<Int>): Button<T>? = this at point.toSlot

    fun button(slots: Iterable<Point<Int>>, item: ViewContext<T>.() -> ItemStack) =
        Button(item).also { button -> slots.forEach { (x, y) -> button at slot(x, y) } }

    fun button(vararg slots: Point<Int>, item: ViewContext<T>.() -> ItemStack) =
        button(slots.asIterable(), item)

    fun button(slot: Int, item: ViewContext<T>.() -> ItemStack) =
        Button(item).also { button -> button at slot }

    fun fill(button: Button<T>) {
        for (i in indices) {
            button at i
        }
    }

    fun fill(item: ItemStack) = fill(button { item })
}

class ButtonMapImpl<T : View>(
    val map: MutableMap<Int, Button<T>>,
    override val size: Int
) : ButtonMap<T>, MutableMap<Int, Button<T>> by map {
    override fun Button<T>.at(slot: Int) = map.put(slot, this)
}