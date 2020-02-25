package kr.entree.enderwand.math

data class Region3D<T>(
    val from: Point3D<T>,
    val to: Point3D<T>
) where T : Number, T : Comparable<T> {
    val isIdentical get() = from == to

    operator fun contains(point: Point3D<T>) = point.run {
        x in from.x..to.y
                && y in from.y..to.y
                && z in from.z..to.z
    }

    fun center() = Point3D(
        (from.x.toDouble() - to.x.toDouble()) / 2,
        (from.y.toDouble() - to.y.toDouble()) / 2,
        (from.z.toDouble() - to.z.toDouble()) / 2
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