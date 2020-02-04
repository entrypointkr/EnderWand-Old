package enderwand.bukkit.data

import enderwand.bukkit.enderWand
import enderwand.bukkit.scheduler.scheduler
import enderwand.bukkit.scheduler.schedulerAsync
import enderwand.data.StandardData
import org.bukkit.plugin.Plugin

/**
 * Created by JunHyung Lim on 2020-01-02
 */
fun StandardData.loadAsync(plugin: Plugin = enderWand) = loadAsync(plugin.schedulerAsync, plugin.scheduler)

fun StandardData.saveAsync(plugin: Plugin = enderWand) = saveAsync(plugin.schedulerAsync)