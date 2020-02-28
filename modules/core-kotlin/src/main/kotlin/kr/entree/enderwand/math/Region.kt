package kr.entree.enderwand.math

import kotlinx.serialization.Serializable

operator fun <T> Point3D<T>.rangeTo(other: Point3D<T>): Region3D<T>
        where T : Number, T : Comparable<T> = Region3D(
    Point3D(min(x, other.x), min(y, other.y), min(z, other.z)),
    Point3D(max(x, other.x), max(y, other.y), max(z, other.z))
)

@Serializable
data class Region3D<T>(
    val from: Point3D<T>,
    val to: Point3D<T>
) where T : Number, T : Comparable<T> {
    val isIdentical get() = from == to

    operator fun contains(point: Point3D<T>) = point.run {
        x in from.x..to.x
                && y in from.y..to.y
                && z in from.z..to.z
    }

    fun center() = Point3D(
        mid(from.x, to.x),
        mid(from.y, to.y),
        mid(from.z, to.z)
    )

    fun isEnter(to: Point3D<T>, from: Point3D<T>): Boolean {
        val enter = contains(to)
        return enter && enter != contains(from)
    }

    fun format() = if (isIdentical) {
        val (x, y, z) = from
        "$x, $y, $z"
    } else {
        val (fromX, fromY, fromZ) = from
        val (toX, toY, toZ) = to
        "($fromX, $fromY, $fromZ) - ($toX, $toY, $toZ)"
    }
}