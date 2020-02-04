package enderwand.bukkit.coroutine

import kotlinx.coroutines.CoroutineScope
import enderwand.bukkit.scheduler.scheduler
import enderwand.coroutine.RunnerDispatcher
import org.bukkit.plugin.Plugin

/**
 * Created by JunHyung Lim on 2020-01-09
 */
val Plugin.scope get() = CoroutineScope(RunnerDispatcher(scheduler))