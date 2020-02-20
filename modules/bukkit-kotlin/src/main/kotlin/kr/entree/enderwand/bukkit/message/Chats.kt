package kr.entree.enderwand.bukkit.message

import kr.entree.enderwand.bukkit.sender.tell
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import java.util.regex.Pattern

/**
 * Created by JunHyung Lim on 2020-01-09
 */
fun String.colorize(altColorChar: Char = '&') =
    ChatColor.translateAlternateColorCodes(altColorChar, this)

fun String.broadcast() = Bukkit.broadcastMessage(toString())

fun CharSequence.sendTo(sender: CommandSender) = sender.tell(toString())

val STRIP_COLORS_PATTERN = Pattern.compile("(?i)[${ChatColor.COLOR_CHAR}|&][0-9A-FK-OR]")

fun String.stripColor() = STRIP_COLORS_PATTERN.matcher(this).replaceAll("")