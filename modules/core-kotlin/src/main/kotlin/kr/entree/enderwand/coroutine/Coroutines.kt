package kr.entree.enderwand.coroutine

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by JunHyung Lim on 2020-02-15
 */
suspend fun <T> whileNotNull(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    onNull: (Deferred<T?>) -> Unit = {},
    block: suspend CoroutineScope.() -> T?
) = coroutineScope {
    val deferred = async(context, start, block)
    var value = deferred.await()
    while (value == null && isActive) {
        onNull(deferred)
        value = deferred.await()
    }
    value ?: throw CancellationException()
}