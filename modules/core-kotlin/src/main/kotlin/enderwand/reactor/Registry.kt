package enderwand.reactor

/**
 * Created by JunHyung Lim on 2020-01-07
 */
class Registry<K, V>(
    private val reactor: MapReactor<K, V>,
    private val key: K
) {
    fun run(actor: ReactorContext<V>.() -> Unit) = reactor.subscribe(key, actor)

    fun let(actor: Actor<V>) = reactor.subscribe(key, actor)

    operator fun invoke(actor: ReactorContext<V>.() -> Unit) = run(actor)
}