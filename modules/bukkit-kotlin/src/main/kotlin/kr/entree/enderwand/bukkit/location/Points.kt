package kr.entree.enderwand.bukkit.location

import kr.entree.enderwand.math.Point
import org.bukkit.util.Vector
import java.text.MessageFormat

/**
 * Created by JunHyung Lim on 2020-01-11
 */
fun Point<Int>.toVector(vector: Vector = Vector()) = Vector(
    (x shl 4) + (vector.blockX and 15),
    vector.blockY,
    (y shl 4) + (vector.blockZ and 15)
)

fun <T> Point<T>.format(format: String = "{0},{1}") where T : Number, T : Comparable<T> = MessageFormat.format(format, x, y)

infix fun Point<Int>.shl(count: Int) = Point(
    x shl count, y shl count
)

infix fun Point<Int>.shr(count: Int) = Point(
    x shr count, y shr count
)

inline fun <T> String.toPoint(mapper: (String) -> T) where T : Number, T : Comparable<T> =
    split(",").run {
        Point(mapper(get(0)), mapper(get(1)))
    }

fun String.toPoint() = toPoint { it.toInt() }