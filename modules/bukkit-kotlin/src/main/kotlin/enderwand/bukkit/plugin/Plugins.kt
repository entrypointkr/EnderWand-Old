package enderwand.bukkit.plugin

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import kotlin.reflect.KClass

/**
 * Created by JunHyung Lim on 2020-01-08
 */
fun Plugin.file(child: String): File {
    return File(dataFolder, child)
}

fun String.toPlugin() = Bukkit.getPluginManager().getPlugin(this)

fun <T : JavaPlugin> Class<T>.toPlugin() = JavaPlugin.getPlugin(this)

fun <T : JavaPlugin> KClass<T>.toPlugin() = JavaPlugin.getPlugin(java)

fun Plugin.registerListeners(vararg listeners: Listener) {
    listeners.forEach { Bukkit.getPluginManager().registerEvents(it, this) }
}