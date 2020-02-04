package kr.entree.enderwand.command

import kr.entree.enderwand.command.executor.Executor

/**
 * Created by JunHyung Lim on 2020-01-09
 */
open class NotNumberException(val value: String) : NumberFormatException()

class NotIntException(value: String) : NotNumberException(value)

class NotDoubleException(value: String) : NotNumberException(value)

class InvalidUsageException : IllegalArgumentException()

class ArgumentParseException(val index: Int, cause: Throwable? = null) : IllegalArgumentException(cause)

class NoPermissionException(val permission: String) : IllegalStateException()

class CommandException(val errorMessage: String) : RuntimeException()

class NotUUIDException(val string: String) : IllegalArgumentException()

fun executorExceptionOf(executor: Executor<*>, cause: Throwable) =
    ExecutorException(executor, StringBuilder(), mutableListOf(), cause)

class ExecutorException(
    val executor: Executor<*>,
    val argument: StringBuilder,
    val stack: MutableList<Pair<String, Executor<*>>>,
    override val cause: Throwable
) : RuntimeException(cause)

fun validate(bool: Boolean, message: String) {
    if (!bool) {
        throw CommandException(message)
    }
}