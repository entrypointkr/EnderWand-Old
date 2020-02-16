package kr.entree.enderwand.reactor

/**
 * Created by JunHyung Lim on 2020-02-16
 */
operator fun Disposable.plus(other: Disposable) =
    ChainedDisposable(this, other)

interface Disposable {
    fun dispose()
}

class ChainedDisposable(
    val first: Disposable,
    val second: Disposable
) : Disposable {
    override fun dispose() {
        first.dispose()
        second.dispose()
    }
}