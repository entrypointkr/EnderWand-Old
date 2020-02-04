package enderwand.reactor

/**
 * Created by JunHyung Lim on 2019-12-21
 */
data class ReactorContext<T>(
    val event: T,
    var remove: Boolean = true
)