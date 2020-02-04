package kr.entree.enderwand.reactor

/**
 * Created by JunHyung Lim on 2020-01-06
 */
interface MapReactor<K, V> {
    fun get(key: K): Reactor<V>

    fun subscribe(key: K, actor: Actor<V>): Boolean

    fun remove(key: K): Reactor<V>?

    fun remove(key: K, actor: Actor<V>): Boolean

    fun notify(value: V)
}