package kr.entree.enderwand.bukkit.coroutine

import kotlinx.coroutines.*
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
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by JunHyung Lim on 2020-01-09
 */
val <T : Plugin> T.scope
    get() = PluginCoroutineScopeImpl(this, CoroutineScope(SchedulerDispatcher(scheduler)))

inline fun <T : Plugin> T.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    crossinline block: suspend PluginCoroutineScopeImpl<T>.() -> Unit
) = scope.run { delegate.launch(context, start) { block() } }

@UseExperimental(ExperimentalCoroutinesApi::class)
suspend inline fun <reified T : Event> Plugin.awaitOn(
    priority: EventPriority = EventPriority.MONITOR,
    ignoreCancelled: Boolean = true
) = suspendCancellableCoroutine<T> { continuation ->
    val listener = on<T>(priority, ignoreCancelled) {
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

suspend inline fun Plugin.awaitJoin(filter: (Player) -> Boolean): Player {
    while (true) {
        val joinEvent = awaitOn<PlayerJoinEvent>()
        if (filter(joinEvent.player)) {
            return joinEvent.player
        }
    }
}

suspend fun Plugin.awaitJoin(uuid: UUID) = awaitJoin { it.uniqueId == uuid }

suspend fun Plugin.awaitJoin(name: String) = awaitJoin { it.name.equals(name, true) }

suspend inline fun <reified T : Event, P : Plugin> PluginEntityCoroutineScopeImpl<Player, P>.awaitOn(): T {
    while (true) {
        val event = awaitOn<T>()
        if (event.findPlayer()?.uniqueId == uniqueId)
            return event
    }
}

suspend fun <T : Plugin> PluginEntityCoroutineScope<Player, T>.awaitChat() = awaitOn<AsyncPlayerChatEvent>().message

suspend fun <T : Plugin> PluginEntityCoroutineScope<Player, T>.awaitInteract(): Block {
    while (true) {
        val event = awaitOn<PlayerInteractEvent>()
        val clickedBlock = event.clickedBlock
        if (clickedBlock != null) {
            return clickedBlock
        }
    }
}

suspend fun <T : Plugin> PluginEntityCoroutineScope<Player, T>.awaitMove(): Pair<Any, Any?> =
    awaitOn<PlayerMoveEvent>().run { from to to }

suspend fun <T : Plugin> PluginEntityCoroutineScope<Player, T>.awaitResourcePackStatus() =
    awaitOn<PlayerResourcePackStatusEvent>().status

inline fun <T : Plugin> Player.onAction(
    plugin: T,
    dispatcher: BukkitDispatcher = Dispatchers.Bukkit,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    crossinline block: suspend PluginPlayerCoroutineScope<T>.() -> Unit
) = PluginPlayerCoroutineScope(this, plugin.scope).run {
    launch(dispatcher, start) { block() }
}