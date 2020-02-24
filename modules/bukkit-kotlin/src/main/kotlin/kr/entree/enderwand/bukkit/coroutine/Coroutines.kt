package kr.entree.enderwand.bukkit.coroutine

import kotlinx.coroutines.*
import kr.entree.enderwand.bukkit.event.findPlayer
import kr.entree.enderwand.bukkit.event.on
import kr.entree.enderwand.bukkit.scheduler.scheduler
import kr.entree.enderwand.coroutine.SchedulerDispatcher
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.player.*
import org.bukkit.plugin.Plugin
import java.time.Duration
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by JunHyung Lim on 2020-01-09
 */
val <T : Plugin> T.scope
    get() = PluginCoroutineScopeImpl(this, CoroutineScope(SchedulerDispatcher(scheduler)))

inline fun <T : Plugin> T.launch(
    context: CoroutineContext = Dispatchers.Bukkit,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    crossinline block: suspend PluginCoroutineScope<T>.() -> Unit
) = scope.run { delegate.launch(context, start) { block() } }

suspend fun delay(duration: Duration) = delay(duration.toMillis())

@UseExperimental(ExperimentalCoroutinesApi::class)
suspend inline fun <reified T : Event> Plugin.awaitOn(
    priority: EventPriority = EventPriority.NORMAL,
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

suspend inline fun <reified T : Event, E : Entity, P : Plugin> PluginEntityCoroutineScope<E, out P>.awaitWhile(filter: (T) -> Boolean): T {
    while (isActive) {
        val event = plugin.awaitOn<T>()
        if (filter(event))
            return event
    }
    throw CancellationException()
}

suspend inline fun <reified T : Event> PluginEntityCoroutineScope<Player, out Plugin>.awaitUnique() =
    awaitWhile<T, Player, Plugin> {
        it.findPlayer()?.uniqueId == entity.uniqueId
    }

suspend inline fun <reified T : Event> PluginEntityCoroutineScope<Player, out Plugin>.awaitInput() =
    awaitUnique<T>().also { event ->
        if (event is Cancellable) {
            event.isCancelled = true
        }
    }

suspend fun <T : Plugin> PluginEntityCoroutineScope<Player, T>.awaitChat() = awaitInput<AsyncPlayerChatEvent>().message

suspend fun <T : Plugin> PluginEntityCoroutineScope<Player, T>.awaitInteract(): Block {
    while (isActive) {
        val event = awaitInput<PlayerInteractEvent>()
        val clickedBlock = event.clickedBlock
        if (clickedBlock != null) {
            return clickedBlock
        }
    }
    throw CancellationException()
}

suspend fun <T : Plugin> PluginEntityCoroutineScope<Player, T>.awaitMove(): Pair<Any, Any?> =
    awaitInput<PlayerMoveEvent>().run { from to to }

suspend fun <T : Plugin> PluginEntityCoroutineScope<Player, T>.awaitResourcePackStatus() =
    awaitInput<PlayerResourcePackStatusEvent>().status

inline fun <T : Plugin> Player.onAction(
    plugin: T,
    dispatcher: BukkitDispatcher = Dispatchers.Bukkit,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    crossinline block: suspend PluginPlayerCoroutineScope<T>.() -> Unit
) = PluginPlayerCoroutineScope(this, plugin.scope).run {
    launch(dispatcher, start) {
        val job = launch { block() }
        withContext(job) {
            awaitUnique<PlayerQuitEvent>()
            delay(1)
            if (isCancelWhenQuit) {
                cancel()
            }
        }
    }
}