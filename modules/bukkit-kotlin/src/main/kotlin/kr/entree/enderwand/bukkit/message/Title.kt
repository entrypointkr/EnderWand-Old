package kr.entree.enderwand.bukkit.message

import org.bukkit.entity.Player

/**
 * Created by JunHyung Lim on 2019-12-31
 */
fun parseTitle(string: String): Title {
    val pieces = string.split("|")
    return Title(
        pieces[0], pieces.getOrNull(1) ?: "",
        pieces.getOrNull(2)?.toIntOrNull() ?: 10,
        pieces.getOrNull(3)?.toIntOrNull() ?: 40,
        pieces.getOrNull(4)?.toIntOrNull() ?: 10
    )
}

fun String.toTitle() = parseTitle(this)

fun Player.sendTitle(title: Title) = title.sendTo(this)

data class Title(
    var title: String,
    var subTitle: String,
    var fadeIn: Int = 10,
    var stay: Int = 40,
    var fadeOut: Int = 10
) {
    infix fun sendTo(player: Player) =
        player.sendTitle(title, subTitle, fadeIn, stay, fadeOut)
}