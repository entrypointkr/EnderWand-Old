package kr.entree.enderwand.bukkit.coroutine

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kr.entree.enderwand.bukkit.enderWand
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by JunHyung Lim on 2020-02-14
 */
suspend fun awaitTick() = suspendCancellableCoroutine<Unit> {
    enderWand.tickNotifier.continuations += it
}

class TickNotifier : () -> Unit {
    val continuations = CopyOnWriteArrayList<CancellableContinuation<Unit>>()

    @UseExperimental(ExperimentalCoroutinesApi::class)
    override fun invoke() {
        val array = continuations.toTypedArray()
        continuations.clear()
        array.forEach { continuation ->
            continuation.apply { Dispatchers.Bukkit.resumeUndispatched(Unit) }
        }
    }
}