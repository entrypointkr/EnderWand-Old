package kr.entree.enderwand.reactor

/**
 * Created by JunHyung Lim on 2020-02-16
 */
val <T> Actor<T>.context get() = this as? ReactorContext ?: EmptyReactorContext

interface Actor<T> {
    operator fun invoke(ctx: ReactorResult<T>)
}

class FunctionalActor<T>(
    val function: ReactorResult<T>.() -> Unit,
    val context: ReactorContext
) : Actor<T>, ReactorContext by context {
    override fun invoke(ctx: ReactorResult<T>) = function(ctx)
}