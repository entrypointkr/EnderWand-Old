package kr.entree.enderwand.bukkit.event

import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerEvent

/**
 * Created by JunHyung Lim on 2020-01-06
 */
inline val Cancellable.isNotCancelled get() = !isCancelled

fun Event.findPlayer() =
    when (this) {
        is PlayerEvent -> {
            player
        }
        is BlockPlaceEvent -> {
            player
        }
        is BlockBreakEvent -> {
            player
        }
        else -> null
    }