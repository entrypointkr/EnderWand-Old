package kr.entree.enderwand.bukkit.lang

import kr.entree.enderwand.bukkit.enderWand
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * Created by JunHyung Lim on 2020-01-17
 */
fun Material.i18nName(locale: String = "en_us") =
    enderWand.languageManager.getMessage(locale, "${if (isBlock) "block" else "item"}.${key.namespace}.${key.key}")
        ?: "${locale}_$name"

fun Material.i18nName(player: Player) = i18nName(player.locale)

val Material.korName get() = i18nName("ko_kr")

class LanguageManager(
    val map: MutableMap<String, MessageMap> = mutableMapOf()
) {
    fun getMessage(locale: String, key: String) = map[locale.toLowerCase()]?.map?.get(key)

    fun getMap(locale: String) = map.getOrPut(locale) { MessageMap() }
}