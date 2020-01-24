package kr.entree.enderwand.collection

/**
 * Created by JunHyung Lim on 2019-12-03
 */
fun <T> List<T>.toReader() = Reader(this)

fun <T> Array<T>.toReader() = toList().toReader()

class Reader<T>(
    private val list: List<T>
) : List<T> by list {
    var pos = 0

    fun canRead() = size > pos

    fun peek() = list[pos]

    fun peekOrNull() = if (canRead()) peek() else null

    fun read() = list[pos++]

    fun readOrNull() = if (canRead()) read() else null

    fun remain() = (size - pos).coerceAtLeast(0)
}