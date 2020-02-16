@file:Suppress("EXPERIMENTAL_API_USAGE")

package kr.entree.enderwand.bukkit.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.bukkit.event.isMainHand
import kr.entree.enderwand.bukkit.reactor.*
import kr.entree.enderwand.reactor.Registry
import kr.entree.enderwand.reactor.SimpleReactorContext
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import java.util.logging.Level

/**
 * Created by JunHyung Lim on 2020-02-15
 */
suspend fun <T, R> awaitReact(
    registry: Registry<UUID, T>,
    transform: (T) -> R
): R {
    delay(1)
    return suspendCancellableCoroutine { continuation ->
        val context = SimpleReactorContext().apply {
            continuation.invokeOnCancellation {
                cancel()
            }
            onCancelledHandler = {
                continuation.cancel()
            }
        }
        registry(context) {
            if (continuation.isActive) {
                continuation.apply {
                    Dispatchers.Bukkit.resumeUndispatched(transform(result))
                }
                cancel()
            } else {
                val message = StringWriter()
                RuntimeException().printStackTrace(PrintWriter(message))
                enderWand.logger.apply {
                    log(Level.WARNING, "Something went wrong!")
                    log(Level.WARNING, message.toString())
                    dispose()
                    log(Level.WARNING, "Tried to disposing actor")
                }
            }
        }
    }
}

suspend fun Player.awaitChat() = awaitReact(onChat) { it.message }

suspend fun Player.awaitInteract(): Block {
    return awaitReact(onInteract) { it }.run {
        if (clickedBlock != null && isMainHand) {
            clickedBlock!!
        } else {
            awaitInteract()
        }
    }
}

suspend fun Player.awaitInteractEntity() = awaitReact(onInteractEntity) { it.rightClicked }

suspend fun Player.awaitMove() = awaitReact(onMove) { it.from to it.to }

suspend fun Player.awaitResourcePack() = awaitReact(onResourcePack) { it.status }