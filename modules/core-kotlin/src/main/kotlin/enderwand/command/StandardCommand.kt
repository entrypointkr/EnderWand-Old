package enderwand.command

import enderwand.collection.Reader
import enderwand.command.argument.Argument
import enderwand.command.sender.Sender

/**
 * Created by JunHyung Lim on 2020-01-09
 */
fun Sender.checkPermission(permission: String) {
    if (!hasPermission(permission)) {
        throw NoPermissionException(permission)
    }
}

class StandardCommand<S : Sender>(
    private val executor: CommandContext<S, Reader<String>>.() -> Unit,
    private val tabCompleter: CommandContext<S, Reader<String>>.() -> List<String>,
    override var description: String = "",
    override var permission: String = "",
    override var arguments: List<Argument<*>> = emptyList()
) : Command<S>, CommandDetailed {
    override fun execute(ctx: CommandContext<S, Reader<String>>) {
        try {
            ctx.sender.checkPermission(permission)
            executor(CommandContext(ctx.sender, ctx.args))
        } catch (ex: ExecutorException) {
            ex.stack += "" to this
            throw ex
        } catch (ex: Exception) {
            throw executorExceptionOf(this, ex)
        }
    }

    override fun tabComplete(
        ctx: CommandContext<S, Reader<String>>
    ) = tabCompleter(ctx)
}