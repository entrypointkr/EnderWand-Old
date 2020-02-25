package kr.entree.enderwand.math

/**
 * Created by JunHyung Lim on 2020-01-04
 */
infix fun <T> T.to(num: T) where  T : Number, T : Comparable<T> = Point(this, num)

data class Point<T>(
    val x: T,
    val y: T
) where T : Number, T : Comparable<T>