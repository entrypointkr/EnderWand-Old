package kr.entree.enderwand.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kr.entree.enderwand.scheduler.Runner
import kotlin.coroutines.CoroutineContext

/**
 * Created by JunHyung Lim on 2020-01-09
 */
class RunnerDispatcher(private val runner: Runner) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        runner.run { block.run() }
    }
}