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
fun Material.toItem(amount: Int = 1) = ItemStack(this, amount)

inline fun item(material: Material, configure: ItemStack.() -> Unit = {}) = material.toItem().apply(configure)

inline fun item(item: ItemStack, configure: ItemStack.() -> Unit = {}) = ItemStack(item).apply(configure)

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

inline fun headItem(player: OfflinePlayer?, configure: SkullMeta.() -> Unit) =
    item(Material.PLAYER_HEAD) {
        meta<SkullMeta> {
            owningPlayer = player
            configure(this)
        }
    }