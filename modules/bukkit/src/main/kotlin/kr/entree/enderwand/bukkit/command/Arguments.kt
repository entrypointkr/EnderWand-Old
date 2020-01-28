package kr.entree.enderwand.bukkit.command

import kr.entree.enderwand.bukkit.exception.NotCraftingMaterialException
import kr.entree.enderwand.bukkit.exception.UnknownMaterialException
import kr.entree.enderwand.bukkit.exception.UnknownPlayerException
import kr.entree.enderwand.bukkit.player.toOfflinePlayer
import kr.entree.enderwand.bukkit.player.toPlayer
import kr.entree.enderwand.bukkit.recipe.recipe
import kr.entree.enderwand.command.argument.Argument
import kr.entree.enderwand.command.argument.ArgumentedCommand
import kr.entree.enderwand.command.argument.Completer
import kr.entree.enderwand.command.argument.Parser
import org.bukkit.Bukkit
import org.bukkit.Material

/**
 * Created by JunHyung Lim on 2020-01-28
 */
val PARSER_PLAYER: Parser = {
    it.read().run { toPlayer() ?: throw UnknownPlayerException(this) }
}
val COMPLETER_PLAYER: Completer = {
    Bukkit.getOnlinePlayers().map { it.name }
}
val PARSER_OFFLINE_PLAYER: Parser = {
    it.read().let { argument ->
        argument.toOfflinePlayer().apply {
            if (name == null) {
                throw UnknownPlayerException(argument)
            }
        }
    }
}
val PARSER_MATERIAL: Parser = {
    it.read().run { Material.getMaterial(toUpperCase()) ?: throw UnknownMaterialException(this) }
}
val COMPLETER_MATERIAL: Completer = {
    Material.values().map { it.name }
}
val CRAFTING_MATERIALS: Map<String, Material> by lazy {
    Material.values().filter { it.recipe != null }.map { it.name to it }.toMap()
}
val PARSER_CRAFTING_MATERIAL: Parser = {
    val mat = PARSER_MATERIAL(it) as Material
    CRAFTING_MATERIALS[mat.name] ?: throw NotCraftingMaterialException(mat)
}
val COMPLETER_CRAFTING_MATERIAL: Completer = {
    CRAFTING_MATERIALS.keys.toList()
}

inline fun ArgumentedCommand<BukkitSender>.player(
    description: String = "player",
    configure: Argument.() -> Unit = {}
) = +Argument(description, parser = PARSER_PLAYER, tabCompleter = COMPLETER_PLAYER).apply(configure)

inline fun ArgumentedCommand<BukkitSender>.offlinePlayer(
    description: String = "offline-player",
    configure: Argument.() -> Unit = {}
) = +Argument(description, parser = PARSER_OFFLINE_PLAYER, tabCompleter = COMPLETER_PLAYER).apply(configure)

inline fun ArgumentedCommand<BukkitSender>.material(
    description: String = "material",
    configure: Argument.() -> Unit = {}
) = +Argument(description, parser = PARSER_MATERIAL, tabCompleter = COMPLETER_MATERIAL).apply(configure)

inline fun ArgumentedCommand<BukkitSender>.craftingMaterial(
    description: String = "material",
    configure: Argument.() -> Unit = {}
) = +Argument(description, parser = PARSER_CRAFTING_MATERIAL, tabCompleter = COMPLETER_CRAFTING_MATERIAL)
    .apply(configure)