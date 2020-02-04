package enderwand.bukkit.internal

import org.bukkit.Bukkit
import org.bukkit.command.CommandMap

/**
 * Created by JunHyung Lim on 2020-01-08
 */
val commandMap by lazy {
    val server = Bukkit.getServer()
    val method = server.javaClass.getMethod("getCommandMap")
    method(server) as CommandMap
}