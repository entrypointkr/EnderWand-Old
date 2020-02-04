package enderwand.bukkit.command

import enderwand.bukkit.enderWand
import enderwand.command.Command
import enderwand.command.executor.Executor
import enderwand.command.tabcompleter.TabCompleter
import org.bukkit.plugin.Plugin

/**
 * Created by JunHyung Lim on 2019-12-18
 */
fun commandDescriptorOf(configure: CommandDescriptor.() -> Unit): CommandDescriptor {
    val extra = CommandDescriptor()
    extra.configure()
    return extra
}

class CommandDescriptor {
    var description: String = ""
    var usage: String = ""
    var aliases: Set<String> = emptySet()
    var plugin: Plugin? = null

    fun create(
        label: String,
        executor: Executor<BukkitSender>,
        tabCompleter: TabCompleter<BukkitSender>
    ) = BukkitCommandAdapter(
        label,
        description,
        usage,
        aliases.toList(),
        plugin ?: enderWand,
        executor,
        tabCompleter
    )

    fun create(label: String, command: Command<BukkitSender>) =
        create(label, command, command)
}