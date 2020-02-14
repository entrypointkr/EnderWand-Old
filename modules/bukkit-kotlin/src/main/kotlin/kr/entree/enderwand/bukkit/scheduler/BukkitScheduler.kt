package kr.entree.enderwand.bukkit.scheduler

import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.data.StandardData
import kr.entree.enderwand.data.autoSave
import kr.entree.enderwand.scheduler.Scheduler
import kr.entree.enderwand.time.minutes
import org.bukkit.Bukkit
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

    override fun isPrimaryThread() = Bukkit.isPrimaryThread()

    override fun runLater(
        delay: Duration,
        runnable: () -> Unit
    ) = runTaskLater(delay) { runnable() }

    override fun runRepeat(
        period: Duration,
        delay: Duration,
        runnable: () -> Unit
    ) = runTaskRepeat(period, delay) { runnable() }

    /////////////////////

    fun runTaskLater(
        delay: Duration = Duration.ZERO,
        runnable: () -> Unit
    ) = BukkitTask(runTaskLater(delay.toTicks(), runnable))

    fun runTaskRepeat(
        period: Duration = Duration.ZERO,
        delay: Duration = Duration.ZERO,
        runnable: () -> Unit
    ) = BukkitTask(runTaskRepeat(period.toTicks(), delay.toTicks(), runnable))

    abstract fun runTask(
        runnable: () -> Unit
    ): OriginalTask

    abstract fun runTaskLater(
        delayTicks: Long,
        runnable: () -> Unit
    ): OriginalTask

    abstract fun runTaskRepeat(
        periodTicks: Long,
        delayTicks: Long,
        runnable: () -> Unit
    ): OriginalTask

    fun runTaskRepeat(
        periodTicks: Long,
        runnable: () -> Unit
    ) = runTaskRepeat(periodTicks, periodTicks, runnable)
}