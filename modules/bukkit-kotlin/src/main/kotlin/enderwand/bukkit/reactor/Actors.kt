package enderwand.bukkit.reactor

import enderwand.bukkit.enderWand
import enderwand.reactor.Registry
import org.bukkit.entity.HumanEntity

/**
 * Created by JunHyung Lim on 2019-12-21
 */
val HumanEntity.onInteract get() = Registry(enderWand.eventReactor.interact, uniqueId)
val HumanEntity.onMove get() = Registry(enderWand.eventReactor.move, uniqueId)
val HumanEntity.onInteractAny get() = Registry(enderWand.eventReactor.interactAny, uniqueId)
val HumanEntity.onChat get() = Registry(enderWand.eventReactor.chat, uniqueId)
val HumanEntity.onChatAsync get() = Registry(enderWand.eventReactor.chatAsync, uniqueId)
val HumanEntity.onResourcePack get() = Registry(enderWand.eventReactor.resourcePack, uniqueId)