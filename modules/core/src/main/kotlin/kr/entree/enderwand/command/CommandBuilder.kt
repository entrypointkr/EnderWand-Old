package kr.entree.enderwand.command

import kr.entree.enderwand.collection.Reader
import kr.entree.enderwand.command.argument.Argument
import kr.entree.enderwand.command.argument.ArgumentedCommand
import kr.entree.enderwand.command.sender.Sender

/**
 * Created by JunHyung Lim on 2020-01-08
 */
inline fun <T : Sender> command(configure: CommandBuilder<T>.() -> Unit) =
    CommandBuilder<T>().apply(configure).build()

@DslMarker
annotation class CmdMarker

@CmdMarker
class CommandBuilder<S : Sender> {
    var aliases: Set<String> = mutableSetOf()
    var description: String = ""
    var permission: String = ""
    var arguments: List<Argument> = emptyList()
    val childs = mutableMapOf<String, Command<S>>()
    val commands = mutableListOf<Command<S>>()
    private lateinit var _executor: CommandContext<S, Reader<String>>.() -> Unit
    private var _tabCompleter: CommandContext<S, Reader<String>>.() -> List<String> = { emptyList() }

    fun executor(executor: CommandContext<S, Reader<String>>.() -> Unit) {
        this._executor = executor
    }

    fun tabCompleter(tabCompleter: CommandContext<S, Reader<String>>.() -> List<String>) {
        this._tabCompleter = tabCompleter
    }

    inline fun arguments(configure: ArgumentedCommand<S>.() -> Unit) {
        ArgumentedCommand<S>().let {
            it.configure()
            executor(it)
            tabCompleter(it::tabComplete)
            arguments = it.arguments
        }
    }

    inline fun child(label: String, configure: CommandBuilder<S>.() -> Unit) {
        val builder = CommandBuilder<S>().apply(configure)
        if (permission.isNotBlank() && builder.permission.isNotBlank()) {
            builder.permission = "$permission.${builder.permission}"
        }
        val cmd = builder.build()
        childs[label] = cmd
        builder.aliases.forEach { childs[it] = cmd }
    }

    fun build(): Command<S> {
        return if (childs.isEmpty()) {
            StandardCommand(_executor, _tabCompleter, description, permission, arguments)
        } else {
            if (this::_executor.isInitialized) {
                MapCommand(childs, _executor)
            } else {
                MapCommand(childs)
            }
        }
    }
}