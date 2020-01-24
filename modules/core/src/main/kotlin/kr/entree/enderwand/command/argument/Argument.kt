package kr.entree.enderwand.command.argument

import kr.entree.enderwand.collection.Reader
import kr.entree.enderwand.command.NotDoubleException
import kr.entree.enderwand.command.NotIntException

/**
 * Created by JunHyung Lim on 2020-01-09
 */
typealias Parser = (Reader<String>) -> Any

typealias Completer = () -> List<String>

data class Argument(
    var description: String = "",
    var optional: Boolean = false,
    private var _parser: (Reader<String>) -> Any,
    private var _tabCompleter: () -> List<String> = { emptyList() }
) {
    fun parser(parser: (Reader<String>) -> Any) {
        _parser = parser
    }

    fun tabCompleter(completer: () -> List<String>) {
        _tabCompleter = completer
    }

    fun parse(reader: Reader<String>) = _parser(reader)

    fun tabComplete() = _tabCompleter()
}

val STRING_PARSER: Parser = { it.read() }
val INT_PARSER: Parser = {
    it.read().run { toIntOrNull() ?: throw NotIntException(this) }
}
val DOUBLE_PARSER: Parser = {
    it.read().run { toDoubleOrNull() ?: throw NotDoubleException(this) }
}