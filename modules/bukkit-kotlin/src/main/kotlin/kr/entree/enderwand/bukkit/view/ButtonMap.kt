package kr.entree.enderwand.bukkit.view

import org.bukkit.inventory.ItemStack

/**
 * Created by JunHyung Lim on 2020-01-14
 */
interface ButtonMap<T> {
    infix fun Int.slotOf(button: Button<T>)

    companion object {
        fun <T> simple(map: MutableMap<Int, Button<T>>) =
            SimpleButtonMap(map)
    }

    fun button(vararg slots: Int, item: () -> ItemStack) =
        Button<T>(item).also { button -> slots.forEach { it slotOf button } }

    fun button(slots: Collection<Int>, item: () -> ItemStack) =
        Button<T>(item).also { button -> slots.forEach { it slotOf button } }
}

class SimpleButtonMap<T>(
    val map: MutableMap<Int, Button<T>>
) : ButtonMap<T> {
    override fun Int.slotOf(button: Button<T>) {
        map[this] = button
    }
}