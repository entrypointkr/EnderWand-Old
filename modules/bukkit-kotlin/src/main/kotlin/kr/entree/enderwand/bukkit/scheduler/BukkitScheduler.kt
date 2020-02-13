package kr.entree.enderwand.bukkit.scheduler

import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.data.StandardData
import kr.entree.enderwand.data.autoSave
import kr.entree.enderwand.scheduler.Scheduler
import kr.entree.enderwand.time.minutes
import org.bukkit.plugin.Plugin
import java.time.Duration

import org.bukkit.scheduler.BukkitTask as OriginalTask

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
    override fun run(runnable: () -> Unit) = BukkitTask(runTask { runnable() })

    fun runTaskLater(
        delay: Duration,
        runnable: () -> Unit
    ) = BukkitTask(runTaskLater(delay.toTicks(), runnable))

    override fun runLater(
        delay: Duration,
        runnable: () -> Unit
    ) = runTaskLater(delay) { runnable() }

    fun runTaskRepeat(
        delay: Duration,
        period: Duration = delay,
        runnable: () -> Unit
    ): BukkitTask = BukkitTask(runTaskRepeat(delay.toTicks(), period.toTicks(), runnable))

    fun runTaskRepeat(
        period: Duration,
        runnable: () -> Unit
    ) = runTaskRepeat(period, period, runnable)

    override fun runRepeat(
        delay: Duration,
        period: Duration,
        runnable: () -> Unit
    ) = runTaskRepeat(delay, period) { runnable() }

    abstract fun runTask(
        runnable: () -> Unit
    ): OriginalTask

    abstract fun runTaskLater(
        delayTicks: Long,
        runnable: () -> Unit
    ): OriginalTask

    abstract fun runTaskRepeat(
        delayTicks: Long,
        periodTicks: Long,
        runnable: () -> Unit
    ): OriginalTask

    fun runTaskRepeat(
        periodTicks: Long,
        runnable: () -> Unit
    ) = runTaskRepeat(periodTicks, periodTicks, runnable)
}