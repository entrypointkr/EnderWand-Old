package kr.entree.enderwand.bukkit.serialization

import kotlinx.serialization.*
import kotlinx.serialization.builtins.AbstractEncoder
import kr.entree.enderwand.bukkit.config.getOrPut
import org.bukkit.configuration.ConfigurationSection

class ConfigurationSectionEncoder(
    private val section: ConfigurationSection
) : AbstractEncoder() {
    var prefix = StringBuilder()
    var prefixMark = 0
    var key = ""
    var lastKind: SerialKind = StructureKind.CLASS
    var mapKey: Any = Unit

    fun appendPrefix(string: String) {
        if (prefix.isNotEmpty()) {
            prefix.append('.')
        }
        prefix.append(string)
    }

    override fun beginStructure(
        descriptor: SerialDescriptor,
        vararg typeSerializers: KSerializer<*>
    ): CompositeEncoder {
        prefix.clear()
        prefix.append(key)
        prefixMark = prefix.length
        lastKind = descriptor.kind
        return this
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        prefix.setLength(prefixMark)
    }

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        val prev = prefix.length
        when (lastKind) {
            StructureKind.MAP -> {
                if (index % 2 != 0) { // isValue, not a key
                    appendPrefix(mapKey.toString())
                }
            }
            StructureKind.LIST -> {
                // Nothing to do
            }
            StructureKind.CLASS -> {
                appendPrefix(descriptor.getElementName(index))
            }
        }
        key = prefix.toString()
        prefix.setLength(prev)
        return true
    }

    override fun encodeValue(value: Any) {
        if (lastKind == StructureKind.MAP && mapKey == Unit) {
            mapKey = value
        } else {
            val list = section.getOrPut(key) { mutableListOf<Any>() }
            list += value
            mapKey = Unit
        }
    }
}