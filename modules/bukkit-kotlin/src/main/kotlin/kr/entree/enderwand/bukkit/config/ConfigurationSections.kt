package kr.entree.enderwand.bukkit.config

import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.bukkit.material.toMaterial
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.MemoryConfiguration
import java.util.logging.Level

/**
 * Created by JunHyung Lim on 2020-01-18
 */
val ConfigurationSection.keys get() = getKeys(false)
val ConfigurationSection.entries
    get() = getKeys(false).map {
        it to get(it)
    }.filter {
        it.second != null
    }

inline fun section(configure: ConfigurationSection.() -> Unit) =
    MemoryConfiguration().apply(configure)

inline fun ConfigurationSection.section(key: String, configure: ConfigurationSection.() -> Unit): ConfigurationSection {
    val section = MemoryConfiguration()
    set(key, section)
    configure(section)
    return section
}

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

inline fun <reified T> ConfigurationSection.getOrPut(id: String, default: () -> T): T {
    return get(id)?.run { this as T } ?: default().apply {
        set(id, this)
    }
}

inline fun ConfigurationSection.forEach(receiver: (Pair<String, Any?>) -> Unit) {
    for (key in getKeys(false)) {
        receiver(Pair(key, get(key)))
    }
}

fun <V> Iterable<Pair<String, V>>.toSection(): ConfigurationSection {
    val section = MemoryConfiguration()
    forEach { (key, value) -> section.set(key, value) }
    return section
}

inline fun <reified T> ConfigurationSection.getTypedList(key: String): List<T> {
    return getList(key)?.map { it as T } ?: emptyList()
}