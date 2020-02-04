package enderwand.bukkit.time

import enderwand.time.CooldownMap
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import java.time.Duration
import java.util.*

/**
 * Created by JunHyung Lim on 2020-01-10
 */
typealias UUIDCooldown = CooldownMap<UUID>

typealias NameCooldown = CooldownMap<String>

fun CooldownMap<UUID>.get(entity: Entity) = get(entity.uniqueId)

fun CooldownMap<String>.get(sender: CommandSender) = get(sender.name)

fun CooldownMap<UUID>.action(entity: Entity, cool: Duration) = action(entity.uniqueId, cool)

fun CooldownMap<String>.action(sender: CommandSender, cool: Duration) = action(sender.name, cool)