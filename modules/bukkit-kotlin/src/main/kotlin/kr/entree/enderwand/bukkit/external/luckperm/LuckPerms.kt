package kr.entree.enderwand.bukkit.external.luckperm

import net.luckperms.api.LuckPerms
import net.luckperms.api.query.QueryOptions
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity

/**
 * Created by JunHyung Lim on 2020-01-26
 */
val luckPerms: LuckPerms by lazy {
    Bukkit.getServicesManager().load(LuckPerms::class.java)!!
}

val HumanEntity.toLuckpermUser get() = luckPerms.userManager.getUser(uniqueId)

val HumanEntity.luckpermGroup
    get() = toLuckpermUser?.primaryGroup?.run {
        luckPerms.groupManager.getGroup(this)
    }

val HumanEntity.luckpermGroupName
    get() = luckpermGroup?.name

val HumanEntity.luckpermGroupDisplayName
    get() = luckpermGroup?.displayName

val HumanEntity.luckpermPrefix
    get() = toLuckpermUser?.cachedData
        ?.getMetaData(QueryOptions.defaultContextualOptions())?.prefix