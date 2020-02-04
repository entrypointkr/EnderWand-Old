package enderwand.duration

import enderwand.time.DURATION_FORMATTER_KOREAN
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Duration

/**
 * Created by JunHyung Lim on 2020-02-02
 */
class DurationFormatterTest {
    @Test
    fun formatKorean() {
        val duration = Duration.ofHours(1).plusMinutes(2).plusSeconds(3)
        val duration2 = Duration.ofSeconds(0)
        val duration3 = duration2.plusHours(1)
        Assertions.assertEquals("1시간 2분 3초", DURATION_FORMATTER_KOREAN.format(duration))
        Assertions.assertEquals("0초", DURATION_FORMATTER_KOREAN.format(duration2))
        Assertions.assertEquals("1시간", DURATION_FORMATTER_KOREAN.format(duration3))
    }
}