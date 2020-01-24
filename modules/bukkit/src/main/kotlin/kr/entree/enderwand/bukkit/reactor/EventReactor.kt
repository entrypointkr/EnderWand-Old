package kr.entree.enderwand.bukkit.reactor

import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.bukkit.scheduler.scheduler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.*

/**
 * Created by JunHyung Lim on 2020-01-08
 */
/**
 * Created by JunHyung Lim on 2019-12-21
 */
class EventReactor : Listener {
    val interact = playerReactor<PlayerInteractEvent>()
    val interactEntity = playerReactor<PlayerInteractEntityEvent>()
    val interactAny = playerReactor<PlayerEvent>()
    val move = playerReactor<PlayerMoveEvent>()
    val chat = playerReactor<AsyncPlayerChatEvent>()
    val chatAsync = playerReactor<AsyncPlayerChatEvent>()
    val playerReactors = listOf(interact, interactEntity, interactAny, move, chat, chatAsync)

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        playerReactors.forEach { it.remove(e.player) }
    }

    @EventHandler
    fun onClick(e: PlayerInteractEvent) {
        interact.notify(e)
        interactAny.notify(e)
    }

    @EventHandler
    fun onClickEntity(e: PlayerInteractEntityEvent) {
        interactEntity.notify(e)
        interactAny.notify(e)
    }

    @EventHandler
    fun onMove(e: PlayerMoveEvent) {
        move.notify(e)
    }

    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {
        chatAsync.notify(e)
        enderWand.scheduler.runTask { chat.notify(e) }
    }
}