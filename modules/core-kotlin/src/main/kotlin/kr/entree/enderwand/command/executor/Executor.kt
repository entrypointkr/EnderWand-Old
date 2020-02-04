package kr.entree.enderwand.command.executor

import kr.entree.enderwand.collection.Reader
import kr.entree.enderwand.command.CommandContext
import kr.entree.enderwand.command.sender.Sender

/**
 * Created by JunHyung Lim on 2019-12-20
 */
fun <S : Sender> Executor<S>.execute(sender: S, args: Reader<String>) =
    execute(CommandContext(sender, args))

interface Executor<S : Sender> : (CommandContext<S, Reader<String>>) -> Unit {
    fun execute(ctx: CommandContext<S, Reader<String>>)

    override operator fun invoke(ctx: CommandContext<S, Reader<String>>) = execute(ctx)
}