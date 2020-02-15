package kr.entree.enderwand.bukkit

import com.google.gson.JsonParser
import kr.entree.enderwand.bukkit.command.CommandManager
import kr.entree.enderwand.bukkit.internal.commandMap
import kr.entree.enderwand.bukkit.lang.LanguageManager
import kr.entree.enderwand.bukkit.plugin.plugin
import kr.entree.enderwand.bukkit.plugin.registerListeners
import kr.entree.enderwand.bukkit.reactor.EventReactor
import kr.entree.enderwand.bukkit.view.ViewManager
import org.bukkit.plugin.java.JavaPlugin
import java.io.BufferedReader
import java.net.URLDecoder
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

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

private inline fun readJar(clazz: Class<*>, receiver: (ZipEntry) -> Unit) {
    ZipFile(URLDecoder.decode(clazz.protectionDomain.codeSource.location.path, "UTF-8")).use {
        val entries = it.entries()
        while (entries.hasMoreElements()) {
            receiver(entries.nextElement())
        }
    }
}

private fun LanguageManager.load(locale: String, bufferedReader: BufferedReader) {
    val json = JsonParser().parse(bufferedReader).asJsonObject
    for (entry in json.entrySet()) {
        getMap(locale.toLowerCase()).map[entry.key] = entry.value.asString
    }
}

private fun LanguageManager.loadFromResource(classLoader: ClassLoader) {
    readJar(EnderWand::class.java) { entry ->
        if (entry.name.startsWith("minecraft/lang/") && entry.name.endsWith(".json")) {
            classLoader.getResourceAsStream(entry.name)?.bufferedReader()?.use {
                load(entry.name.substringBefore('.').substringAfterLast('/'), it)
            }
        }
    }
}