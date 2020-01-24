package kr.entree.enderwand.command.tabcompleter

import kr.entree.enderwand.collection.Reader
import kr.entree.enderwand.command.CommandContext
import kr.entree.enderwand.command.sender.Sender

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