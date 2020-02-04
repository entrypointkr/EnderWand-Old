package enderwand.bukkit.command

import enderwand.bukkit.enderWand
import enderwand.collection.toReader
import enderwand.command.CommandContext
import enderwand.command.CommandException
import enderwand.command.CommandTrouble
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
            ctx.sender.sendMessage("&c${ex.errorMessage}")
        } else {
            ctx.sender.sendMessage("오류가 발생했습니다. $exception")
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