package kr.entree.enderwand.time

import java.time.Duration
import java.time.temporal.ChronoUnit

/**
 * Created by JunHyung Lim on 2020-01-28
 */
class UnknownUnitException(val unit: String) : IllegalArgumentException()

class DurationParser(
    val unitParser: (String) -> ChronoUnit?
) {
    fun parse(line: String): Duration {
        val digits = StringBuilder()
        val letters = StringBuilder()
        var duration = Duration.ZERO
        fun plus() {
            if (letters.isNotEmpty()) {
                val unit = unitParser(letters.toString()) ?: throw UnknownUnitException(letters.toString())
                duration = duration.plus(digits.toString().toLong(), unit)
                digits.clear()
                letters.clear()
            }
        }
        for (index in line.indices) {
            val ch = line[index]
            if (index == 0 && ch == '-') {
                duration.negated()
            } else if (ch == '.' || ch.isDigit()) {
                plus()
                digits.append(ch)
            } else if (ch.isLetter()) {
                letters.append(ch)
            }
        }
        plus()
        return duration
    }
}

val DURATION_PARSER_KOREAN = DurationParser {
    when (it) {
        "년" -> ChronoUnit.YEARS
        "개월", "월" -> ChronoUnit.MONTHS
        "일" -> ChronoUnit.DAYS
        "시간", "시" -> ChronoUnit.HOURS
        "분" -> ChronoUnit.MINUTES
        "초" -> ChronoUnit.SECONDS
        "밀리", "밀리초" -> ChronoUnit.MILLIS
        "나노", "나노초" -> ChronoUnit.NANOS
        else -> null
    }
}

val DURATION_PARSER_ENGLISH = DurationParser {
    when (it) {
        "years", "year" -> ChronoUnit.YEARS
        "months", "month" -> ChronoUnit.MONTHS
        "days", "day" -> ChronoUnit.DAYS
        "hours", "hour" -> ChronoUnit.HOURS
        "minutes", "minute", "mins", "min" -> ChronoUnit.MINUTES
        "seconds", "second", "secs", "sec" -> ChronoUnit.SECONDS
        "milliseconds", "millisecond", "millis", "milli" -> ChronoUnit.MILLIS
        "nanoseconds", "nanosecond", "nanos", "nano" -> ChronoUnit.NANOS
        else -> null
    }
}