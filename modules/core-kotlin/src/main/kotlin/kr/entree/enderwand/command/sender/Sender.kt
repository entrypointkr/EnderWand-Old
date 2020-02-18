package kr.entree.enderwand.command.sender

/**
 * Created by JunHyung Lim on 2019-12-18
 */
fun CharSequence.sendTo(sender: Sender) {
    sender.tell(toString())
}

interface Sender {
    fun tell(message: Any)

    fun tellError(errorMessage: Any)

    fun hasPermission(node: String): Boolean

    fun isOp(): Boolean
}