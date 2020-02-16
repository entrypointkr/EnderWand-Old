package kr.entree.enderwand.reactor

/**
 * Created by JunHyung Lim on 2020-02-15
 */
class SimpleReactor<T> : Reactor<T> {
    var actor: Actor<T>? = null

    override fun subscribe(actor: Actor<T>): Boolean {
        dispose()
        this.actor = actor
        return true
    }

    override fun remove(actor: Actor<T>): Boolean {
        dispose()
        this.actor = null
        return true
    }

    override fun notify(value: T) {
        val actor = this.actor ?: return
        val context = DelegateReactorContext(actor.context, this)
        ReactorResult(value, context).apply {
            actor(this)
            if (isCancelled) {
                dispose()
            }
        }
    }

    override fun dispose() {
        (actor as? ReactorContext)?.dispose()
        actor = null
    }
}