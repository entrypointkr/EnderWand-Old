package kr.entree.enderwand.bukkit.event

import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor

/**
 * Created by JunHyung Lim on 2020-02-18
 */
class FunctionalEventExecutor(
    private val ignoreCancelled: Boolean,
    private val function: FunctionalEventExecutor.(Listener, Event) -> Unit
) : EventExecutor, Listener {
    override fun execute(listener: Listener, event: Event) {
        if (event is Cancellable && event.isCancelled && ignoreCancelled) {
            return
        }
        function(listener, event)
    }

    fun unregister() = HandlerList.unregisterAll(this)
}