package kr.entree.enderwand.bukkit.reactor

import kr.entree.enderwand.bukkit.event.findPlayer
import kr.entree.enderwand.reactor.FunctionalMapReactor
import kr.entree.enderwand.reactor.simpleReactor
import org.bukkit.entity.Entity
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityEvent
import java.util.*

/**
 * Created by JunHyung Lim on 2020-01-08
 */
fun <T : Event> eventEntityReactor() =
    eventPlayerReactor<T>().apply {
        FunctionalMapReactor(factory, {
            when (it) {
                is EntityEvent -> {
                    it.entity
                }
                else -> null
            }?.uniqueId ?: identifier(it)
        })
    }

fun <T : Event> eventPlayerReactor() =
    simpleReactor<UUID, T> {
        it.findPlayer()?.uniqueId
    }

fun <V> FunctionalMapReactor<UUID, V>.remove(entity: Entity) = remove(entity.uniqueId)