package enderwand.bukkit.command

import enderwand.command.CommandBuilder
import enderwand.command.CommandContext
import enderwand.command.command
import enderwand.command.withHelper

/**
 * Created by JunHyung Lim on 2019-12-20
 */
typealias BukkitExecutor<T> = CommandContext<BukkitSender, T>.() -> Unit

inline fun bukkitCommand(
    configure: CommandBuilder<BukkitSender>.() -> Unit
) = command(configure).withHelper(BukkitCommandHelper())
