package kr.entree.enderwand.bukkit.serialization

import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.withName
import org.bukkit.inventory.ItemStack

object ItemStackSerializer : TypedConfigurationSerializer<ItemStack>() {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("ItemStack")
}