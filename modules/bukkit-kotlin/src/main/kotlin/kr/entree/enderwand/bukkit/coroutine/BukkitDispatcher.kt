@file:Suppress("unused")

package kr.entree.enderwand.bukkit.coroutine

import com.google.auto.service.AutoService
import kotlinx.coroutines.*
import kotlinx.coroutines.internal.MainDispatcherFactory
import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.bukkit.scheduler.scheduler
import kr.entree.enderwand.coroutine.SchedulerDispatcher
import org.bukkit.Bukkit
import kotlin.coroutines.CoroutineContext

/**
 * Created by JunHyung Lim on 2020-02-14
 */
val Dispatchers.EnderWand: SchedulerDispatcher by lazy {
    SchedulerDispatcher(enderWand.scheduler)
}
val Dispatchers.Bukkit: BukkitDispatcher get() = MainBukkitDispatcher

@UseExperimental(InternalCoroutinesApi::class)
sealed class BukkitDispatcher : MainCoroutineDispatcher(), Delay by Dispatchers.EnderWand {
    override fun dispatch(context: CoroutineContext, block: Runnable) =
        Dispatchers.EnderWand.dispatch(context, block)

    override fun invokeOnTimeout(timeMillis: Long, block: Runnable) =
        Dispatchers.EnderWand.invokeOnTimeout(timeMillis, block)
}

internal object MainBukkitDispatcher : BukkitDispatcher() {
    override val immediate get() = ImmediateBukkitDispatcher

    override fun toString() = "Bukkit"
}

internal object ImmediateBukkitDispatcher : BukkitDispatcher() {
    override val immediate get() = this

    override fun isDispatchNeeded(context: CoroutineContext) =
        !Bukkit.isPrimaryThread()

    override fun toString() = "Bukkit [immediate]"
}

@AutoService(MainDispatcherFactory::class)
@UseExperimental(InternalCoroutinesApi::class)
internal class BukkitDispatcherFactory : MainDispatcherFactory {
    override val loadPriority get() = 0

    override fun createDispatcher(allFactories: List<MainDispatcherFactory>) =
        MainBukkitDispatcher
}