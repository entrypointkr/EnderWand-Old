@file:UseSerializers(UUIDSerializer::class, LocalDateTimeSerializer::class)

package kr.entree.enderwand.bukkit.serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kr.entree.enderwand.serialization.LocalDateTimeSerializer
import kr.entree.enderwand.serialization.UUIDSerializer
import org.bukkit.configuration.MemoryConfiguration
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

class SectionCodecTest {
    @Test
    fun `configuration section codec`() {
        val config = MemoryConfiguration()
        val encoder = ConfigurationSectionEncoder(config)
        val data = MyData(
            UUID.randomUUID(), SubMyData(
                mutableMapOf(
                    UUID.randomUUID() to LocalDateTime.now(),
                    UUID.randomUUID() to LocalDateTime.now(),
                    UUID.randomUUID() to LocalDateTime.now()
                )
            )
        )
        val serializer = MyData.serializer()
        serializer.serialize(encoder, data)
    }

    @Serializable
    data class MyData(val uuid: UUID, val sub: SubMyData = SubMyData())

    @Serializable
    data class SubMyData(
        val map: MutableMap<UUID, LocalDateTime> = mutableMapOf(),
        val list: List<UUID> = mutableListOf(UUID.randomUUID(), UUID.randomUUID())
    )
}