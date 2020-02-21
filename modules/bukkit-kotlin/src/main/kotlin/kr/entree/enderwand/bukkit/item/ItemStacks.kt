package kr.entree.enderwand.bukkit.item

import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Created by JunHyung Lim on 2020-01-01
 */
val EMPTY_ITEM by lazy { item(Material.AIR) }

fun Material.toItem(amount: Int = 1) = ItemStack(this, amount)

inline fun item(material: Material = Material.AIR, amount: Int = 1, configure: ItemStack.() -> Unit = {}) =
    material.toItem(amount).apply(configure)

inline fun item(item: ItemStack, amount: Int = 1, configure: ItemStack.() -> Unit = {}) =
    ItemStack(item).apply {
        setAmount(amount)
        configure()
    }

fun emptyItem() = EMPTY_ITEM

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

inline operator fun ItemStack.invoke(configure: ItemStack.() -> Unit) = apply(configure)

inline fun headItem(player: OfflinePlayer?, configure: SkullMeta.() -> Unit) =
    item(Material.PLAYER_HEAD) {
        metaOf<SkullMeta> {
            owningPlayer = player
            configure(this)
        }
    }