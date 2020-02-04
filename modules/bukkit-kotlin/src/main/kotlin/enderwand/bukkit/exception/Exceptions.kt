package enderwand.bukkit.exception

import org.bukkit.Material


/**
 * Created by JunHyung Lim on 2020-01-09
 */
class UnknownPlayerException(val query: Any) : RuntimeException()

class UnknownMaterialException(val query: String) : RuntimeException()

class NotCraftingMaterialException(val material: Material) : RuntimeException()