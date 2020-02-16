package kr.entree.enderwand.bukkit.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.entree.enderwand.bukkit.enderWand
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

/**
 * Created by JunHyung Lim on 2020-02-15
 */
interface BukkitEntityCoroutineScope<E : Entity> : CoroutineScope {
    val entity: E
}

class PlayerCoroutineScope(
    private val delegate: CoroutineScope,
    private val player: Player
) : BukkitEntityCoroutineScope<Player>, Player by player {
    override val entity get() = player
    override val coroutineContext get() = delegate.coroutineContext
}

fun Player.onAction(
    dispatcher: BukkitDispatcher = Dispatchers.Bukkit,
    plugin: Plugin = enderWand,
    block: suspend PlayerCoroutineScope.() -> Unit
) {
    plugin.scope.launch(dispatcher) {
        block(PlayerCoroutineScope(this, this@onAction))
    }
}