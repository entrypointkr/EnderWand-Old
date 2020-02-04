package kr.entree.enderwand.command

import kr.entree.enderwand.command.sender.Sender

/**
 * Created by JunHyung Lim on 2020-01-15
 */
interface CommandMapped<S : Sender> {
    val childs: Map<String, Command<S>>
    val aliasesByCommand
        get() = mutableMapOf<Command<S>, MutableSet<String>>().apply {
            childs.forEach { getOrPut(it.value) { mutableSetOf() }.add(it.key) }
        }
}