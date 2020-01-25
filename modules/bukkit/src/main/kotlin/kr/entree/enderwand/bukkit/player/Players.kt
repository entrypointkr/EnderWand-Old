package kr.entree.enderwand.bukkit.player

import kr.entree.enderwand.bukkit.command.BukkitSender
import kr.entree.enderwand.bukkit.exception.UnknownPlayerException
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import java.util.*

/**
 * Created by JunHyung Lim on 2019-12-31
 */
fun String.toPlayer() = Bukkit.getPlayer(this)

@Suppress("DEPRECATION")
fun String.toOfflinePlayer() =
    runCatching {
        UUID.fromString(this)
    }.getOrNull()?.toOfflinePlayer() ?: Bukkit.getOfflinePlayer(this)

fun UUID.toPlayer() = Bukkit.getPlayer(this)

fun UUID.toOfflinePlayer() = Bukkit.getOfflinePlayer(this)

fun UUID.getPlayerName(defaultName: String = "???") = toOfflinePlayer().name ?: defaultName

fun CommandSender.toPlayer() = this as? Player

fun CommandSender.toPlayerOrThrow() = toPlayer() ?: throw UnknownPlayerException(name)

fun BukkitSender.toPlayerOrThrow() = player ?: throw UnknownPlayerException(name)

val HumanEntity.itemInMainHand get() = inventory.itemInMainHand

fun CommandSender.execute(commandLine: String) = Bukkit.dispatchCommand(this, commandLine)

inline fun CommandSender.sudo(block: CommandSender.() -> Unit) {
    val wasUser = isOp
    if (wasUser) {
        isOp = true
    }
    block()
    if (wasUser) {
        isOp = false
    }
}

fun CommandSender.executeAsOp(commandLine: String) = sudo { execute(commandLine) }