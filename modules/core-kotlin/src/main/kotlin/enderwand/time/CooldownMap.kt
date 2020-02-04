package enderwand.time

import java.time.Duration

/**
 * Created by JunHyung Lim on 2019-12-29
 */
class CooldownMap<K> {
    val map = mutableMapOf<K, Cooldown>()

    fun get(id: K): Cooldown {
        return map.getOrPut(id) { Cooldown() }
    }

    fun action(id: K, duration: Duration) =
        get(id).action(duration)

    fun remove(id: K) = map.remove(id)
}