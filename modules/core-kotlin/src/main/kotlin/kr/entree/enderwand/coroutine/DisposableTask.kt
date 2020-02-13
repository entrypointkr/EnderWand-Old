package kr.entree.enderwand.coroutine

import kotlinx.coroutines.DisposableHandle
import kr.entree.enderwand.scheduler.Task

/**
 * Created by JunHyung Lim on 2020-02-14
 */
class DisposableTask(private val task: Task) : DisposableHandle, Task by task {
    override fun dispose() = task.cancel()
}