package kr.entree.enderwand.reactor

/**
 * Created by JunHyung Lim on 2019-12-21
 */
interface ReactorContext {
    var isCancelled: Boolean
    var onCancelledHandler: () -> Unit

    fun cancel() {
        isCancelled = true
    }
}

class SimpleReactorContext : ReactorContext {
    override var isCancelled = false
    override var onCancelledHandler: () -> Unit = {}
}

class ReactorResult<T>(
    val result: T,
    val delegate: ReactorContext
) : ReactorContext by delegate