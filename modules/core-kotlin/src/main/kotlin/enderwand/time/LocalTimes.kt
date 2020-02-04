package enderwand.time

import java.time.Duration
import java.time.LocalTime

/**
 * Created by JunHyung Lim on 2020-01-23
 */
val LocalTime.isExpired get() = this <= LocalTime.now()

val LocalTime.isNotExpired get() = this > LocalTime.now()

val LocalTime.expires get() = Duration.between(LocalTime.now(), this)