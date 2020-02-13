package kr.entree.enderwand.bukkit.scheduler

import kr.entree.enderwand.scheduler.Task
import org.bukkit.scheduler.BukkitTask as OriginalTask

/**
 * Created by JunHyung Lim on 2020-02-13
 */
class BukkitTask(val handle: OriginalTask) : Task {
    override fun cancel() = handle.cancel()

    override val isCancelled get() = handle.isCancelled
}