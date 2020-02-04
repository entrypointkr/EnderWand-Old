package enderwand.bukkit.config

import enderwand.bukkit.enderWand
import enderwand.bukkit.material.toMaterial
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.MemoryConfiguration
import java.util.logging.Level

/**
 * Created by JunHyung Lim on 2020-01-18
 */
inline fun section(configure: ConfigurationSection.() -> Unit) =
    MemoryConfiguration().apply(configure)

fun ConfigurationSection.setMaterials(key: String, materials: Collection<Material>) =
    set(key, materials.map { it.name }.toList())

inline fun ConfigurationSection.getMaterials(
    key: String,
    def: Set<Material> = emptySet(),
    failure: (String) -> Unit = {
        enderWand.logger.log(Level.WARNING, "Unknown material name: $it")
    }
) = get(key)?.run {
    if (this is Collection<*>) {
        this.mapNotNull {
            val mat = it.toString().toMaterial()
            if (mat != null) {
                mat
            } else {
                failure(it.toString())
                null
            }
        }.toSet()
    } else {
        null
    }
} ?: def

inline fun ConfigurationSection.forEach(receiver: (Pair<String, Any?>) -> Unit) {
    for (key in getKeys(false)) {
        receiver(Pair(key, get(key)))
    }
}

val ConfigurationSection.keys get() = getKeys(false)
val ConfigurationSection.entries
    get() = getKeys(false).map {
        it to get(it)
    }.filter {
        it.second != null
    }