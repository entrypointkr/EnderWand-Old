@file:Suppress("UNCHECKED_CAST")

package enderwand.bukkit.command

import enderwand.bukkit.exception.NotCraftingMaterialException
import enderwand.bukkit.exception.UnknownMaterialException
import enderwand.bukkit.exception.UnknownPlayerException
import enderwand.bukkit.player.toOfflinePlayer
import enderwand.bukkit.player.toPlayer
import enderwand.bukkit.recipe.recipe
import enderwand.command.CommandBuilder
import enderwand.command.CommandContext
import enderwand.command.argument.Argument
import enderwand.command.argument.Completer
import enderwand.command.argument.Parser
import enderwand.command.sender.Sender
import enderwand.data.*
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

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

inline fun CommandBuilder<BukkitSender>.player(
    description: String = "player",
    configure: Argument<Player>.() -> Unit = {}
) = Argument<Player>(description, parser = PARSER_PLAYER, tabCompleter = COMPLETER_PLAYER).apply(configure)

inline fun CommandBuilder<BukkitSender>.offlinePlayer(
    description: String = "offline-player",
    configure: Argument<OfflinePlayer>.() -> Unit = {}
) = Argument<OfflinePlayer>(description, parser = PARSER_OFFLINE_PLAYER, tabCompleter = COMPLETER_PLAYER).apply(
    configure
)

inline fun CommandBuilder<BukkitSender>.material(
    description: String = "material",
    configure: Argument<Material>.() -> Unit = {}
) = Argument<Material>(description, parser = PARSER_MATERIAL, tabCompleter = COMPLETER_MATERIAL).apply(configure)

inline fun CommandBuilder<BukkitSender>.craftingMaterial(
    description: String = "material",
    configure: Argument<Material>.() -> Unit = {}
) = Argument<Material>(description, parser = PARSER_CRAFTING_MATERIAL, tabCompleter = COMPLETER_CRAFTING_MATERIAL)
    .apply(configure)

fun <S : Sender, T> CommandBuilder<S>.executes(
    arg1: Argument<T>,
    executor: CommandContext<S, List<Any>>.(Tuple1<T>) -> Unit
) = executes(kotlin.collections.listOf(arg1)) {
    executor(
        this,
        enderwand.data.Tuple1(args.getOrNull(0) as T)
    )
}

fun <S : Sender, T1, T2> CommandBuilder<S>.executes(
    arg1: Argument<T1>,
    arg2: Argument<T2>,
    executor: CommandContext<S, List<Any>>.(Tuple2<T1, T2>) -> Unit
) = executes(listOf(arg1, arg2)) {
    executor(
        this,
        Tuple2(args.getOrNull(0) as T1, args.getOrNull(1) as T2)
    )
}

fun <S : Sender, T1, T2, T3> CommandBuilder<S>.executes(
    arg1: Argument<T1>,
    arg2: Argument<T2>,
    arg3: Argument<T3>,
    executor: CommandContext<S, List<Any>>.(Tuple3<T1, T2, T3>) -> Unit
) = executes(listOf(arg1, arg2, arg3)) {
    executor(
        this,
        Tuple3(args.getOrNull(0) as T1, args.getOrNull(1) as T2, args.getOrNull(2) as T3)
    )
}

fun <S : Sender, T1, T2, T3, T4> CommandBuilder<S>.executes(
    arg1: Argument<T1>,
    arg2: Argument<T2>,
    arg3: Argument<T3>,
    arg4: Argument<T4>,
    executor: CommandContext<S, List<Any>>.(Tuple4<T1, T2, T3, T4>) -> Unit
) = executes(listOf(arg1, arg2, arg3, arg4)) {
    executor(
        this,
        Tuple4(args.getOrNull(0) as T1, args.getOrNull(1) as T2, args.getOrNull(2) as T3, args.getOrNull(3) as T4)
    )
}

