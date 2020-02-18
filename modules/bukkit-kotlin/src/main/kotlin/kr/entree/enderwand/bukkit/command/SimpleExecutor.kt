package kr.entree.enderwand.bukkit.command

import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.collection.toReader
import kr.entree.enderwand.command.CommandContext
import kr.entree.enderwand.command.CommandException
import kr.entree.enderwand.command.CommandTrouble
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.util.logging.Level

/**
 * Created by JunHyung Lim on 2020-01-10
 */
abstract class SimpleExecutor(
    private val handler: CommandTrouble.() -> Unit = {
        val ex = exception
        if (ex is CommandException) {
            ctx.sender.tell("&c${ex.errorMessage}")
        } else {
            ctx.sender.tell("오류가 발생했습니다. $exception")
            enderWand.logger.log(Level.WARNING, exception) {
                "Uncaught Exception!"
            }
        }
    }
) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        try {
            execute(sender, label, args)
        } catch (ex: Exception) {
            handler(CommandTrouble(CommandContext(BukkitSender(sender), args.toReader()), ex))
        }
        return true
    }

    abstract fun execute(sender: CommandSender, label: String, args: Array<String>)
}