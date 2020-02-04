package kr.entree.enderwand.string

/**
 * Created by JunHyung Lim on 2020-01-16
 */
val String.unicodeBlock
    get() = find { it.isLetter() }
        ?.run { Character.UnicodeBlock.of(this) }
        ?: Character.UnicodeBlock.BASIC_LATIN