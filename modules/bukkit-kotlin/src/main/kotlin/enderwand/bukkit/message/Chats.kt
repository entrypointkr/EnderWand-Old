package enderwand.bukkit.message

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

/**
 * Created by JunHyung Lim on 2020-01-09
 */
fun String.colorize(altColorChar: Char = '&') =
    ChatColor.translateAlternateColorCodes(altColorChar, this)

fun String.broadcast() = Bukkit.broadcastMessage(toString())

fun CharSequence.sendTo(sender: CommandSender) = sender.sendMessage(toString())