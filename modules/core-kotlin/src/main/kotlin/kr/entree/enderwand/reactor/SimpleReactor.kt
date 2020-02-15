package kr.entree.enderwand.reactor

/**
 * Created by JunHyung Lim on 2020-02-15
 */
class SimpleReactor<T> : Reactor<T> {
    var actor: Actor<T>? = null

    fun invokeCancelledHandler() {
        val ctx = this.actor as? ReactorContext
        ctx?.onCancelledHandler?.invoke()
    }

    override fun subscribe(actor: Actor<T>): Boolean {
        invokeCancelledHandler()
        this.actor = actor
        return true
    }

    override fun remove(actor: Actor<T>): Boolean {
        invokeCancelledHandler()
        this.actor = null
        return true
    }

    override fun notify(value: T) {
        val actor = this.actor ?: return
        ReactorResult(value, actor.getContextOrCreate()).apply {
            actor(this)
            if (isCancelled) {
                onCancelledHandler()
            }
        }
    }
}