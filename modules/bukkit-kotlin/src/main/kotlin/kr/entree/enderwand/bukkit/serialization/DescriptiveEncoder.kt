package kr.entree.enderwand.bukkit.serialization

import kotlinx.serialization.CompositeEncoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor

fun Encoder.beginStructureDescriptive(
    desc: SerialDescriptor,
    vararg typeParams: KSerializer<*>,
    configure: DescriptiveEncoder.() -> Unit = {}
) = DescriptiveEncoder(desc, beginStructure(desc, *typeParams)).apply {
    configure()
    endStructure(desc)
}

class DescriptiveEncoder(
    val descriptor: SerialDescriptor,
    val encoder: CompositeEncoder
) : CompositeEncoder by encoder {
    fun encodeUnit(index: Int) = encodeUnitElement(descriptor, index)
    fun encodeBoolean(index: Int, value: Boolean) = encodeBooleanElement(descriptor, index, value)
    fun encodeByte(index: Int, value: Byte) = encodeByteElement(descriptor, index, value)
    fun encodeShort(index: Int, value: Short) = encodeShortElement(descriptor, index, value)
    fun encodeChar(index: Int, value: Char) = encodeCharElement(descriptor, index, value)
    fun encodeInt(index: Int, value: Int) = encodeIntElement(descriptor, index, value)
    fun encodeLong(index: Int, value: Long) = encodeLongElement(descriptor, index, value)
    fun encodeFloat(index: Int, float: Float) = encodeFloatElement(descriptor, index, float)
    fun encodeDouble(index: Int, double: Double) = encodeDoubleElement(descriptor, index, double)
    fun encodeString(index: Int, string: String) = encodeStringElement(descriptor, index, string)
}