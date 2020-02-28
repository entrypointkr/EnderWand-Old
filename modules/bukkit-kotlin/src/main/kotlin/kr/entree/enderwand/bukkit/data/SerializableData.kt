@file:Suppress("EXPERIMENTAL_API_USAGE")

package kr.entree.enderwand.bukkit.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import kr.entree.enderwand.data.StandardData
import kr.entree.enderwand.data.enderWand
import org.bukkit.plugin.Plugin
import java.io.File

fun <T> Plugin.serializableDataOf(
    serializer: KSerializer<T>,
    fileName: String,
    patcher: (T) -> Unit,
    provider: () -> T,
    format: StringFormat = Json.enderWand,
    configure: StandardData.() -> Unit = {}
) = kr.entree.enderwand.data.serializableDataOf(
    serializer,
    File(dataFolder, fileName),
    logger,
    patcher,
    provider,
    format,
    configure
)