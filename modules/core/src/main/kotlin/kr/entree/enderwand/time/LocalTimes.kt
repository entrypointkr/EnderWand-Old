package kr.entree.enderwand.time

import java.time.LocalTime

/**
 * Created by JunHyung Lim on 2020-01-23
 */
val LocalTime.isUp get() = this <= LocalTime.now()