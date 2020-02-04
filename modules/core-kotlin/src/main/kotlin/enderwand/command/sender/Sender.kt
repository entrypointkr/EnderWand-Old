package enderwand.command.sender

/**
 * Created by JunHyung Lim on 2019-12-18
 */
fun CharSequence.sendTo(sender: Sender) {
    sender.sendMessage(toString())
}

interface Sender {
    fun sendMessage(message: Any)

    fun sendError(errorMessage: Any)

    fun hasPermission(node: String): Boolean

    fun isOp(): Boolean
}