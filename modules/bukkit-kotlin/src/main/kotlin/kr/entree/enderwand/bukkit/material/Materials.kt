package kr.entree.enderwand.bukkit.material

import org.bukkit.DyeColor
import org.bukkit.Material

/**
 * Created by JunHyung Lim on 2020-01-01
 */
val Material.isOre
    get() = when (this) {
        Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE,
        Material.LAPIS_ORE, Material.REDSTONE_ORE, Material.DIAMOND_ORE,
        Material.EMERALD_ORE, Material.NETHER_QUARTZ_ORE -> true
        else -> false
    }

val Material.isSnow
    get() = when (this) {
        Material.SNOW, Material.SNOW_BLOCK -> true
        else -> false
    }

val Material.isLog
    get() = when (this) {
        Material.ACACIA_LOG, Material.BIRCH_LOG, Material.DARK_OAK_LOG,
        Material.JUNGLE_LOG, Material.OAK_LOG, Material.SPRUCE_LOG -> true
        else -> false
    }

val Material.isWood
    get() = when (this) {
        Material.ACACIA_WOOD, Material.BIRCH_WOOD, Material.DARK_OAK_WOOD,
        Material.JUNGLE_WOOD, Material.SPRUCE_WOOD, Material.OAK_WOOD -> true
        else -> false
    }

val Material.isWooden get() = isWood || isLog

val Material.isMineStone
    get() = when (this) {
        Material.STONE, Material.GRAVEL, Material.GRANITE -> true
        else -> false
    }

val Material.isBucket get() = name.endsWith("_BUCKET")

val Material.color
    get() = MaterialColor(name.substringBefore('_'))

val Material.base
    get() = runCatching {
        Material.valueOf(name.substringAfter('_'))
    }.getOrNull()

fun String.toMaterial() = kotlin.runCatching { Material.getMaterial(this) }.getOrNull()

val DyeColor.material
    get() = runCatching {
        Material.valueOf("${name}_DYE")
    }.getOrNull()

inline class MaterialColor(val name: String) {
    val color
        get() = runCatching {
            DyeColor.valueOf(name)
        }.getOrNull()
    val material
        get() = runCatching {
            Material.valueOf("${name}_DYE")
        }.getOrNull()
}