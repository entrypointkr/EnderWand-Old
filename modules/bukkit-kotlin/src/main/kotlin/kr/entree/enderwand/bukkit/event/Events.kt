package kr.entree.enderwand.bukkit.event

import org.bukkit.event.Cancellable

/**
 * Created by JunHyung Lim on 2020-01-06
 */
inline val Cancellable.isNotCancelled get() = !isCancelled