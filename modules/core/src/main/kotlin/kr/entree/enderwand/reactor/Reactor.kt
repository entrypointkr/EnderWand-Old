package kr.entree.enderwand.reactor

/**
 * Created by JunHyung Lim on 2019-12-22
 */
typealias Actor<V> = (ReactorContext<V>) -> Unit

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