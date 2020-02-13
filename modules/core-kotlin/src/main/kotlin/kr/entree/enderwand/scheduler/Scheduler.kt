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

    fun runRepeat(delay: Duration, period: Duration, runnable: () -> Unit): Task

    fun runRepeat(period: Duration, runnable: () -> Unit) = runRepeat(period, period, runnable)

    fun isPrimaryThread(): Boolean
}