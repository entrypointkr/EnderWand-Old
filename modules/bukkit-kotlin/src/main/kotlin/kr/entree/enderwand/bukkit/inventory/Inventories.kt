package kr.entree.enderwand.bukkit.inventory

import kr.entree.enderwand.bukkit.item.isAir
import kr.entree.enderwand.bukkit.item.item
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory

/**
 * Created by JunHyung Lim on 2020-01-01
 */
val Inventory.sizeOfStorage
    get() =
        if (this is PlayerInventory) {
            36
        } else {
            size
        }

fun Inventory.iteratorOfStorage() = (0 until sizeOfStorage).iterator()

fun inventory(type: InventoryType, title: String, configure: Inventory.() -> Unit = {}) =
    Bukkit.createInventory(null, type, title).apply(configure)

inline fun inventory(title: String, row: Int, configure: Inventory.() -> Unit = {}) =
    Bukkit.createInventory(null, row * 9, title).apply(configure)

fun Inventory.addAt(slot: Int, item: ItemStack, count: Int = item.amount): JobResult {
    if (count <= 0) return JobResult.SUCCESS
    val slotItem = getItem(slot)
    return JobResult(
        when {
            slotItem.isAir() -> {
                val amount = count.coerceAtMost(item.maxStackSize)
                setItem(slot, item(item, amount))
                (count - item.maxStackSize).coerceAtLeast(0)
            }
            slotItem.isSimilar(item) && slotItem.amount < slotItem.maxStackSize -> {
                val beforeAmount = slotItem.amount
                slotItem.amount = (beforeAmount + count).coerceAtMost(item.maxStackSize)
                (beforeAmount + count - maxStackSize).coerceAtLeast(0)
            }
            else -> item.amount
        }
    )
}

fun Inventory.takeAt(slot: Int, item: ItemStack, count: Int = item.amount): JobResult {
    if (count <= 0) return JobResult.SUCCESS
    val slotItem = getItem(slot)
    return JobResult(
        when {
            slotItem != null && slotItem.isSimilar(item) -> {
                if (slotItem.amount > count) {
                    slotItem.amount -= count
                    0
                } else {
                    setItem(slot, null)
                    count - slotItem.amount
                }
            }
            else -> count
        }
    )
}


fun Inventory.open(player: HumanEntity) = player.openInventory(this)

fun Inventory.fill(item: ItemStack?) {
    for (i in 0 until size) {
        setItem(i, item)
    }
}