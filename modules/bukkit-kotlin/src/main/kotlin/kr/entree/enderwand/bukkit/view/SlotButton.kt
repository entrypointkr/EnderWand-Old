package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.inventory.toSlot
import kr.entree.enderwand.math.Point

/**
 * Created by JunHyung Lim on 2020-02-19
 */
fun <T> slotButtonOf(point: Point<Int>, button: Button<T>) =
    SlotButton(point.toSlot, button)

data class SlotButton<T>(
    var slot: Int,
    var button: Button<T>
)