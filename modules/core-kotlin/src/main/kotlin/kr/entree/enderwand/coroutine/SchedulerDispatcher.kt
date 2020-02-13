package kr.entree.enderwand.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kr.entree.enderwand.scheduler.Scheduler
import kotlin.coroutines.CoroutineContext

/**
 * Created by JunHyung Lim on 2020-01-09
 */
class SchedulerDispatcher(private val scheduler: Scheduler) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        scheduler.run { block.run() }
    }
}