package kr.entree.enderwand.bukkit.command

import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.command.Command
import org.bukkit.command.CommandMap

/**
 * Created by JunHyung Lim on 2019-12-18
 */
val commandManager get() = enderWand.commandManager

inline fun Command<BukkitSender>.register(label: String, configure: CommandDescriptor.() -> Unit = {}) =
    commandManager.register(this, CommandDescriptor().apply(configure), label)

class CommandManager(
    private val commandMap: CommandMap
) : CommandMap by commandMap {
    fun register(
        command: Command<BukkitSender>,
        descriptor: CommandDescriptor,
        label: String
    ) {
        descriptor.create(label, command).apply {
            commandMap.register(label, plugin.name.toLowerCase(), this)
        }
    }
}