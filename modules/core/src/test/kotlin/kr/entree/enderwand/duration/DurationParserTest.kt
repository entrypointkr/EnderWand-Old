package kr.entree.enderwand.duration

import kr.entree.enderwand.time.DURATION_FORMATTER_ENGLISH
import kr.entree.enderwand.time.DURATION_PARSER_ENGLISH
import kr.entree.enderwand.time.DURATION_PARSER_KOREAN
import kr.entree.enderwand.time.toDurationKor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Duration
import kotlin.system.measureTimeMillis

/**
 * Created by JunHyung Lim on 2020-01-28
 */
class DurationParserTest {
    @Test
    fun english() {
        Assertions.assertEquals(
            Duration.parse("P1DT22H333M4444S"),
            DURATION_PARSER_ENGLISH.parse("1 days 22hours 333minute 4444sec")
        )
    }

    @Test
    fun korean() {
        Assertions.assertEquals(
            Duration.parse("P1DT22H333M4444S"),
            DURATION_PARSER_KOREAN.parse("1일 22시간 333분 4444초")
        )
    }

    @Test
    fun benchmark() {
        var java = 0L
        var enderwand = 0L
        val line = "1일 22시간 333분 4444초"
        val duration = line.toDurationKor()
        val string = duration.toString()
        for (i in 0 until 100) { // Pre-heat
            Assertions.assertEquals(
                Duration.parse(string),
                line.toDurationKor()
            )
        }
        for (i in 0 until 10000) {
            java += measureTimeMillis {
                Duration.parse(string)
            }
            enderwand += measureTimeMillis {
                line.toDurationKor()
            }
        }
        println("java: $java, enderwand: $enderwand")
    }
}