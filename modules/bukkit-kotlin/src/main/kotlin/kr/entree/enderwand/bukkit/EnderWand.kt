package kr.entree.enderwand.bukkit

import kr.entree.enderwand.bukkit.command.CommandManager
import kr.entree.enderwand.bukkit.internal.commandMap
import kr.entree.enderwand.bukkit.lang.LanguageManager
import kr.entree.enderwand.bukkit.plugin.plugin
import kr.entree.enderwand.bukkit.plugin.registerListeners
import kr.entree.enderwand.bukkit.reactor.EventReactor
import kr.entree.enderwand.bukkit.view.ViewManager
import org.bukkit.plugin.java.JavaPlugin

/**
 * Created by JunHyung Lim on 2019-12-03
 */
val enderWand get() = plugin<EnderWand>()

class EnderWand : JavaPlugin() {
    val commandManager = CommandManager(commandMap)
    val viewManager = ViewManager()
    val languageManager = LanguageManager()
    val eventReactor = EventReactor()

    override fun onEnable() {
        initLanguages()
        initListeners()
    }

    private fun initLanguages() {
        runCatching {
            languageManager.loadFromResource(classLoader)
        }.onFailure {
            logger.warning("Failed to loading languages.")
        }
    }

    private fun initListeners() = registerListeners(viewManager, eventReactor)
}