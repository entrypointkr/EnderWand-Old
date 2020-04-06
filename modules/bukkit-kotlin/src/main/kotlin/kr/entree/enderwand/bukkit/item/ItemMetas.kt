package kr.entree.enderwand.bukkit.item

import kr.entree.enderwand.bukkit.lang.i18nName
import kr.entree.enderwand.bukkit.message.colorize
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * Created by JunHyung Lim on 2020-01-25
 */
inline fun ItemStack.meta(configure: ItemMeta.() -> Unit) = metaOf(configure)

inline fun <reified T : ItemMeta> ItemStack.metaOf(configure: T.() -> Unit) {
    val meta = itemMeta
    if (meta is T) {
        meta.configure()
        itemMeta = meta
    } else throw IllegalStateException("$type meta of item isn't ${T::class.java.simpleName}")
}

fun ItemMeta.setName(name: String) = setDisplayName(name.colorize())

fun ItemStack.setName(name: String) = meta { setName(name) }

fun ItemMeta.setLore(vararg lore: String) = setLore(lore.map { it.colorize() })

fun ItemStack.setLore(vararg line: String) = meta { setLore(*line) }

inline fun ItemMeta.lore(configure: MutableList<String>.() -> Unit) {
    val newLore = (lore ?: mutableListOf()).apply(configure).map { it.colorize() }
    lore = newLore
}

val ItemStack.displayName get() = itemMeta?.displayName?.takeIf { it.isNotBlank() } ?: type.i18nName()

val ItemMeta.modelData
    get() = if (hasCustomModelData()) {
        customModelData
    } else null