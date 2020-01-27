package kr.entree.enderwand.bukkit.item

import kr.entree.enderwand.bukkit.message.colorize
import org.bukkit.ChatColor
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * Created by JunHyung Lim on 2020-01-25
 */
inline fun ItemStack.meta(configure: ItemMeta.() -> Unit) = meta<ItemMeta>(configure)

@JvmName("metaTyped")
inline fun <reified T : ItemMeta> ItemStack.meta(configure: T.() -> Unit) {
    val meta = itemMeta
    if (meta is T) {
        meta.configure()
        itemMeta = meta
    }
}

fun ItemMeta.setName(name: String) = setDisplayName(name.colorize())

fun ItemMeta.setLore(vararg lore: String) = setLore(lore.map { ChatColor.WHITE.toString() + it.colorize() })

inline fun ItemMeta.lore(builder: MutableList<String>.() -> Unit) =
    setLore(mutableListOf<String>().apply(builder).map { it.colorize() })

val ItemStack.displayName get() = itemMeta?.displayName ?: ""

val ItemMeta.modelData
    get() = if (hasCustomModelData()) {
        customModelData
    } else null