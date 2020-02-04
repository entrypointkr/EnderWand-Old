package enderwand.bukkit.world

import org.bukkit.Chunk
import org.bukkit.block.Block

/**
 * Created by JunHyung Lim on 2020-01-22
 */
inline fun Chunk.getBlockHighest(x: Int, z: Int, picker: (Block) -> Boolean): Block {
    var last = getBlock(x, 0, z)
    for (y in 1 until world.maxHeight) {
        val block = getBlock(x, y, z)
        if (picker(block)) {
            last = block
        }
    }
    return last
}