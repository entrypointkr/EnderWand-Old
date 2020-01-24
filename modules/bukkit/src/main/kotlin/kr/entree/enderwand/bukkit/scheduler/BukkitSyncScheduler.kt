package kr.entree.enderwand.bukkit.scheduler

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask


/**
 * Created by JunHyung Lim on 2019-12-05
 */
class BukkitSyncScheduler(
    val plugin: Plugin,
    val scheduler: UglyScheduler = Bukkit.getScheduler()
) : BukkitScheduler() {
    override fun runTask(
        runnable: (BukkitTask) -> Unit
    ) = scheduler.runTask(plugin, runnable)

    override fun runTaskLater(
        delayTicks: Long, runnable: (BukkitTask) -> Unit
    ) = scheduler.runTaskLater(plugin, runnable, delayTicks)

    override fun runTaskRepeat(
        delayTicks: Long,
        periodTicks: Long,
        runnable: (BukkitTask) -> Unit
    ) = scheduler.runTaskTimer(plugin, runnable, delayTicks, periodTicks)
}