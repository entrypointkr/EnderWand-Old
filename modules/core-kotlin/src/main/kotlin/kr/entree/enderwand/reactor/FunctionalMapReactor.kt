package kr.entree.enderwand.reactor

/**
 * Created by JunHyung Lim on 2020-01-07
 */
fun <K, V> simpleReactor(identifier: (V) -> K?) =
    FunctionalMapReactor(factory = {
        SimpleReactor()
    }, identifier = identifier)

class FunctionalMapReactor<K, V>(
    val factory: () -> Reactor<V>,
    val identifier: (V) -> K?
) : MapReactor<K, V> {
    private val map = mutableMapOf<K, Reactor<V>>()

    override fun get(key: K) = map.getOrPut(key, factory)

    override fun subscribe(key: K, actor: Actor<V>) = get(key).subscribe(actor)

    override fun remove(key: K) = map.remove(key)?.apply { dispose() }

    override fun remove(key: K, actor: Actor<V>) = get(key).remove(actor)

    override fun notify(value: V) {
        val key = identifier(value) ?: return
        map[key]?.notify(value)
    }

    override fun dispose() {
        map.forEach { (_, reactor) ->
            reactor.dispose()
        }
        map.clear()
    }
}