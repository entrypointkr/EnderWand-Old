package kr.entree.enderwand.bukkit.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.bukkit.event.findPlayer
import kr.entree.enderwand.bukkit.event.on
import kr.entree.enderwand.bukkit.scheduler.scheduler
import kr.entree.enderwand.coroutine.SchedulerDispatcher
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.player.*
import org.bukkit.plugin.Plugin
import java.util.*

/**
 * Created by JunHyung Lim on 2020-01-09
 */
val Plugin.scope get() = CoroutineScope(SchedulerDispatcher(scheduler))

@UseExperimental(ExperimentalCoroutinesApi::class)
suspend inline fun <reified T : Event> awaitOn(
    priority: EventPriority = EventPriority.MONITOR,
    ignoreCancelled: Boolean = true,
    plugin: Plugin = enderWand
) = suspendCancellableCoroutine<T> { continuation ->
    val listener = plugin.on<T> {
        if (continuation.isActive) {
            continuation.apply {
                Dispatchers.Bukkit.resumeUndispatched(this@on)
            }
        } else {
            it.unregister() // It maybe causes lag
        }
    }
    continuation.invokeOnCancellation {
        listener.unregister()
    }
}

suspend inline fun awaitJoin(filter: (Player) -> Boolean): Player {
    while (true) {
        val joinEvent = awaitOn<PlayerJoinEvent>()
        if (filter(joinEvent.player)) {
            return joinEvent.player
        }
    }
}

suspend fun awaitJoin(uuid: UUID) = awaitJoin { it.uniqueId == uuid }

suspend fun awaitJoin(name: String) = awaitJoin { it.name.equals(name, true) }

suspend inline fun <reified T : Event> Player.awaitOn(): T {
    while (true) {
        val event = kr.entree.enderwand.bukkit.coroutine.awaitOn<T>()
        if (event.findPlayer()?.uniqueId == uniqueId)
            return event
    }
}

suspend fun Player.awaitChat() = awaitOn<AsyncPlayerChatEvent>().message

suspend fun Player.awaitInteract(): Block {
    while (true) {
        val event = awaitOn<PlayerInteractEvent>()
        val clickedBlock = event.clickedBlock
        if (clickedBlock != null) {
            return clickedBlock
        }
    }
}

suspend fun Player.awaitMove() = awaitOn<PlayerMoveEvent>().run { from to to }

suspend fun Player.awaitResourcePackStatus() = awaitOn<PlayerResourcePackStatusEvent>().status