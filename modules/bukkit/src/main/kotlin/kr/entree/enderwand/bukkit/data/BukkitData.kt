package kr.entree.enderwand.bukkit.data

import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.bukkit.scheduler.scheduler
import kr.entree.enderwand.bukkit.scheduler.schedulerAsync
import kr.entree.enderwand.data.StandardData
import org.bukkit.plugin.Plugin

/**
 * Created by JunHyung Lim on 2020-01-02
 */
fun StandardData.loadAsync(plugin: Plugin = enderWand) = loadAsync(plugin.schedulerAsync, plugin.scheduler)

fun StandardData.saveAsync(plugin: Plugin = enderWand) = saveAsync(plugin.schedulerAsync)