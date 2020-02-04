package enderwand.time

import java.time.Duration
import java.time.temporal.ChronoUnit

/**
 * Created by JunHyung Lim on 2020-02-02
 */
val DURATION_UNITS = arrayOf(
    ChronoUnit.HOURS to 24,
    ChronoUnit.MINUTES to 60,
    ChronoUnit.SECONDS to 60,
    ChronoUnit.MILLIS to 1000
)

val DURATION_FORMATTER_KOREAN = DurationFormatter(
    mapOf(
        ChronoUnit.SECONDS to "초",
        ChronoUnit.MINUTES to "분",
        ChronoUnit.HOURS to "시간"
    )
)

val DURATION_FORMATTER_ENGLISH = DurationFormatter(
    mapOf(
        ChronoUnit.SECONDS to "seconds",
        ChronoUnit.MINUTES to "minutes",
        ChronoUnit.HOURS to "hours"
    )
)

fun Duration.format(formatter: DurationFormatter = DURATION_FORMATTER_ENGLISH) = formatter.format(this)

fun Duration.formatKor() = format(DURATION_FORMATTER_KOREAN)

class DurationFormatter(
    private val formats: Map<ChronoUnit, String>
) {
    private fun Duration.getTime(unit: ChronoUnit) = when (unit) {
        ChronoUnit.HOURS -> toHours()
        ChronoUnit.MINUTES -> toMinutes()
        ChronoUnit.MILLIS -> toMillis()
        else -> get(unit)
    }

    fun format(duration: Duration) = buildString {
        for ((unit, max) in DURATION_UNITS) {
            val format = formats[unit] ?: continue
            val times = duration.getTime(unit) % max
            if (times > 0 || (isEmpty() && unit == ChronoUnit.SECONDS)) {
                if (isNotEmpty()) append(' ')
                append(times).append(format)
            }
        }
    }
}