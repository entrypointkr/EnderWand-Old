package kr.entree.enderwand.command

import kr.entree.enderwand.collection.Reader
import kr.entree.enderwand.command.sender.Sender
import kr.entree.enderwand.string.unicodeBlock

/**
 * Created by JunHyung Lim on 2020-01-09
 */
class MapCommand<S : Sender>(
    override val childs: Map<String, Command<S>>,
    private val defaultExecutor: CommandContext<S, Reader<String>>.() -> Unit = {
        throw InvalidUsageException()
    }
) : Command<S>, CommandMapped<S> {
    override fun execute(ctx: CommandContext<S, Reader<String>>) {
        val argument = ctx.args.readOrNull()
        val executor = argument?.run { childs[this] }
            ?.run { this::execute } ?: defaultExecutor
        try {
            executor(ctx)
        } catch (ex: ExecutorException) {
            ex.stack += (argument ?: "") to this
            if (ex.argument.isNotEmpty()) {
                ex.argument.insert(0, ' ')
            }
            ex.argument.insert(0, argument ?: "???")
            throw ex
        } catch (ex: Exception) {
            throw executorExceptionOf(this, ex)
        }
    }

    override fun tabComplete(
        ctx: CommandContext<S, Reader<String>>
    ): List<String> {
        return ctx.run {
            val label = metadata["label"]?.toString()
            val completes = when (args.remain()) {
                0 -> childs.keys.toList()
                1 -> {
                    val argument = args.peek()
                    childs.keys.filter { it.startsWith(argument) }
                }
                else -> {
                    val sub = childs[args.peek()]
                    if (sub != null) {
                        args.read()
                        sub.tabComplete(this)
                    } else {
                        emptyList()
                    }
                }
            }
            if (label != null && label.isNotEmpty()) {
                val localized = completes.filter { label.unicodeBlock == it.unicodeBlock }
                if (localized.isEmpty()) {
                    completes
                } else {
                    localized
                }
            } else {
                completes
            }
        }
    }
}