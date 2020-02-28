package kr.entree.enderwand.bukkit.data

import kr.entree.enderwand.data.DynamicData
import org.bukkit.plugin.Plugin
import java.io.File

/**
 * Created by JunHyung Lim on 2020-02-28
 */
fun Plugin.dataOf(file: File, data: DynamicData) = kr.entree.enderwand.data.dataOf(file, data, logger)