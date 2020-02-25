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

fun <T> Point<T>.format(format: String) where T : Number, T : Comparable<T> = MessageFormat.format(format, x, y)

fun <T> Point<T>.serializeToString() where T : Number, T : Comparable<T> = format("{0},{1}")

inline fun <T> String.toPoint(mapper: (String) -> T) where T : Number, T : Comparable<T> =
    split(",").run {
        Point(mapper(get(0)), mapper(get(1)))
    }

fun String.toPoint() = toPoint { it.toInt() }