package kr.entree.enderwand.bukkit.command

import kr.entree.enderwand.collection.toReader
import kr.entree.enderwand.command.CommandContext
import kr.entree.enderwand.command.executor.Executor
import kr.entree.enderwand.command.tabcompleter.TabCompleter
import org.bukkit.command.CommandSender
import org.bukkit.command.PluginIdentifiableCommand
import org.bukkit.plugin.Plugin
import org.bukkit.command.Command as BukkitCommand

/**
 * Created by JunHyung Lim on 2019-12-18
 */
class BukkitCommandAdapter(
    label: String,
    description: String,
    usage: String,
    aliases: List<String>,
    private val plugin: Plugin,
    private val executor: Executor<BukkitSender>,
    private val tabCompleter: TabCompleter<BukkitSender>
) : BukkitCommand(label, description, usage, aliases), PluginIdentifiableCommand {
    override fun execute(sender: CommandSender, label: String, args: Array<String>): Boolean {
        executor.execute(CommandContext(BukkitSender(sender), args.toReader()).apply {
            metadata["label"] = label
        })
        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>): List<String> {
        return tabCompleter.tabComplete(CommandContext(BukkitSender(sender), args.toReader()).apply {
            metadata["label"] = alias
        })
    }

    override fun getPlugin(): Plugin {
        return plugin
    }
}