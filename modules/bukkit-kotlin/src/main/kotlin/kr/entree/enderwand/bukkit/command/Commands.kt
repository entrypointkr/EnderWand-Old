package kr.entree.enderwand.bukkit.command

import kr.entree.enderwand.bukkit.player.toPlayerOrThrow
import kr.entree.enderwand.command.CommandBuilder
import kr.entree.enderwand.command.CommandContext
import kr.entree.enderwand.command.command
import kr.entree.enderwand.command.withHelper
import org.bukkit.entity.Player

/**
 * Created by JunHyung Lim on 2019-12-20
 */
typealias BukkitExecutor<T> = CommandContext<BukkitSender, T>.() -> Unit

inline fun bukkitCommand(
    configure: CommandBuilder<BukkitSender>.() -> Unit
) = command(configure).withHelper(BukkitCommandHelper())

val <T> CommandContext<BukkitSender, T>.player: Player get() = sender.toPlayerOrThrow()