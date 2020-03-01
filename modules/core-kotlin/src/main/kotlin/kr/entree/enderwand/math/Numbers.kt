package kr.entree.enderwand.math

/**
 * Created by JunHyung Lim on 2020-03-01
 */
fun Double.format(): String {
    val number: Number = if (toInt().toDouble() == this)
        toInt()
    else this
    return number.toString()
}

fun Float.format(): String {
    val number: Number = if (toInt().toFloat() == this)
        toInt()
    else this
    return number.toString()
}