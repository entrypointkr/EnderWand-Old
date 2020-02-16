package kr.entree.enderwand.reactor

/**
 * Created by JunHyung Lim on 2019-12-21
 */
interface ReactorContext : Disposable {
    var isCancelled: Boolean
    var onCancelledHandler: () -> Unit

    fun cancel() {
        isCancelled = true
    }

    override fun dispose() {
        onCancelledHandler()
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

class DelegateReactorContext(
    val context: ReactorContext,
    val disposable: Disposable
) : ReactorContext by context {
    override fun dispose() = disposable.dispose()
}

object EmptyReactorContext : ReactorContext {
    override var isCancelled: Boolean
        get() = true
        set(value) {}
    override var onCancelledHandler: () -> Unit
        get() = {}
        set(value) {}
}