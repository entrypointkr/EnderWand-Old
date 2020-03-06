package kr.entree.enderwand.bukkit.serialization

import kotlinx.serialization.SerialDescriptor
import org.bukkit.inventory.ItemStack

object ItemStackSerializer : TypedConfigurationSerializer<ItemStack>() {
    override val descriptor: SerialDescriptor = PolymorphicConfigurationSerializer.descriptor
}