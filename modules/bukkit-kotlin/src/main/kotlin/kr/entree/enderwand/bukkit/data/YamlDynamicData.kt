package kr.entree.enderwand.bukkit.data

import kr.entree.enderwand.data.DynamicData
import kr.entree.enderwand.data.StandardData
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.Reader
import java.io.Writer

/**
 * Created by JunHyung Lim on 2020-01-01
 */
inline fun yamlDataOf(file: File, yamlData: YamlData, plugin: Plugin, configure: StandardData.() -> Unit = {}) =
    plugin.dataOf(file, YamlDynamicData(yamlData)).apply(configure)

inline fun Plugin.yamlDataOf(fileName: String, yamlData: YamlData, configure: StandardData.() -> Unit = {}) =
    dataOf(File(dataFolder, fileName), YamlDynamicData(yamlData)).apply(configure)

class YamlDynamicData(
    private val data: YamlData
) : DynamicData {
    override fun load(reader: Reader) = reader.use {
        val contents = it.readText()
        YamlConfiguration().run {
            loadFromString(contents)
            data.load(this)
        }
    }

    override fun save(writer: Writer) = writer.use {
        it.write(data.save().saveToString())
    }
}