package kr.entree.enderwand.bukkit.inventory

import kr.entree.enderwand.command.validate
import kr.entree.enderwand.math.Point

/**
 * Created by JunHyung Lim on 2020-03-09
 */
val Point<Int>.toSlot get() = slot(x, y)

fun Point<Int>.slots(end: Point<Int>): List<Int> {
    val ret = mutableListOf<Int>()
    for (y in this.y..end.y) {
        for (x in this.x..end.x) {
            ret += slot(x, y)
        }
    }
    return ret
}

fun slot(x: Int, y: Int) = y * 9 + x

private val PATTERNS =
    ('1'..'9').plus('a'..'z').plus('A'..'Z')
        .mapIndexed { index, char -> char to index }
        .toMap()

fun slotPatterns(vararg patterns: String): List<Int> {
    val ret = mutableListOf<Pair<Int, Int>>()
    for ((y, pattern) in patterns.withIndex()) {
        validate(pattern.length == 9, "Length must be 9")
        for ((x, char) in pattern.withIndex()) {
            val priority = PATTERNS[char] ?: continue
            ret += slot(x, y) to priority
        }
    }
    return ret.sortedBy { it.second }.map { it.first }
}