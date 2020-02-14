package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.inventory.slot
import org.bukkit.inventory.ItemStack

/**
 * Created by JunHyung Lim on 2020-01-14
 */
fun <T> buttonMapOf(mutableMap: MutableMap<Int, Button<T>>) =
    SimpleButtonMap(mutableMap)

interface ButtonMap<T> : MutableMap<Int, Button<T>> {
    val slots: Int
    val slotIndices get() = 0 until slots

    infix fun Int.slotOf(button: Button<T>)

    fun button(slots: Iterable<Pair<Int, Int>>, item: () -> ItemStack) =
        Button<T>(item).also { button -> slots.forEach { (x, y) -> slot(x, y) slotOf button } }

    fun button(vararg slots: Pair<Int, Int>, item: () -> ItemStack) =
        button(slots.asIterable(), item)

    fun button(slot: Int, item: () -> ItemStack) =
        Button<T>(item).also { button -> slot slotOf button }

    fun fill(button: Button<T>) {
        for (i in slotIndices) {
            i slotOf button
        }
    }
}

class SimpleButtonMap<T>(
    val map: MutableMap<Int, Button<T>>
) : ButtonMap<T>, MutableMap<Int, Button<T>> by map {
    override fun Int.slotOf(button: Button<T>) {
        val array = arrayOfNulls<Int>(3)
        array.indices
        map[this] = button
    }

    override val slots get() = map.map { it.key }.max() ?: 0
}