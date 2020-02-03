package kr.entree.enderwand.bukkit.scheduler

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask

/**
 * Created by JunHyung Lim on 2019-12-05
 */
class BukkitAsyncScheduler(
    val plugin: Plugin,
    val scheduler: UglyScheduler = Bukkit.getScheduler()
) : BukkitScheduler() {
    override fun runTask(
        runnable: () -> Unit
    ): BukkitTask = scheduler.runTask(plugin, runnable)

    override fun runTaskLater(
        delayTicks: Long,
        runnable: () -> Unit
    ): BukkitTask = scheduler.runTaskLaterAsynchronously(plugin, runnable, delayTicks)

    override fun runTaskRepeat(
        delayTicks: Long,
        periodTicks: Long,
        runnable: () -> Unit
    ): BukkitTask = scheduler.runTaskTimerAsynchronously(plugin, runnable, delayTicks, periodTicks)
}