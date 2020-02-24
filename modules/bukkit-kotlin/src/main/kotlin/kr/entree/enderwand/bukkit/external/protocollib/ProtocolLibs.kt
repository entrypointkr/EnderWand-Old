package kr.entree.enderwand.bukkit.external.protocollib

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.*
import kr.entree.enderwand.bukkit.enderWand
import org.bukkit.plugin.Plugin

/**
 * Created by JunHyung Lim on 2020-01-22
 */
fun Plugin.registerPacketListener(
    vararg types: PacketType,
    priority: ListenerPriority = ListenerPriority.NORMAL,
    receiver: PacketEvent.() -> Unit
) = ProtocolLibrary.getProtocolManager().addPacketListener(
    FunctionalPacketAdapter(
        this,
        priority,
        types.asIterable(),
        receiver
    )
)

fun NetworkMarker.addOutputHandler(
    plugin: Plugin = enderWand,
    priority: ListenerPriority = ListenerPriority.NORMAL,
    receiver: PacketEvent.(ByteArray) -> ByteArray
) = addOutputHandler(
    FunctionalPacketOutputHandler(
        plugin,
        priority,
        receiver
    )
)

class FunctionalPacketAdapter(
    aPlugin: Plugin,
    priority: ListenerPriority,
    types: Iterable<PacketType>,
    val receiver: (PacketEvent) -> Unit
) : PacketAdapter(aPlugin, priority, *(types.distinct().toTypedArray())) {
    override fun onPacketSending(event: PacketEvent) {
        receiver(event)
    }

    override fun onPacketReceiving(event: PacketEvent) {
        receiver(event)
    }
}

class FunctionalPacketOutputHandler(
    val aPlugin: Plugin,
    val listenerPriority: ListenerPriority,
    val receiver: PacketEvent.(ByteArray) -> ByteArray
) : PacketOutputHandler {
    override fun getPriority(): ListenerPriority {
        return listenerPriority
    }

    override fun handle(p0: PacketEvent, p1: ByteArray): ByteArray {
        return receiver(p0, p1)
    }

    override fun getPlugin(): Plugin {
        return aPlugin
    }
}