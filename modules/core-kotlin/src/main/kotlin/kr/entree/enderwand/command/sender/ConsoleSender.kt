package kr.entree.enderwand.command.sender

/**
 * Created by JunHyung Lim on 2019-12-18
 */
class ConsoleSender : Sender {
    override fun tell(message: Any) {
        println(message)
    }

    override fun tellError(errorMessage: Any) {
        tell(errorMessage)
    }

    override fun hasPermission(node: String) = true

    override fun isOp() = true
}