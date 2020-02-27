package kr.entree.enderwand.serialization

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@Serializer(forClass = UUID::class)
object UUIDSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("UUID")

    override fun serialize(encoder: Encoder, obj: UUID) = encoder.encodeString(obj.toString())

    override fun deserialize(decoder: Decoder) = UUID.fromString(decoder.decodeString())
}

@Serializer(forClass = LocalDateTime::class)
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("LocalDateTime")

    override fun serialize(encoder: Encoder, obj: LocalDateTime) = encoder.encodeString(obj.toString())

    override fun deserialize(decoder: Decoder) = LocalDateTime.parse(decoder.decodeString())
}

@Serializer(forClass = Duration::class)
object DurationSerializer : KSerializer<Duration> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("Duration")

    override fun serialize(encoder: Encoder, obj: Duration) = encoder.encodeString(obj.toString())

    override fun deserialize(decoder: Decoder) = Duration.parse(decoder.decodeString())
}