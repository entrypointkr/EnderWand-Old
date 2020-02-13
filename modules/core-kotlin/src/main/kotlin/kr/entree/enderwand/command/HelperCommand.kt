package kr.entree.enderwand.command

import kr.entree.enderwand.collection.Reader
import kr.entree.enderwand.command.executor.Executor
import kr.entree.enderwand.command.sender.Sender
import kr.entree.enderwand.command.tabcompleter.TabCompleter

/**
 * Created by JunHyung Lim on 2020-01-09
 */
object DefaultCommandHelper : (CommandTrouble) -> Unit {
    fun handle(trouble: CommandTrouble) {
        trouble.sender.sendMessage("Invalid command usage.")
    }

    override fun invoke(trouble: CommandTrouble) {
        handle(trouble)
    }
}

fun <T : Sender> Command<T>.withHelper(helper: (CommandTrouble) -> Unit = DefaultCommandHelper) =
    HelperCommand(this, this, helper)

class HelperCommand<S : Sender>(
    private val executor: Executor<S>,
    private val tabCompleter: TabCompleter<S>,
    private val helper: (CommandTrouble) -> Unit
) : Command<S> {
    override fun execute(ctx: CommandContext<S, Reader<String>>) {
        try {
            executor.execute(ctx)
        } catch (ex: Exception) {
            helper(CommandTrouble(ctx, ex))
        }
    }

    override fun tabComplete(
        ctx: CommandContext<S, Reader<String>>
    ) = tabCompleter.tabComplete(ctx)
}

data class CommandTrouble(
    val ctx: CommandContext<out Sender, Reader<String>>,
    val exception: Throwable
) {
    val sender get() = ctx.sender
    val args get() = ctx.args
}