package kr.entree.enderwand.math

operator fun <T> T.rangeTo(other: T): NumberRange<T> where T : Number, T : Comparable<T> = NumberRange(this, other)

class NumberRange<T>(override val start: T, override val endInclusive: T) :
    ClosedRange<T> where T : Number, T : Comparable<T>