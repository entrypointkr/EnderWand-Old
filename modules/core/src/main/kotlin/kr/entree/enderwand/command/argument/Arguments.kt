package kr.entree.enderwand.command.argument

import kr.entree.enderwand.command.NotDoubleException
import kr.entree.enderwand.command.NotIntException
import kr.entree.enderwand.command.sender.Sender
import kr.entree.enderwand.time.DurationParser
import kr.entree.enderwand.time.EnglishDurationParser
import kr.entree.enderwand.time.KoreanDurationParser

/**
 * Created by JunHyung Lim on 2020-01-28
 */
val PARSER_STRING: Parser = { it.read() }
val PARSER_INT: Parser = {
    it.read().run { toIntOrNull() ?: throw NotIntException(this) }
}
val PARSER_DOUBLE: Parser = {
    it.read().run { toDoubleOrNull() ?: throw NotDoubleException(this) }
}
val PARSER_DURATION_ENGLISH: Parser = {
    EnglishDurationParser.parse(it.read())
}
val PARSER_DURATION_KOREAN: Parser = {
    KoreanDurationParser.parse(it.read())
}

inline fun <S : Sender> ArgumentedCommand<S>.string(
    description: String = "string",
    configure: Argument.() -> Unit = {}
) = +Argument(description, parser = PARSER_STRING).apply(configure)

inline fun <S : Sender> ArgumentedCommand<S>.int(
    description: String = "int",
    configure: Argument.() -> Unit = {}
) = +Argument(description, parser = PARSER_INT).apply(configure)

inline fun <S : Sender> ArgumentedCommand<S>.double(
    description: String = "double",
    configure: Argument.() -> Unit = {}
) = +Argument(description, parser = PARSER_DOUBLE).apply(configure)

inline fun <S : Sender> ArgumentedCommand<S>.duration(
    description: String = "duration",
    parser: DurationParser = EnglishDurationParser,
    configure: Argument.() -> Unit = {}
) = +Argument(description, parser = PARSER_DURATION_ENGLISH)

inline fun <S : Sender> ArgumentedCommand<S>.durationKor(
    description: String = "시간",
    parser: DurationParser = KoreanDurationParser,
    configure: Argument.() -> Unit = {}
) = +Argument(description, parser = PARSER_DURATION_KOREAN)