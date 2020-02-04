package enderwand.bukkit.data

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration

fun YamlData.save() = YamlConfiguration().apply { save(this) }

interface YamlData {
    fun save(to: ConfigurationSection)

    fun load(from: ConfigurationSection)
}