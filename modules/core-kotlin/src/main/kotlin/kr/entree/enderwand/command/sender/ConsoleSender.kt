package kr.entree.enderwand.command.sender

/**
 * Created by JunHyung Lim on 2019-12-18
 */
class ConsoleSender : Sender {
    override fun sendMessage(message: Any) {
        println(message)
    }

    override fun sendError(errorMessage: Any) {
        sendMessage(errorMessage)
    }

    override fun hasPermission(node: String) = true

    override fun isOp() = true
}