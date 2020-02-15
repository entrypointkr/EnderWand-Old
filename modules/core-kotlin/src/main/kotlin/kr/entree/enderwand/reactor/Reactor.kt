package kr.entree.enderwand.reactor

/**
 * Created by JunHyung Lim on 2019-12-22
 */
fun <T> Actor<T>.getContextOrCreate() = this as? ReactorContext ?: SimpleReactorContext()

interface Actor<T> {
    operator fun invoke(ctx: ReactorResult<T>)
}

class FunctionalActor<T>(
    val function: ReactorResult<T>.() -> Unit,
    val context: ReactorContext
) : Actor<T>, ReactorContext by context {
    override fun invoke(ctx: ReactorResult<T>) = function(ctx)
}

interface Reactor<T> {
    fun subscribe(actor: Actor<T>): Boolean

    fun remove(actor: Actor<T>): Boolean

    fun notify(value: T)

    operator fun plusAssign(receiver: Actor<T>) {
        subscribe(receiver)
    }

    operator fun minusAssign(receiver: Actor<T>) {
        remove(receiver)
    }
}