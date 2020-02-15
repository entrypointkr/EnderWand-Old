package kr.entree.enderwand.reactor

/**
 * Created by JunHyung Lim on 2020-01-07
 */
class Registry<K, V>(
    private val reactor: MapReactor<K, V>,
    private val key: K
) {
    operator fun invoke(context: ReactorContext = SimpleReactorContext(), actor: ReactorResult<V>.() -> Unit) =
        reactor.subscribe(key, FunctionalActor(actor, context))
}