package enderwand.bukkit.scheduler

import enderwand.bukkit.enderWand
import enderwand.data.StandardData
import enderwand.data.autoSave
import enderwand.scheduler.Scheduler
import enderwand.time.minutes
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import java.time.Duration

typealias UglyScheduler = org.bukkit.scheduler.BukkitScheduler

/**
 * Created by JunHyung Lim on 2020-01-06
 */
val Number.ticks get() = Duration.ofMillis(50 * toLong())

val Plugin.scheduler get() = BukkitSyncScheduler(this)

val Plugin.schedulerAsync get() = BukkitAsyncScheduler(this)

fun Duration.toTicks() = toMillis() / 50

fun StandardData.autoSave(plugin: Plugin = enderWand, period: Duration = 15.minutes) =
    autoSave(plugin.scheduler, plugin.schedulerAsync, period)

abstract class BukkitScheduler : Scheduler {
    override fun invoke(
        runnable: () -> Unit
    ) {
        runTask { runnable() }
    }

    fun runTaskLater(
        delay: Duration,
        runnable: () -> Unit
    ): BukkitTask = runTaskLater(delay.toTicks(), runnable)

    override fun runLater(
        delay: Duration,
        runnable: () -> Unit
    ) {
        runTaskLater(delay) { runnable() }
    }

    fun runTaskRepeat(
        delay: Duration,
        period: Duration = delay,
        runnable: () -> Unit
    ): BukkitTask = runTaskRepeat(delay.toTicks(), period.toTicks(), runnable)

    fun runTaskRepeat(
        period: Duration,
        runnable: () -> Unit
    ) = runTaskRepeat(period, period, runnable)

    override fun runRepeat(
        delay: Duration,
        period: Duration,
        runnable: () -> Unit
    ) {
        runTaskRepeat(delay, period) { runnable() }
    }

    abstract fun runTask(
        runnable: () -> Unit
    ): BukkitTask

    abstract fun runTaskLater(
        delayTicks: Long,
        runnable: () -> Unit
    ): BukkitTask

    abstract fun runTaskRepeat(
        delayTicks: Long,
        periodTicks: Long,
        runnable: () -> Unit
    ): BukkitTask

    fun runTaskRepeat(
        periodTicks: Long,
        runnable: () -> Unit
    ) = runTaskRepeat(periodTicks, periodTicks, runnable)
}