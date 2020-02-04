package enderwand.command.executor

import enderwand.collection.Reader
import enderwand.command.CommandContext
import enderwand.command.sender.Sender

/**
 * Created by JunHyung Lim on 2019-12-20
 */
fun <S : Sender> Executor<S>.execute(sender: S, args: Reader<String>) =
    execute(CommandContext(sender, args))

interface Executor<S : Sender> : (CommandContext<S, Reader<String>>) -> Unit {
    fun execute(ctx: CommandContext<S, Reader<String>>)

    override operator fun invoke(ctx: CommandContext<S, Reader<String>>) = execute(ctx)
}