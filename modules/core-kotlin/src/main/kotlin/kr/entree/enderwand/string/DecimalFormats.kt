package kr.entree.enderwand.string

import java.text.DecimalFormat

/**
 * Created by JunHyung Lim on 2020-03-16
 */
val DECIMAL_FORMAT_INTEGER = DecimalFormat("#,###")

fun Number.format() = DECIMAL_FORMAT_INTEGER.format(this)