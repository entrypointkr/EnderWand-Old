package enderwand.command.tabcompleter

import enderwand.collection.Reader
import enderwand.command.CommandContext
import enderwand.command.sender.Sender

/**
 * Created by JunHyung Lim on 2019-12-18
 */
typealias FunctionalTabCompleter<S> = (CommandContext<S, Reader<String>>) -> List<String>

fun <S : Sender> TabCompleter<S>.tabComplete(sender: S, args: Reader<String>) =
    tabComplete((CommandContext(sender, args)))

interface TabCompleter<S : Sender> {
    fun tabComplete(
        ctx: CommandContext<S, Reader<String>>
    ): List<String>
}