package kr.entree.enderwand.bukkit.exception

import org.bukkit.Material
import org.bukkit.inventory.PlayerInventory


/**
 * Created by JunHyung Lim on 2020-01-09
 */
interface Minecraft

open class UnknownPlayerException(val query: String) : RuntimeException(), Minecraft

open class UnknownMaterialException(val query: String) : RuntimeException(), Minecraft

open class NotCraftingMaterialException(val material: Material) : RuntimeException(), Minecraft

open class NoItemInMainHandException(val inventory: PlayerInventory) : IllegalStateException(), Minecraft