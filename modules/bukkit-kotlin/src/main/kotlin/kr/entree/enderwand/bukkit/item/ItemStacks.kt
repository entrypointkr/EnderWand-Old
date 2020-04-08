package kr.entree.enderwand.bukkit.item

import kr.entree.enderwand.bukkit.lang.i18nName
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Created by JunHyung Lim on 2020-01-01
 */
@OptIn(ExperimentalContracts::class)
fun ItemStack?.isAir(): Boolean {
    contract {
        returns(false) implies (this@isAir != null)
    }
    return this == null || type == Material.AIR
}

@OptIn(ExperimentalContracts::class)
fun ItemStack?.isNotAir(): Boolean {
    contract {
        returns(true) implies (this@isNotAir != null)
    }
    return !isAir()
}

fun ItemStack.space(item: ItemStack): Int {
    return when {
        isAir() -> item.maxStackSize
        isSimilar(item) -> (maxStackSize - amount).coerceAtLeast(0)
        else -> 0
    }
}

val ItemStack.displayName get() = itemMeta?.displayName?.takeIf { it.isNotBlank() } ?: type.i18nName()

fun ItemStack.format() = "$displayName*$amount"