package kr.entree.enderwand.time

import java.time.Duration
import java.time.LocalTime

/**
 * Created by JunHyung Lim on 2020-01-23
 */
val LocalTime.isPast get() = this < LocalTime.now()

val LocalTime.isPastOrNow get() = this <= LocalTime.now()

val LocalTime.isFuture get() = this > LocalTime.now()

val LocalTime.nowTimeGap: Duration get() = Duration.between(LocalTime.now(), this)