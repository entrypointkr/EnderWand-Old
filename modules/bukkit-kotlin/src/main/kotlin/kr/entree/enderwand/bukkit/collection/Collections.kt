package kr.entree.enderwand.bukkit.collection

import org.bukkit.entity.Entity
import java.util.*

/**
 * Created by JunHyung Lim on 2020-01-26
 */
operator fun Iterable<UUID>.contains(entity: Entity) = contains(entity.uniqueId)

operator fun MutableCollection<UUID>.plusAssign(entity: Entity) = plusAssign(entity.uniqueId)

fun MutableCollection<UUID>.add(entity: Entity) = add(entity.uniqueId)

operator fun MutableCollection<UUID>.minusAssign(entity: Entity) = minusAssign(entity.uniqueId)

fun MutableCollection<UUID>.remove(entity: Entity) = remove(entity.uniqueId)