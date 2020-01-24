package kr.entree.enderwand.bukkit.command

import kr.entree.enderwand.bukkit.exception.NotCraftingMaterialException
import kr.entree.enderwand.bukkit.exception.UnknownMaterialException
import kr.entree.enderwand.bukkit.exception.UnknownPlayerException
import kr.entree.enderwand.bukkit.player.toOfflinePlayer
import kr.entree.enderwand.bukkit.player.toPlayer
import kr.entree.enderwand.bukkit.recipe.recipe
import kr.entree.enderwand.command.CommandBuilder
import kr.entree.enderwand.command.CommandContext
import kr.entree.enderwand.command.argument.Argument
import kr.entree.enderwand.command.argument.ArgumentedCommand
import kr.entree.enderwand.command.argument.Completer
import kr.entree.enderwand.command.argument.Parser
import kr.entree.enderwand.command.command
import kr.entree.enderwand.command.sender.Sender
import kr.entree.enderwand.command.withHelper
import org.bukkit.Bukkit
import org.bukkit.Material

/**
 * Created by JunHyung Lim on 2019-12-20
 */
val PLAYER_PARSER: Parser = {
    it.read().run { toPlayer() ?: throw UnknownPlayerException(this) }
}
val PLAYER_COMPLETER: Completer = {
    Bukkit.getOnlinePlayers().map { it.name }
}
val OFFLINE_PLAYER_PARSER: Parser = {
    it.read().let { argument ->
        argument.toOfflinePlayer().apply {
            if (name == null) {
                throw UnknownPlayerException(argument)
            }
        }
    }
}
val MATERIAL_PARSER: Parser = {
    it.read().run { Material.getMaterial(toUpperCase()) ?: throw UnknownMaterialException(this) }
}
val MATERIAL_COMPLETER: Completer = {
    Material.values().map { it.name }
}
val CRAFTING_MATERIALS: Map<String, Material> by lazy {
    Material.values().filter { it.recipe != null }.map { it.name to it }.toMap()
}
val CRAFTING_MATERIAL_PARSER: Parser = {
    val mat = MATERIAL_PARSER(it) as Material
    CRAFTING_MATERIALS[mat.name] ?: throw NotCraftingMaterialException(mat)
}
val CRAFTING_MATERIAL_COMPLETER: Completer = {
    CRAFTING_MATERIALS.keys.toList()
}

typealias BukkitExecutor<T> = CommandContext<BukkitSender, T>.() -> Unit

inline fun bukkitCommand(
    configure: CommandBuilder<BukkitSender>.() -> Unit
) = command(configure).withHelper(BukkitCommandHelper())

inline fun <S : Sender> ArgumentedCommand<S>.player(
    description: String = "player",
    configure: Argument.() -> Unit = {}
) = add(Argument(description, _parser = PLAYER_PARSER, _tabCompleter = PLAYER_COMPLETER).apply(configure))

inline fun <S : Sender> ArgumentedCommand<S>.offlinePlayer(
    description: String = "offline-player",
    configure: Argument.() -> Unit = {}
) = add(Argument(description, _parser = OFFLINE_PLAYER_PARSER, _tabCompleter = PLAYER_COMPLETER).apply(configure))

inline fun <S : Sender> ArgumentedCommand<S>.material(
    description: String = "material",
    configure: Argument.() -> Unit = {}
) = add(Argument(description, _parser = MATERIAL_PARSER, _tabCompleter = MATERIAL_COMPLETER).apply(configure))

inline fun <S : Sender> ArgumentedCommand<S>.craftingMaterial(
    description: String = "material",
    configure: Argument.() -> Unit = {}
) = add(
    Argument(description, _parser = CRAFTING_MATERIAL_PARSER, _tabCompleter = CRAFTING_MATERIAL_COMPLETER)
        .apply(configure)
)