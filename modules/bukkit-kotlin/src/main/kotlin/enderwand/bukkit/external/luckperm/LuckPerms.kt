package enderwand.bukkit.external.luckperm

import net.luckperms.api.LuckPerms
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity

/**
 * Created by JunHyung Lim on 2020-01-26
 */
val luckPerms: LuckPerms by lazy {
    Bukkit.getServicesManager().load(LuckPerms::class.java)!!
}

val HumanEntity.group
    get() = luckPerms.userManager.getUser(uniqueId)?.primaryGroup?.run {
        luckPerms.groupManager.getGroup(this)
    }?.name