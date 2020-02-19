package kr.entree.enderwand.bukkit.sender

import kr.entree.enderwand.bukkit.message.colorize
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

/**
 * Created by JunHyung Lim on 2020-01-09
 */
fun CommandSender.tell(message: String, altChar: Char = '&') = sendMessage(message.colorize(altChar))

fun CommandSender.execute(commandLine: String) = Bukkit.dispatchCommand(this, commandLine)

inline fun <T : CommandSender> T.sudo(block: T.() -> Unit) {
    val wasUser = !isOp
    if (wasUser) {
        isOp = true
    }
    block()
    if (wasUser) {
        isOp = false
    }
}

fun CommandSender.executeAsOp(commandLine: String) = sudo { execute(commandLine) }