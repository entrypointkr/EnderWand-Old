package kr.entree.enderwand.bukkit.item

import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

/**
 * Created by JunHyung Lim on 2020-03-09
 */
fun Material.toItem(amount: Int = 1) = ItemStack(this, amount)

inline fun item(material: Material = Material.AIR, amount: Int = 1, configure: ItemStack.() -> Unit = {}) =
    material.toItem(amount).apply(configure)

inline fun item(item: ItemStack, amount: Int = item.amount, configure: ItemStack.() -> Unit = {}) =
    ItemStack(item).apply {
        setAmount(amount)
        configure()
    }

inline fun itemOfSkull(player: OfflinePlayer?, configure: SkullMeta.() -> Unit) =
    item(Material.PLAYER_HEAD) {
        metaOf<SkullMeta> {
            owningPlayer = player
            configure(this)
        }
    }

val EMPTY_ITEM by lazy { item(Material.AIR) }

fun emptyItem() = EMPTY_ITEM