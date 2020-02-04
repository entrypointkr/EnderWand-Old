package enderwand.bukkit.reactor

import enderwand.reactor.FunctionalMapReactor
import enderwand.reactor.simpleReactor
import org.bukkit.entity.Entity
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityEvent
import org.bukkit.event.player.PlayerEvent
import java.util.*

/**
 * Created by JunHyung Lim on 2020-01-08
 */
fun <T> entityReactor() =
    playerReactor<T>().apply {
        FunctionalMapReactor(factory, {
            when (it) {
                is EntityEvent -> {
                    it.entity
                }
                else -> null
            }?.uniqueId ?: identifier(it)
        })
    }

fun <T> playerReactor() =
    simpleReactor<UUID, T> {
        val player = when (it) {
            is PlayerEvent -> {
                it.player
            }
            is BlockPlaceEvent -> {
                it.player
            }
            is BlockBreakEvent -> {
                it.player
            }
            else -> null
        }
        player?.uniqueId
    }

fun <V> FunctionalMapReactor<UUID, V>.remove(entity: Entity) = remove(entity.uniqueId)