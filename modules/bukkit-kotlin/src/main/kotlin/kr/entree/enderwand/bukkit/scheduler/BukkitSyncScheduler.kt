package kr.entree.enderwand.bukkit.scheduler

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import org.bukkit.scheduler.BukkitScheduler as OriginalScheduler

/**
 * Created by JunHyung Lim on 2019-12-05
 */
class BukkitSyncScheduler(
    val plugin: Plugin,
    val scheduler: OriginalScheduler = Bukkit.getScheduler()
) : BukkitScheduler() {
    override fun runTask(
        runnable: () -> Unit
    ): BukkitTask = scheduler.runTask(plugin, runnable)

    override fun runTaskLater(
        delayTicks: Long, runnable: () -> Unit
    ): BukkitTask = scheduler.runTaskLater(plugin, runnable, delayTicks)

    override fun runTaskRepeat(
        periodTicks: Long,
        delayTicks: Long,
        runnable: () -> Unit
    ): BukkitTask = scheduler.runTaskTimer(plugin, runnable, delayTicks, periodTicks)
}