fun <S : Sender, T1, T2, T3, T4, T5> CommandBuilder<S>.executes(
    arg1: Argument<T1>,
    arg2: Argument<T2>,
    arg3: Argument<T3>,
    arg4: Argument<T4>,
    arg5: Argument<T5>,
    executor: CommandContext<S, List<Any>>.(Tuple5<T1, T2, T3, T4, T5>) -> Unit
) = executes(listOf(arg1, arg2, arg3, arg4, arg5)) {
    executor(
        this,
        Tuple5(
            args.getOrNull(0) as T1,
            args.getOrNull(1) as T2,
            args.getOrNull(2) as T3,
            args.getOrNull(3) as T4,
            args.getOrNull(4) as T5
        )
    )
}

fun <S : Sender, T1, T2, T3, T4, T5, T6> CommandBuilder<S>.executes(
    arg1: Argument<T1>,
    arg2: Argument<T2>,
    arg3: Argument<T3>,
    arg4: Argument<T4>,
    arg5: Argument<T5>,
    arg6: Argument<T6>,
    executor: CommandContext<S, List<Any>>.(Tuple6<T1, T2, T3, T4, T5, T6>) -> Unit
) = executes(listOf(arg1, arg2, arg3, arg4, arg5, arg6)) {
    executor(
        this,
        Tuple6(
            args.getOrNull(0) as T1,
            args.getOrNull(1) as T2,
            args.getOrNull(2) as T3,
            args.getOrNull(3) as T4,
            args.getOrNull(4) as T5,
            args.getOrNull(5) as T6
        )
    )
}

fun <S : Sender, T1, T2, T3, T4, T5, T6, T7> CommandBuilder<S>.executes(
    arg1: Argument<T1>,
    arg2: Argument<T2>,
    arg3: Argument<T3>,
    arg4: Argument<T4>,
    arg5: Argument<T5>,
    arg6: Argument<T6>,
    arg7: Argument<T7>,
    executor: CommandContext<S, List<Any>>.(Tuple7<T1, T2, T3, T4, T5, T6, T7>) -> Unit
) = executes(listOf(arg1, arg2, arg3, arg4, arg5, arg6, arg7)) {
    executor(
        this,
        Tuple7(
            args.getOrNull(0) as T1,
            args.getOrNull(1) as T2,
            args.getOrNull(2) as T3,
            args.getOrNull(3) as T4,
            args.getOrNull(4) as T5,
            args.getOrNull(5) as T6,
            args.getOrNull(6) as T7
        )
    )
}

fun <S : Sender, T1, T2, T3, T4, T5, T6, T7, T8> CommandBuilder<S>.executes(
    arg1: Argument<T1>,
    arg2: Argument<T2>,
    arg3: Argument<T3>,
    arg4: Argument<T4>,
    arg5: Argument<T5>,
    arg6: Argument<T6>,
    arg7: Argument<T7>,
    arg8: Argument<T8>,
    executor: CommandContext<S, List<Any>>.(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>) -> Unit
) = executes(listOf(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8)) {
    executor(
        this,
        Tuple8(
            args.getOrNull(0) as T1,
            args.getOrNull(1) as T2,
            args.getOrNull(2) as T3,
            args.getOrNull(3) as T4,
            args.getOrNull(4) as T5,
            args.getOrNull(5) as T6,
            args.getOrNull(6) as T7,
            args.getOrNull(7) as T8
        )
    )
}

fun <S : Sender, T1, T2, T3, T4, T5, T6, T7, T8, T9> CommandBuilder<S>.executes(
    arg1: Argument<T1>,
    arg2: Argument<T2>,
    arg3: Argument<T3>,
    arg4: Argument<T4>,
    arg5: Argument<T5>,
    arg6: Argument<T6>,
    arg7: Argument<T7>,
    arg8: Argument<T8>,
    arg9: Argument<T9>,
    executor: CommandContext<S, List<Any>>.(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>) -> Unit
) = executes(listOf(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9)) {
    executor(
        this,
        Tuple9(
            args.getOrNull(0) as T1,
            args.getOrNull(1) as T2,
            args.getOrNull(2) as T3,
            args.getOrNull(3) as T4,
            args.getOrNull(4) as T5,
            args.getOrNull(5) as T6,
            args.getOrNull(6) as T7,
            args.getOrNull(7) as T8,
            args.getOrNull(8) as T9
        )
    )
}