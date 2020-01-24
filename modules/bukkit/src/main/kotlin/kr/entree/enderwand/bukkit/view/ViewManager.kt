package kr.entree.enderwand.bukkit.view

import kr.entree.enderwand.bukkit.enderWand
import org.bukkit.entity.HumanEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.Inventory
import java.util.*

/**
 * Created by JunHyung Lim on 2020-01-04
 */
fun HumanEntity.open(view: ViewComponent) =
    enderWand.viewManager.open(this, view)

fun HumanEntity.open(handler: (InventoryEvent) -> Unit, factory: () -> Inventory) =
    open(View(handler, factory))

class ViewManager : Listener {
    val handlerMap = mutableMapOf<UUID, ViewComponent>()

    fun open(player: HumanEntity, view: ViewComponent) =
        view.create().apply {
            player.openInventory(this)
            handlerMap[player.uniqueId] = view
        }

    fun notify(e: InventoryEvent) = handlerMap[e.view.player.uniqueId]?.onEvent(e)

    @EventHandler
    fun onClick(e: InventoryClickEvent) = notify(e)

    @EventHandler
    fun onClose(e: InventoryCloseEvent) {
        notify(e)
        handlerMap.remove(e.player.uniqueId)
    }

    @EventHandler
    fun onDrag(e: InventoryDragEvent) = notify(e)
}