package kr.entree.enderwand.command.argument

import kr.entree.enderwand.collection.Reader

/**
 * Created by JunHyung Lim on 2020-01-09
 */
typealias Parser = (Reader<String>) -> Any

typealias Completer = () -> List<String>

data class Argument(
    var description: String = "",
    var optional: Boolean = false,
    private var parser: (Reader<String>) -> Any,
    private var tabCompleter: () -> List<String> = { emptyList() }
) {
    fun parser(parser: (Reader<String>) -> Any) {
        this.parser = parser
    }

    fun tabCompleter(completer: () -> List<String>) {
        this.tabCompleter = completer
    }

    fun parse(reader: Reader<String>) = parser(reader)

    fun tabComplete() = tabCompleter()
}