package enderwand.time

import java.time.Duration
import java.time.LocalTime

/**
 * Created by JunHyung Lim on 2019-12-29
 */
class Cooldown(
    var last: LocalTime = LocalTime.MIN
) {
    fun action(duration: Duration): ActionResult {
        if (duration.isZero || duration.isNegative) {
            return COOLDOWN_RESULT_ZERO
        }
        val now = LocalTime.now()
        val diff = Duration.between(last, now)
        if (diff >= duration) {
            last = now
            return COOLDOWN_RESULT_ZERO
        }
        return ActionResult(duration.minus(diff))
    }

    fun reset() {
        last = LocalTime.MIN
    }
}

val COOLDOWN_RESULT_ZERO = ActionResult(Duration.ZERO)

data class ActionResult(val remain: Duration) {
    val success get() = remain.isZero || remain.isNegative

    val fail get() = !success

    inline fun onSuccess(block: () -> Unit) {
        if (success) {
            block()
        }
    }
}