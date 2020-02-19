package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.inventory.toSlot
import kr.entree.enderwand.math.Point
import org.bukkit.inventory.ItemStack

/**
 * Created by JunHyung Lim on 2020-02-19
 */
fun <T> slotButtonOf(point: Point<Int>, button: (T) -> Button<T>) =
    SlotButton(point.toSlot, button)

fun <T> slotItemOf(point: Point<Int>, item: (T) -> ItemStack) =
    slotButtonOf<T>(point) { button { item(it) } }

data class SlotButton<T>(
    var slot: Int,
    var button: (T) -> Button<T>
)