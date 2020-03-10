package kr.entree.enderwand.bukkit.serialization

import kotlinx.serialization.CompositeDecoder
import kotlinx.serialization.Decoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor

fun Decoder.beginStructureDescriptive(
    desc: SerialDescriptor,
    vararg typeParams: KSerializer<*>,
    configure: DescriptiveDecoder.() -> Unit = {}
) = DescriptiveDecoder(desc, beginStructure(desc, *typeParams)).apply {
    configure()
    endStructure(desc)
}

class DescriptiveDecoder(
    val descriptor: SerialDescriptor,
    val decoder: CompositeDecoder
) : CompositeDecoder by decoder {
    fun doLoopIndexes(decoder: (index: Int) -> Unit) {
        mainLoop@ while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                CompositeDecoder.READ_DONE -> break@mainLoop
                else -> decoder(index)
            }
        }
    }

    fun decodeUnit(index: Int) = decodeUnitElement(descriptor, index)
    fun decodeBoolean(index: Int) = decodeBooleanElement(descriptor, index)
    fun decodeByte(index: Int) = decodeByteElement(descriptor, index)
    fun decodeShort(index: Int) = decodeShortElement(descriptor, index)
    fun decodeChar(index: Int) = decodeCharElement(descriptor, index)
    fun decodeInt(index: Int) = decodeIntElement(descriptor, index)
    fun decodeLong(index: Int) = decodeLongElement(descriptor, index)
    fun decodeFloat(index: Int) = decodeFloatElement(descriptor, index)
    fun decodeDouble(index: Int) = decodeDoubleElement(descriptor, index)
    fun decodeString(index: Int) = decodeStringElement(descriptor, index)
}