package kr.entree.enderwand.scheduler

import java.time.Duration


/**
 * Created by JunHyung Lim on 2019-12-05
 */
typealias Runner = (() -> Unit) -> Task

interface Scheduler : Runner {
    fun run(runnable: () -> Unit): Task

    override fun invoke(runnable: () -> Unit) = run(runnable)

    fun runLater(delay: Duration, runnable: () -> Unit): Task

    fun runRepeat(period: Duration = Duration.ZERO, delay: Duration = Duration.ZERO, runnable: () -> Unit): Task

    fun isPrimaryThread(): Boolean
}