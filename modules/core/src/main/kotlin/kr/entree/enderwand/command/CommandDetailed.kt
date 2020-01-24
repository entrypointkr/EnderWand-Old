package kr.entree.enderwand.command

import kr.entree.enderwand.command.argument.Argument

/**
 * Created by JunHyung Lim on 2020-01-16
 */
interface CommandDetailed {
    val description: String
    val permission: String
    val arguments: List<Argument>
}

object EmptyCommandDetailed : CommandDetailed {
    override val description: String
        get() = ""
    override val permission: String
        get() = ""
    override val arguments: List<Argument>
        get() = emptyList()
}