package kr.entree.enderwand.bukkit.inventory

import kr.entree.enderwand.bukkit.item.emptyItem
import kr.entree.enderwand.bukkit.item.isAir
import kr.entree.enderwand.bukkit.item.item
import kr.entree.enderwand.command.validate
import kr.entree.enderwand.math.Point
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory

/**
 * Created by JunHyung Lim on 2020-01-01
 */
val Inventory.storageSize
    get() =
        if (this is PlayerInventory) {
            36
        } else {
            size
        }

fun Inventory.asIterable() = Iterable { iterator() }.mapNotNull { it ?: emptyItem() }.withIndex()

fun Inventory.asSequence() = Sequence { iterator() }.mapNotNull { it ?: emptyItem() }.withIndex()

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

fun Inventory.takeItem(count: Int, hasItemsByIndex: List<IndexedValue<Int>>): JobResult {
    if (count <= 0) JobResult.SUCCESS
    val hasCount = hasItemsByIndex.sumBy { it.value }
    return JobResult(
        if (hasCount >= count) {
            var remain = count
            for ((index, amount) in hasItemsByIndex) {
                if (remain <= 0) break
                val slotItem = getItem(index) ?: continue
                takeAt(index, slotItem, amount.coerceAtMost(remain))
                remain -= amount
            }
            0
        } else {
            count - hasCount
        }
    )
}

inline fun Inventory.takeItem(
    count: Int,
    hasItemsByIndex: List<IndexedValue<Int>>? = null,
    selector: (ItemStack) -> Boolean
) = takeItem(count, hasItemsByIndex ?: hasItems(selector))

fun Inventory.takeItem(item: ItemStack, count: Int = item.amount, hasItemsByIndex: List<IndexedValue<Int>>? = null) =
    takeItem(count, hasItemsByIndex ?: hasItems { it.isSimilar(item) })

inline fun Inventory.hasItems(selector: (ItemStack) -> Boolean) =
    asIterable().filter { (_, item) -> selector(item) }
        .map { (index, item) -> IndexedValue(index, item.amount) }

fun Inventory.hasItems(item: ItemStack) = hasItems { it.isSimilar(item) }

fun Inventory.hasItem(item: ItemStack, count: Int = item.amount) =
    hasItems { item.isSimilar(it) }.sum() >= count

fun Inventory.giveItem(
    item: ItemStack,
    giveCount: Int = item.amount,
    spaceBySlot: List<IndexedValue<Int>>? = null
): JobResult {
    if (giveCount <= 0) {
        return JobResult.SUCCESS
    }
    val spaces = spaceBySlot ?: hasSpaces(item)
    val totalSpace = spaces.sumBy { it.value }
    return if (totalSpace >= giveCount) {
        spaces.forEach { (index, amount) -> addAt(index, item(item, amount)) }
        JobResult.SUCCESS
    } else {
        JobResult(totalSpace - giveCount)
    }
}

fun Inventory.giveItemOrDrop(item: ItemStack, giveCount: Int = item.amount): JobResult {
    val result = giveItem(item, giveCount)
    val loc = location
    val world = loc?.world
    return if (result.failure && loc != null && world != null) {
        world.dropItem(loc.add(0.0, 0.1, 0.0), item(item) { amount = result.remain })
        JobResult.SUCCESS
    } else {
        result
    }
}

fun Inventory.hasSpaces(item: ItemStack) =
    asIterable().map { (index, element) ->
        IndexedValue(
            index, when {
                element.isAir() -> item.maxStackSize
                element.isSimilar(item) -> (item.maxStackSize - element.amount).coerceAtMost(0)
                else -> 0
            }
        )
    }.filter { it.value > 0 }

fun Inventory.open(player: HumanEntity) = player.openInventory(this)

fun Inventory.fill(item: ItemStack?) {
    for (i in 0 until size) {
        setItem(i, item)
    }
}

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

private val PATTERNS =
    ('1'..'9').union('a'..'z').union('A'..'Z')
        .mapIndexed { index, char -> char to index }
        .toMap()

fun slotPatterns(vararg patterns: String): List<Int> {
    val ret = mutableListOf<Pair<Int, Int>>()
    for ((y, pattern) in patterns.withIndex()) {
        validate(pattern.length == 9, "Length must be 9")
        for ((x, char) in pattern.withIndex()) {
            val priority = PATTERNS[char] ?: continue
            ret += slot(x, y) to priority
        }
    }
    return ret.sortedBy { it.second }.map { it.first }
}

inline class JobResult(val remain: Int) {
    val success get() = remain <= 0
    val failure get() = !success

    companion object {
        val SUCCESS = JobResult(0)
    }

    inline fun onSuccess(block: JobResult.() -> Unit): JobResult {
        if (success) block(this)
        return this
    }

    inline fun onFailure(block: JobResult.() -> Unit): JobResult {
        if (failure) block(this)
        return this
    }
}

fun List<IndexedValue<Int>>.sum() = sumBy { it.value }