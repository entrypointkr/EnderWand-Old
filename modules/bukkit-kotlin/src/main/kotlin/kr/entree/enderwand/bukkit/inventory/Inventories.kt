package kr.entree.enderwand.bukkit.inventory

import kr.entree.enderwand.bukkit.item.isAir
import kr.entree.enderwand.bukkit.item.item
import kr.entree.enderwand.math.Point
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory
import kotlin.math.min

/**
 * Created by JunHyung Lim on 2020-01-01
 */
inline fun inventory(type: InventoryType, title: String, configure: Inventory.() -> Unit = {}) =
    Bukkit.createInventory(null, type, title).apply(configure)

inline fun inventory(title: String, row: Int, configure: Inventory.() -> Unit = {}) =
    Bukkit.createInventory(null, row * 9, title).apply(configure)

val Inventory.storageSize
    get() =
        if (this is PlayerInventory) {
            36
        } else {
            size
        }

val Inventory.indicies get() = 0 until storageSize

fun Inventory.addAt(slot: Int, addItem: ItemStack, count: Int = addItem.amount): JobResult {
    var fails = count
    val item = getItem(slot)
    if (item.isAir()) {
        val amount = min(count, addItem.maxStackSize)
        setItem(slot, item(addItem) {
            setAmount(amount)
        })
        fails -= amount
    } else if (addItem.isSimilar(item)) {
        val amount = min(addItem.maxStackSize - item.amount, count)
        item.amount += amount
        fails -= amount
    }
    return JobResult(fails)
}

val JOB_RESULT_SUCCESS = JobResult(0)

inline class JobResult(val remain: Int) {
    val success get() = remain <= 0
    val failure get() = !success

    inline fun onSuccess(block: JobResult.() -> Unit): JobResult {
        if (success) block()
        return this
    }

    inline fun onFailure(block: JobResult.() -> Unit): JobResult {
        if (failure) block()
        return this
    }
}

inline fun Inventory.takeItem(count: Int, selector: (ItemStack) -> Boolean): JobResult {
    if (count <= 0) return JOB_RESULT_SUCCESS
    val operations = mutableListOf<() -> Unit>()
    var fails = count
    for (index in indicies) {
        val content = getItem(index)?.takeIf(selector) ?: continue
        val qty = content.amount
        if (qty > fails) {
            operations += { content.amount = qty - fails }
            fails = 0
            break
        } else {
            operations += { setItem(index, null) }
            fails -= qty
            if (fails == 0) {
                break
            }
        }
    }
    return if (fails <= 0) {
        operations.forEach { it() }
        JOB_RESULT_SUCCESS
    } else {
        JobResult(fails)
    }
}

fun Inventory.takeItem(item: ItemStack, takeCount: Int = item.amount) =
    takeItem(takeCount) { item.isSimilar(it) }

fun Inventory.hasItem(count: Int, selector: (ItemStack) -> Boolean): JobResult {
    var remain = count
    for (i in 0 until storageSize) {
        val content = getItem(i)
        if (content != null && selector(content)) {
            if (content.amount >= remain) {
                return JobResult(0)
            }
            remain -= content.amount
        }
    }
    return JobResult(remain)
}

fun Inventory.hasItem(item: ItemStack, count: Int = item.amount) = hasItem(count) { item.isSimilar(it) }

fun Inventory.giveItem(item: ItemStack, giveCount: Int = item.amount): JobResult {
    if (giveCount <= 0) {
        return JOB_RESULT_SUCCESS
    }
    var remain = giveCount
    val size = storageSize
    for (i in 0 until size) {
        val fails = addAt(i, item, remain)
        remain = fails.remain
        if (remain <= 0) {
            break
        }
    }
    return JobResult(remain)
}

fun Inventory.giveItemOrDrop(item: ItemStack, giveCount: Int = item.amount): JobResult {
    val result = giveItem(item, giveCount)
    val loc = location
    val world = loc?.world
    return if (result.failure && loc != null && world != null) {
        world.dropItem(loc.add(0.0, 0.1, 0.0), item(item) { amount = result.remain })
        JOB_RESULT_SUCCESS
    } else {
        result
    }
}

fun Inventory.open(player: HumanEntity) = player.openInventory(this)

fun Inventory.fill(item: ItemStack?) {
    for (i in 0 until size) {
        setItem(i, item)
    }
}

fun Inventory.setItem(item: ItemStack?, vararg indexes: Int) = indexes.forEach { setItem(it, item) }

val Point<Int>.toSlot get() = slot(x, y)

fun Point<Int>.slots(end: Point<Int>): List<Int> {
    val ret = mutableListOf<Int>()
    for (y in this.y..end.y) {
        for (x in this.x..end.x) {
            ret += slot(x, y)
        }
    }
    return ret
}

fun slot(x: Int, y: Int) = y * 9 + x