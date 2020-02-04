package enderwand.command

import enderwand.command.sender.Sender

/**
 * Created by JunHyung Lim on 2020-01-08
 */
open class CommandContext<S : Sender, T>(
    val sender: S,
    val args: T
) {
    val metadata: MutableMap<String, Any> by lazy { mutableMapOf<String, Any>() }
}