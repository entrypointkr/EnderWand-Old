package kr.entree.enderwand.bukkit.sender

import kr.entree.enderwand.bukkit.message.colorize
import org.bukkit.command.CommandSender

/**
 * Created by JunHyung Lim on 2020-01-09
 */
fun CommandSender.tell(message: String, altChar: Char = '&') = sendMessage(message.colorize(altChar))