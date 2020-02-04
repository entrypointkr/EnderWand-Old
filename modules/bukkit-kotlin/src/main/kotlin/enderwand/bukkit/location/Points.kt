package enderwand.bukkit.location

import enderwand.math.Point
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

fun <T : Number> Point<T>.format(format: String) = MessageFormat.format(format, x, y)

fun <T : Number> Point<T>.serializeToString() = format("{0},{1}")

inline fun <T : Number> String.toPoint(mapper: (String) -> T) =
    split(",").run {
        Point(mapper(get(0)), mapper(get(1)))
    }

fun String.toPoint() = toPoint { it.toInt() }