package kr.entree.enderwand.time

import java.time.Duration
import java.time.LocalDateTime

val LocalDateTime.isPast get() = this < LocalDateTime.now()

val LocalDateTime.isPastOrNow get() = this <= LocalDateTime.now()

val LocalDateTime.isFuture get() = this > LocalDateTime.now()

val LocalDateTime.nowTimeGap: Duration get() = Duration.between(LocalDateTime.now(), this)