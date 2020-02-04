package kr.entree.enderwand.uuid

import kr.entree.enderwand.command.NotUUIDException
import java.util.*

/**
 * Created by JunHyung Lim on 2020-01-11
 */
fun String.toUniqueId() = runCatching { UUID.fromString(this) }.getOrNull()

fun String.toUniqueIdOrThrow() = runCatching { UUID.fromString(this) }.getOrNull() ?: throw NotUUIDException(this)