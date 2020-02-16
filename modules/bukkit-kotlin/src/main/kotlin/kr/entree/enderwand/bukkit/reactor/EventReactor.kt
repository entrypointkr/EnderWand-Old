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
    val interact = eventPlayerReactor<PlayerInteractEvent>()
    val interactEntity = eventPlayerReactor<PlayerInteractEntityEvent>()
    val interactAny = eventPlayerReactor<PlayerEvent>()
    val move = eventPlayerReactor<PlayerMoveEvent>()
    val chat = eventPlayerReactor<AsyncPlayerChatEvent>()
    val chatAsync = eventPlayerReactor<AsyncPlayerChatEvent>()
    val resourcePack = eventPlayerReactor<PlayerResourcePackStatusEvent>()
    val playerReactors = listOf(interact, interactEntity, interactAny, move, chat, chatAsync, resourcePack)

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

    @EventHandler
    fun onResourcePack(e: PlayerResourcePackStatusEvent) = resourcePack.notify(e)
}