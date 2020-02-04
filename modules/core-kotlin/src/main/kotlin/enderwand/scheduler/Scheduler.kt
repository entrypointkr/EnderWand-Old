package enderwand.scheduler

import java.time.Duration


/**
 * Created by JunHyung Lim on 2019-12-05
 */
typealias Runner = (() -> Unit) -> Unit

fun Scheduler.runRepeat(period: Duration, runnable: () -> Unit) = runRepeat(period, period, runnable)

interface Scheduler : Runner {
    fun runLater(delay: Duration, runnable: () -> Unit)

    fun runRepeat(delay: Duration, period: Duration, runnable: () -> Unit)
}