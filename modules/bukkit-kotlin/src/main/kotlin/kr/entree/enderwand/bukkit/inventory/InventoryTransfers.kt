package kr.entree.enderwand.bukkit.inventory

import kr.entree.enderwand.bukkit.item.emptyItem
import kr.entree.enderwand.bukkit.item.isAir
import kr.entree.enderwand.bukkit.item.space
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * Created by JunHyung Lim on 2020-03-09
 */
fun Inventory.asIterable() = Iterable { iteratorOfStorage() }.map { getItem(it) ?: emptyItem() }

fun Inventory.asSequence() = Sequence { iteratorOfStorage() }.mapNotNull { getItem(it) ?: emptyItem() }

inline fun Inventory.takeItem(
    takeAmount: Int,
    crossinline selector: (ItemStack) -> Boolean
): JobResult {
    if (takeAmount <= 0) return JobResult.SUCCESS
    var hasAmount = 0
    val slotItems = mutableListOf<Pair<Int, ItemStack>>()
    for ((index, item) in withIndex()) {
        if (item != null && selector(item)) {
            hasAmount += item.amount
            slotItems += index to item
        }
        if (hasAmount >= takeAmount) break
    }
    return if (hasAmount >= takeAmount) {
        var remain = takeAmount
        for ((slot, item) in slotItems) {
            takeAt(slot, item, item.amount.coerceAtMost(remain))
            remain -= item.amount
            if (remain <= 0) break
        }
        JobResult.SUCCESS
    } else {
        JobResult(takeAmount - hasAmount)
    }
}

fun Inventory.takeItem(
    item: ItemStack,
    takeAmount: Int = item.amount
) = takeItem(takeAmount) { item.isSimilar(it) }

inline fun Inventory.filter(
    crossinline selector: (ItemStack) -> Boolean
) = asIterable().filter { selector(it) }

inline fun Inventory.hasItems(
    crossinline selector: (ItemStack) -> Boolean
) = filter(selector).sumBy { it.amount }

fun Inventory.hasItems(item: ItemStack) = hasItems { it.isSimilar(item) }

inline fun Inventory.hasItem(
    amount: Int,
    crossinline selector: (ItemStack) -> Boolean
) = hasItems(selector) >= amount

fun Inventory.hasItem(
    item: ItemStack,
    amount: Int = item.amount
) = hasItem(amount) { it.isSimilar(item) }

fun Inventory.giveItem(
    giveItem: ItemStack,
    giveCount: Int = giveItem.amount
): JobResult {
    if (giveCount <= 0) return JobResult.SUCCESS
    val spaceBySlot = mutableListOf<Pair<Int, Int>>()
    var totalSpace = 0
    for ((slot, item) in asIterable().withIndex()) {
        val space = item.space(giveItem)
        if (space >= 1) {
            spaceBySlot += slot to space
        }
        totalSpace += space
    }
    return if (totalSpace >= giveCount) {
        var remain = giveCount
        for ((slot, space) in spaceBySlot) {
            addAt(slot, ItemStack(giveItem).apply {
                amount = space.coerceAtMost(remain)
            })
            remain -= space
        }
        JobResult.SUCCESS
    } else {
        JobResult(giveCount - totalSpace)
    }
}

fun Inventory.giveItemOrDrop(item: ItemStack, giveCount: Int = item.amount): JobResult {
    val result = giveItem(item, giveCount)
    if (result.success) return result
    val loc = location ?: return result
    val world = loc.world ?: return result
    world.dropItem(loc.add(0.0, 0.1, 0.0), ItemStack(item).apply {
        amount = result.remain
    })
    return JobResult.SUCCESS
}

fun Inventory.spaceAt(slot: Int, item: ItemStack): Int {
    val slotItem = getItem(slot) ?: return item.maxStackSize
    return slotItem.space(item)
}

fun Inventory.hasSpaces(item: ItemStack) =
    asIterable().sumBy {
        when {
            it.isAir() -> item.maxStackSize
            it.isSimilar(item) -> (item.maxStackSize - it.amount).coerceAtLeast(0)
            else -> 0
        }
    }

fun Inventory.hasSpace(item: ItemStack, amount: Int = item.amount) = hasSpaces(item) >= amount