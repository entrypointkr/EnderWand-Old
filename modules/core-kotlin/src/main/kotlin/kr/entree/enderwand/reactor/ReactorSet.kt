package kr.entree.enderwand.reactor

/**
 * Created by JunHyung Lim on 2019-12-21
 */
class ReactorSet<T> : Reactor<T> {
    private val actors = mutableSetOf<Actor<T>>()

    override fun subscribe(actor: Actor<T>) = actors.add(actor)

    override fun remove(actor: Actor<T>): Boolean {
        if (actors.remove(actor)) {
            actor.context.onCancelledHandler()
            return true
        }
        return false
    }

    override fun notify(value: T) {
        val iterator = actors.iterator()
        while (iterator.hasNext()) {
            val actor = iterator.next()
            ReactorResult(value, actor.context).apply {
                actor(this)
                if (isCancelled) {
                    iterator.remove()
                    onCancelledHandler()
                }
            }
        }
    }

    override fun dispose() {
        actors.forEach { it.context.dispose() }
    }
}