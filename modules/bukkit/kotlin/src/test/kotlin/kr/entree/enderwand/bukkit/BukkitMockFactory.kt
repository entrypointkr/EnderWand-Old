package kr.entree.enderwand.bukkit

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kr.entree.enderwand.bukkit.command.BukkitSender
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Created by JunHyung Lim on 2020-01-09
 */
fun createCommandSender(msgReceiver: (String) -> Unit) = mock<CommandSender> {
    on { sendMessage(any<String>()) } doAnswer {
        msgReceiver(it.arguments[0].toString())
    }
    on { hasPermission(any<String>()) } doReturn true
}

fun createBukkitSender(msgReceiver: (String) -> Unit) = BukkitSender(createCommandSender(msgReceiver))

fun createServer() = mock<Server>() {
    on { getPlayer(any<String>()) } doAnswer {
        createPlayer(it.arguments[0].toString())
    }
}

fun createPlayer(name: String) = mock<Player> {
    on { getName() } doReturn name
}

fun injectServer() = Bukkit::class.java.getDeclaredField("server").run {
    isAccessible = true
    set(null, createServer())
}