package kr.entree.enderwand.math

/**
 * Created by JunHyung Lim on 2020-01-04
 */
infix fun <T : Number> T.x(num: T) = Point(this, num)

data class Point<T : Number>(
    val x: T,
    val y: T
)