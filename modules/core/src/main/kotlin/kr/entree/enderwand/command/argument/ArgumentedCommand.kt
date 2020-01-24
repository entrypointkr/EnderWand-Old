package kr.entree.enderwand.command.argument

import kr.entree.enderwand.collection.Reader
import kr.entree.enderwand.command.ArgumentParseException
import kr.entree.enderwand.command.CmdMarker
import kr.entree.enderwand.command.Command
import kr.entree.enderwand.command.CommandContext
import kr.entree.enderwand.command.sender.Sender

/**
 * Created by JunHyung Lim on 2020-01-09
 */
@CmdMarker
class ArgumentedCommand<S : Sender> : Command<S> {
    val arguments = mutableListOf<Argument>()
    private lateinit var _executor: CommandContext<S, List<Any>>.() -> Unit
    private var _tabCompletor: CommandContext<S, Reader<String>>.() -> List<String> = { emptyList() }

    fun add(argument: Argument) = arguments.add(argument)

    inline fun string(description: String = "string", configure: Argument.() -> Unit = {}) =
        add(Argument(description, _parser = STRING_PARSER).apply(configure))

    inline fun int(description: String = "int", configure: Argument.() -> Unit = {}) =
        add(Argument(description, _parser = INT_PARSER).apply(configure))

    inline fun double(description: String = "double", configure: Argument.() -> Unit = {}) =
        add(Argument(description, _parser = DOUBLE_PARSER).apply(configure))

    fun executor(executor: CommandContext<S, List<Any>>.() -> Unit) {
        this._executor = executor
    }

    fun parse(reader: Reader<String>): List<Any> {
        val ret = mutableListOf<Any>()
        arguments.forEachIndexed { index, argument ->
            if (!reader.canRead()) {
                if (argument.optional) {
                    return@forEachIndexed
                }
                throw ArgumentParseException(index)
            }
            try {
                ret += argument.parse(reader)
            } catch (ex: Exception) {
                throw ArgumentParseException(index, ex)
            }
        }
        return ret
    }

    override fun execute(ctx: CommandContext<S, Reader<String>>) {
        _executor(CommandContext(ctx.sender, parse(ctx.args)))
    }

    override fun tabComplete(ctx: CommandContext<S, Reader<String>>): List<String> {
        val completer = arguments.getOrNull(ctx.args.remain() - 1)
        return if (completer != null) {
            val input = ctx.args.last()
            completer.tabComplete().filter { it.startsWith(input) }
        } else {
            ctx.args.pos -= arguments.size
            _tabCompletor(ctx)
        }
    }
}