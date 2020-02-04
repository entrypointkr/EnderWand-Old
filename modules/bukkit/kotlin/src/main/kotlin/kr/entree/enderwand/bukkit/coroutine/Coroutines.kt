package kr.entree.enderwand.bukkit.coroutine

import kotlinx.coroutines.CoroutineScope
import kr.entree.enderwand.bukkit.scheduler.scheduler
import kr.entree.enderwand.coroutine.RunnerDispatcher
import org.bukkit.plugin.Plugin

/**
 * Created by JunHyung Lim on 2020-01-09
 */
val Plugin.scope get() = CoroutineScope(RunnerDispatcher(scheduler))