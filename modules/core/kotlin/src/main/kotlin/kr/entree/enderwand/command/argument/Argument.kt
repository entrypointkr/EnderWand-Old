package kr.entree.enderwand.command.argument

import kr.entree.enderwand.collection.Reader

/**
 * Created by JunHyung Lim on 2020-01-09
 */
typealias Parser = (Reader<String>) -> Any

typealias Completer = () -> List<String>

data class Argument<T>(
    var description: String = "",
    var default: T? = null,
    var optional: Boolean = false,
    private var parser: (Reader<String>) -> Any,
    private var tabCompleter: () -> List<String> = { emptyList() }
) {
    infix fun or(value: T): Argument<T> {
        default = value
        optional = true
        return this
    }

    fun option() = Argument<T?>(description, default, true, parser, tabCompleter)

    fun parser(parser: (Reader<String>) -> Any) {
        this.parser = parser
    }

    fun tabCompletes(completer: () -> List<String>) {
        this.tabCompleter = completer
    }

    fun parse(reader: Reader<String>) = parser(reader)

    fun tabComplete() = tabCompleter()
}