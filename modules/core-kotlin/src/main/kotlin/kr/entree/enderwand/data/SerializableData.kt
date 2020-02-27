@file:Suppress("EXPERIMENTAL_API_USAGE")

package kr.entree.enderwand.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import java.io.File
import java.io.Reader
import java.io.Writer

inline fun <T> serializableDataOf(
    serializer: KSerializer<T>,
    file: File,
    noinline patcher: (T) -> Unit,
    noinline provider: () -> T,
    format: StringFormat = Json.indented,
    configure: StandardData.() -> Unit = {}
) = dataOf(file, SerializableData(serializer, patcher, provider, format)).apply(configure)

class SerializableData<T>(
    var serializer: KSerializer<T>,
    var patcher: (T) -> Unit,
    var provider: () -> T,
    val format: StringFormat
) : DynamicData {
    override fun load(reader: Reader) {
        patcher(format.parse(serializer, reader.readText()))
    }

    override fun save(writer: Writer) {
        writer.write(format.stringify(serializer, provider()))
    }
}