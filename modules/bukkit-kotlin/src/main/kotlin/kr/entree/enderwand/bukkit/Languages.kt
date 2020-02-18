package kr.entree.enderwand.bukkit

import com.google.gson.JsonParser
import kr.entree.enderwand.bukkit.lang.LanguageManager
import java.io.BufferedReader
import java.net.URLDecoder
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

/**
 * Created by JunHyung Lim on 2020-02-18
 */
internal inline fun readJar(clazz: Class<*>, receiver: (ZipEntry) -> Unit) {
    ZipFile(URLDecoder.decode(clazz.protectionDomain.codeSource.location.path, "UTF-8")).use {
        val entries = it.entries()
        while (entries.hasMoreElements()) {
            receiver(entries.nextElement())
        }
    }
}

internal fun LanguageManager.load(locale: String, bufferedReader: BufferedReader) {
    val json = JsonParser().parse(bufferedReader).asJsonObject
    for (entry in json.entrySet()) {
        getMap(locale.toLowerCase()).map[entry.key] = entry.value.asString
    }
}

internal fun LanguageManager.loadFromResource(classLoader: ClassLoader) {
    readJar(EnderWand::class.java) { entry ->
        if (entry.name.startsWith("minecraft/lang/") && entry.name.endsWith(".json")) {
            classLoader.getResourceAsStream(entry.name)?.bufferedReader()?.use {
                load(entry.name.substringBefore('.').substringAfterLast('/'), it)
            }
        }
    }
}