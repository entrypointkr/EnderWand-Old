package kr.entree.enderwand.bukkit.coroutine

import kotlinx.coroutines.CoroutineScope
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

/**
 * Created by JunHyung Lim on 2020-02-15
 */
interface PluginCoroutineScope<T : Plugin> : CoroutineScope, Plugin {
    val plugin: T
}

interface PluginEntityCoroutineScope<E : Entity, T : Plugin> : PluginCoroutineScope<T> {
    val entity: E
}

class PluginCoroutineScopeImpl<T : Plugin>(
    override val plugin: T,
    val delegate: CoroutineScope
) : PluginCoroutineScope<T>, Plugin by plugin, CoroutineScope by delegate

class PluginEntityCoroutineScopeImpl<E : Entity, T : Plugin>(
    override val entity: E,
    private val pluginScope: PluginCoroutineScope<T>
) : PluginEntityCoroutineScope<E, T>, Entity by entity, PluginCoroutineScope<T> by pluginScope {
    override fun getName() = entity.name

    override fun getServer() = entity.server
}

class PluginPlayerCoroutineScope<T : Plugin>(
    private val player: Player,
    private val pluginScope: PluginCoroutineScope<T>
) : PluginEntityCoroutineScope<Player, T>, Player by player, PluginCoroutineScope<T> by pluginScope {
    override val entity get() = player

    override fun getPlayer() = player

    override fun getName() = player.name

    override fun getServer() = player.server
}

suspend fun <E : Entity, P : Plugin> PluginCoroutineScope<P>.onEntity(
    entity: E,
    block: suspend PluginEntityCoroutineScopeImpl<E, P>.() -> Unit
) = block(PluginEntityCoroutineScopeImpl(entity, this))

suspend fun <P : Plugin> PluginCoroutineScope<P>.on(
    player: Player,
    block: suspend PluginPlayerCoroutineScope<P>.() -> Unit
) = block(PluginPlayerCoroutineScope(player, this))