package kr.entree.enderwand.bukkit.plugin

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

fun pluginOf(name: String) = Bukkit.getPluginManager().getPlugin(name)

fun <T : JavaPlugin> pluginOf(type: KClass<T>) = JavaPlugin.getPlugin(type.java)

inline fun <reified T : JavaPlugin> plugin() = pluginOf(T::class)

fun Plugin.registerListeners(vararg listeners: Listener) {
    listeners.forEach { Bukkit.getPluginManager().registerEvents(it, this) }
}