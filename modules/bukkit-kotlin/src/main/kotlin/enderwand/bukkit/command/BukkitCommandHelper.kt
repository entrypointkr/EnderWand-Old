package enderwand.bukkit.command

import enderwand.bukkit.enderWand
import enderwand.bukkit.exception.NotCraftingMaterialException
import enderwand.bukkit.exception.UnknownMaterialException
import enderwand.bukkit.exception.UnknownPlayerException
import enderwand.command.*
import enderwand.string.unicodeBlock
import java.io.PrintWriter
import java.io.StringWriter
import java.util.logging.Level

/**
 * Created by JunHyung Lim on 2020-01-15
 */

class BukkitCommandHelper : (CommandTrouble) -> Unit {
    fun jsonMsg(text: String, suggestCmd: String, hoverText: String) =
        "{\"text\":$text,\"clickEvent\":{\"action\":\"suggest_command\",\"value\":$suggestCmd},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"\",{\"text\":$hoverText}]}}"

    val CommandDetailed.info
        get() = buildString {
            var spacing = false
            arguments.forEach {
                if (spacing) append(' ')
                else spacing = true
                append(if (it.optional) '[' else '<')
                append(it.description)
                append(if (it.optional) ']' else '>')
            }
            if (description.isNotBlank()) {
                append(" - ").append(description)
            }
        }

    fun StringBuilder.appendCommandInfo(detailed: CommandDetailed) {
        detailed.arguments.forEach {
            append(' ')
            append(if (it.optional) '[' else '<')
            append(it.description)
            append(if (it.optional) ']' else '>')
        }
        if (detailed.description.isNotBlank()) {
            append(" - ").append(detailed.description)
        }
    }

    override fun invoke(trouble: CommandTrouble) {
        handleError(trouble.exception, trouble.ctx)
    }

    fun handleError(throwable: Throwable, ctx: CommandContext<*, *>) {
        val sender = ctx.sender
        when (throwable) {
            is ExecutorException -> handleExecutor(throwable, ctx)
            is UnknownPlayerException -> sender.sendError("존재하지 않는 플레이어입니다.")
            is ArgumentParseException -> {
                if (throwable.cause != null) {
                    handleError(throwable.cause!!, ctx)
                } else {
                    sender.sendError("잘못된 명령어 사용입니다.")
                }
            }
            is UnknownMaterialException -> sender.sendError("알 수 없는 아이템/블럭입니다. ${throwable.query}")
            is NotCraftingMaterialException -> sender.sendError("조합할 수 없는 아이템/블럭 입니다. ${throwable.material}")
            is NoPermissionException -> sender.sendError("권한 ${throwable.permission} 이 없습니다.")
            is NotNumberException -> sender.sendError("숫자가 아닙니다. ${throwable.value}")
            is NotIntException -> sender.sendError("정수가 아닙니다. ${throwable.value}")
            is NotDoubleException -> sender.sendError("소수가 아닙니다. ${throwable.value}")
            is InvalidUsageException -> sender.sendError("잘못된 사용법입니다.")
            is CommandException -> sender.sendError(throwable.errorMessage)
            else -> {
                if (sender.isOp()) {
                    StringWriter().apply {
                        throwable.printStackTrace(PrintWriter(this))
                        sender.sendError(toString())
                        enderWand.logger.log(Level.WARNING, throwable) { "Error in command!" }
                    }
                }
                sender.sendError("에러가 발생했습니다. $throwable")
            }
        }
    }

    fun handleExecutor(ex: ExecutorException, ctx: CommandContext<*, *>) {
        val executor = ex.executor
        val label = ctx.metadata["label"]?.toString() ?: "???"
        val argument = StringBuilder().append('/').append(label)
        val sender = ctx.sender
        if (ex.argument.isNotEmpty()) {
            argument.append(' ').append(ex.argument)
        }
        val labelUnicode = label.unicodeBlock
        if (executor is CommandMapped<*>) {
            sender.sendError("명령어 도움말")
            executor.aliasesByCommand.filter {
                val cmd = it.key
                cmd !is CommandDetailed || sender.hasPermission(cmd.permission)
            }.forEach { (command, aliases) ->
                val alias = aliases.find { labelUnicode == it.unicodeBlock }
                    ?: aliases.first()
                val prev = argument.length
                argument.append(' ').append(alias)
                val line = when (command) {
                    is CommandDetailed -> {
                        "$argument ${command.info}"
                    }
                    is CommandMapped<*> -> {
                        buildString {
                            append(argument).append(' ')
                            val keys = command.childs.map { it.key }
                            append(keys.filter {
                                it.unicodeBlock == labelUnicode
                            }.ifEmpty {
                                keys
                            }.joinToString("|", "<", ">", 3, "..."))
                        }
                    }
                    else -> {
                        argument.toString()
                    }
                }
                val player = (sender as? BukkitSender)?.player
                if (player != null) {
                    sender.sendMessage(line)
                } else {
                    sender.sendMessage(line)
                }
                argument.setLength(prev)
            }
        } else {
            handleError(ex.cause, ctx)
            if (executor is CommandDetailed) {
                argument.appendCommandInfo(executor)
                sender.sendMessage(argument)
            }
        }
    }
}