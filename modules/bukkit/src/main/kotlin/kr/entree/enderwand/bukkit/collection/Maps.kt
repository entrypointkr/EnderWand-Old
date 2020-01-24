package kr.entree.enderwand.bukkit.collection

import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import java.util.*

/**
 * Created by JunHyung Lim on 2020-01-12
 */
fun <V> MutableMap<UUID, V>.put(entity: Entity, value: V) =
    put(entity.uniqueId, value)

fun <V> MutableMap<String, V>.put(sender: CommandSender, value: V) =
    put(sender.name, value)

fun <V> MutableMap<UUID, V>.remove(entity: Entity) =
    remove(entity.uniqueId)

fun <V> MutableMap<String, V>.remove(sender: CommandSender) =
    remove(sender.name)

operator fun <V> Map<UUID, V>.get(entity: Entity) = get(entity.uniqueId)

fun <V> Map<String, V>.get(sender: CommandSender) = get(sender.name)