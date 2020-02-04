package enderwand.bukkit.data

import enderwand.data.DynamicData
import enderwand.data.dataOf
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.Reader
import java.io.Writer

/**
 * Created by JunHyung Lim on 2020-01-01
 */
fun yamlDataOf(file: File, yamlData: YamlData) =
    dataOf(file, YamlDynamicData(yamlData))

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