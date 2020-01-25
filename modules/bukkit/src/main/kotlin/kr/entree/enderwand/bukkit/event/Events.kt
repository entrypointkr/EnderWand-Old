package kr.entree.enderwand.bukkit.event

import org.bukkit.event.Cancellable
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.EquipmentSlot

/**
 * Created by JunHyung Lim on 2020-01-06
 */
inline val Cancellable.isNotCancelled get() = !isCancelled