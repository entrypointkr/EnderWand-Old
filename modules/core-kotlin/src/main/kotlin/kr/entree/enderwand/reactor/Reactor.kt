package kr.entree.enderwand.reactor

/**
 * Created by JunHyung Lim on 2019-12-22
 */
interface Reactor<T> : Disposable {
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