package kr.entree.enderwand.bukkit.command

import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.command.Command
import kr.entree.enderwand.command.executor.Executor
import kr.entree.enderwand.command.tabcompleter.TabCompleter
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
    var aliases: Collection<String> = emptyList()
    var plugin: Plugin? = null

    fun create(
        label: String,
        executor: Executor<BukkitSender>,
        tabCompleter: TabCompleter<BukkitSender>
    ) = BukkitCommandAdapter(
        label,
        description,
        usage,
        aliases.distinct(),
        plugin ?: enderWand,
        executor,
        tabCompleter
    )

    fun create(label: String, command: Command<BukkitSender>) =
        create(label, command, command)
}