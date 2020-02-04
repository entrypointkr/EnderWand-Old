package enderwand.bukkit.world

import org.bukkit.block.Block
import org.bukkit.block.BlockFace

/**
 * Created by JunHyung Lim on 2020-01-22
 */
fun Block.up() = getRelative(BlockFace.UP)

fun Block.down() = getRelative(BlockFace.DOWN)

inline fun Block.downDepth(filter: (Block) -> Boolean): Int {
    var depth = if (filter(this)) 1 else 0
    for (y in (location.blockY - 1) downTo 0) {
        val block = world.getBlockAt(location.blockX, y, location.blockZ)
        if (filter(block)) {
            depth++
        } else break
    }
    return depth
}