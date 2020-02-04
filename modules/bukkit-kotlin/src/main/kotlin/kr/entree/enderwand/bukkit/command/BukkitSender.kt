package kr.entree.enderwand.bukkit.command

import kr.entree.enderwand.bukkit.message.colorize
import kr.entree.enderwand.command.sender.Sender
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Created by JunHyung Lim on 2019-12-18
 */
class BukkitSender(
    val bukkitSender: CommandSender
) : Sender, CommandSender by bukkitSender {
    val player get() = bukkitSender as? Player
    val isPlayer get() = bukkitSender is Player

    override fun sendMessage(message: Any) {
        bukkitSender.sendMessage(message.toString())
    }

    override fun sendError(errorMessage: Any) {
        bukkitSender.sendMessage("&c$errorMessage".colorize())
    }

    override fun hasPermission(node: String): Boolean {
        return bukkitSender.hasPermission(node)
    }

    override fun isOp(): Boolean {
        return bukkitSender.isOp
    }
}