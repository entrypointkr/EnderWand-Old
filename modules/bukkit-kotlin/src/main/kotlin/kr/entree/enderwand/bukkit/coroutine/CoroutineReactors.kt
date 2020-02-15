@file:Suppress("EXPERIMENTAL_API_USAGE")

package kr.entree.enderwand.bukkit.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kr.entree.enderwand.bukkit.reactor.*
import kr.entree.enderwand.reactor.Registry
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.util.*

/**
 * Created by JunHyung Lim on 2020-02-15
 */
suspend fun <T, R> awaitReact(
    registry: Registry<UUID, T>,
    transform: (T) -> R
) = suspendCancellableCoroutine<R> { continuation ->
    registry {
        continuation.apply {
            Dispatchers.Bukkit.resumeUndispatched(transform(result))
            invokeOnCancellation {
                this@registry.cancel()
            }
        }
        onCancelledHandler = {
            continuation.cancel()
        }
        cancel()
    }
}

suspend fun Player.awaitChat() = awaitReact(onChat) { it.message }

suspend fun Player.awaitInteract(): Block {
    return awaitReact(onInteract) { it.clickedBlock } ?: awaitInteract()
}

suspend fun Player.awaitInteractEntity() = awaitReact(onInteractEntity) { it.rightClicked }

suspend fun Player.awaitMove() = awaitReact(onMove) { it.from to it.to }

suspend fun Player.awaitResourcePack() = awaitReact(onResourcePack) { it.status }