package kr.entree.enderwand.bukkit.serialization

import kotlinx.serialization.CompositeDecoder
import kotlinx.serialization.Decoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.internal.StringSerializer
import kotlinx.serialization.internal.nullable
import kotlin.collections.set

fun Decoder.useStructureDescriptive(
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
    var cached = mutableMapOf<Int, String>()

    fun decodeNullableElement(index: Int): String? {
        if (cached.containsKey(index)) {
            return cached.remove(index)
        }
        mainLoop@ while (true) {
            when (val elementIndex = decodeElementIndex(descriptor)) {
                CompositeDecoder.READ_ALL -> {
                    return decodeNullableSerializableElement(descriptor, elementIndex, StringSerializer.nullable)
                }
                CompositeDecoder.READ_DONE -> {
                    break@mainLoop
                }
                index -> return decodeStringElement(descriptor, elementIndex)
                else -> cached[elementIndex] = decodeStringElement(descriptor, elementIndex)
            }
        }
        return null
    }

    fun decodeElement(index: Int) = decodeNullableElement(index)!!
}