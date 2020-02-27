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
    fun encodeDouble(index: Int, double: Double) = encodeDoubleElement(descriptor, index, double)

    fun encodeFloat(index: Int, float: Float) = encodeFloatElement(descriptor, index, float)

    fun encodeString(index: Int, string: String) = encodeStringElement(descriptor, index, string)
}