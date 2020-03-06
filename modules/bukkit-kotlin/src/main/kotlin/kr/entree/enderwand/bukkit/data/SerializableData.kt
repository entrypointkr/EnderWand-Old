@file:Suppress("EXPERIMENTAL_API_USAGE")

package kr.entree.enderwand.bukkit.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kr.entree.enderwand.data.EnderWand
import kr.entree.enderwand.data.StandardData
import org.bukkit.plugin.Plugin
import java.io.File

fun <T> Plugin.serializableDataOf(
    serializer: KSerializer<T>,
    fileName: String,
    patcher: (T) -> Unit,
    provider: () -> T,
    format: StringFormat = Json(JsonConfiguration.EnderWand),
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