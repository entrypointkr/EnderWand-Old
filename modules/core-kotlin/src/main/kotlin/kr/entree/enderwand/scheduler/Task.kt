package kr.entree.enderwand.scheduler

/**
 * Created by JunHyung Lim on 2020-02-13
 */
interface Task {
    fun cancel()

    val isCancelled: Boolean
}