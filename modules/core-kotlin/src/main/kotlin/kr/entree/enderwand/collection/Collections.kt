package kr.entree.enderwand.collection

/**
 * Created by JunHyung Lim on 2020-02-14
 */
fun <E> MutableList<E>.removeOrNullAt(index: Int) =
    if (size > index) removeAt(index) else null