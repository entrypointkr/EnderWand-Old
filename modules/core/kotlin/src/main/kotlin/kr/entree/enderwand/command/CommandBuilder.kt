package kr.entree.enderwand.command

import kr.entree.enderwand.collection.Reader
import kr.entree.enderwand.command.argument.Argument
import kr.entree.enderwand.command.argument.parse
import kr.entree.enderwand.command.sender.Sender

/**
 * Created by JunHyung Lim on 2020-01-08
 */
inline fun <S : Sender> command(configure: CommandBuilder<S>.() -> Unit) =
    CommandBuilder<S>().apply(configure).build()

class CommandBuilder<S : Sender> {
    var aliases: Set<String> = mutableSetOf()
    var description: String = ""
    var permission: String = ""
    var arguments: MutableList<Argument<*>> = mutableListOf()
    val childs = mutableMapOf<String, Command<S>>()
    val commands = mutableListOf<Command<S>>()
    lateinit var executor: CommandContext<S, Reader<String>>.() -> Unit
    var tabCompleter: CommandContext<S, Reader<String>>.() -> List<String> = { emptyList() }

    fun executes(executor: CommandContext<S, Reader<String>>.() -> Unit) {
        this.executor = executor
    }

    fun executes(
        arguments: List<Argument<*>>,
        receiver: CommandContext<S, List<Any>>.() -> Unit
    ) {
        this.arguments.addAll(arguments)
        executes {
            receiver(CommandContext(sender, arguments.parse(args)))
        }
        tabCompletes {
            val completer = arguments.getOrNull(args.remain() - 1)
            if (completer != null) {
                val input = args.last()
                completer.tabComplete().filter { it.startsWith(input) }
            } else {
                args.pos -= arguments.size
                tabCompleter(this)
            }
        }
    }

    fun tabCompletes(tabCompleter: CommandContext<S, Reader<String>>.() -> List<String>) {
        this.tabCompleter = tabCompleter
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

    operator fun <T> Argument<T>.unaryPlus() = arguments.add(this)

    operator fun <T> Argument<T>.unaryMinus() = arguments.remove(this)

    fun build(): Command<S> {
        return if (childs.isEmpty()) {
            StandardCommand(executor, tabCompleter, description, permission, arguments)
        } else {
            if (this::executor.isInitialized) {
                MapCommand(childs, executor)
            } else {
                MapCommand(childs)
            }
        }
    }
}