package kr.entree.enderwand.math

/**
 * Created by JunHyung Lim on 2020-02-28
 */
fun <T> min(a: T, b: T): T where T : Comparable<T> =
    when {
        a != a -> a
        a <= b -> a
        else -> b
    }

fun <T> max(a: T, b: T): T where T : Comparable<T> =
    when {
        a != a -> a
        a >= b -> a
        else -> b
    }

fun mid(a: Double, b: Double) = a / 2 + b / 2

fun mid(a: Number, b: Number) = mid(a.toDouble(), b.toDouble())