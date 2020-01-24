package kr.entree.enderwand.bukkit.item

import kr.entree.enderwand.bukkit.message.colorize
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Created by JunHyung Lim on 2020-01-01
 */
fun Material.toItem(amount: Int = 1) = ItemStack(this, amount)

inline fun ItemStack.meta(configure: ItemMeta.() -> Unit) {
    val meta = itemMeta
    if (meta != null) {
        meta.configure()
        itemMeta = meta
    }
}

inline fun item(material: Material, configure: ItemStack.() -> Unit = {}) = material.toItem().apply(configure)

inline fun item(item: ItemStack, configure: ItemStack.() -> Unit = {}) = ItemStack(item).apply(configure)

fun ItemMeta.setName(name: String) = setDisplayName(name.colorize())

fun ItemMeta.setLore(vararg lore: String) = setLore(lore.map { ChatColor.WHITE.toString() + it.colorize() })

inline fun ItemMeta.lore(builder: MutableList<String>.() -> Unit) =
    setLore(mutableListOf<String>().apply(builder).map { it.colorize() })

val ItemStack.displayName get() = itemMeta?.displayName ?: ""

@UseExperimental(ExperimentalContracts::class)
fun ItemStack?.isAir(): Boolean {
    contract {
        returns(false) implies (this@isAir != null)
    }
    return this == null || type == Material.AIR
}

@UseExperimental(ExperimentalContracts::class)
fun ItemStack?.isNotAir(): Boolean {
    contract {
        returns(true) implies (this@isNotAir != null)
    }
    return !isAir()
}