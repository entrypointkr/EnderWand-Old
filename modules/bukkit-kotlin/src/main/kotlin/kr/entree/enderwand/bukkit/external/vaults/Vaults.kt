package kr.entree.enderwand.bukkit.external.vaults

import kr.entree.enderwand.bukkit.player.toOfflinePlayer
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.permission.Permission
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.*

/**
 * Created by JunHyung Lim on 2020-01-21
 */
val economy: Economy by lazy {
    Bukkit.getServicesManager().load(Economy::class.java)!!
}

val permission: Permission by lazy {
    Bukkit.getServicesManager().load(Permission::class.java)!!
}

inline class EconomyResult(val remain: Double) {
    val success get() = remain <= 0
    val failure get() = remain > 0

    inline fun onSuccess(block: EconomyResult.() -> Unit): EconomyResult {
        if (success) block()
        return this
    }

    inline fun onFailure(block: EconomyResult.() -> Unit): EconomyResult {
        if (failure) block()
        return this
    }
}

inline class EconomyHolder(val uuid: UUID) {
    operator fun plusAssign(amount: Number) {
        giveMoney(amount)
    }

    fun giveMoney(amount: Number) = economy.depositPlayer(
        uuid.toOfflinePlayer(),
        amount.toDouble()
    ).let {
        EconomyResult(amount.toDouble() - it.balance)
    }

    fun takeMoney(amount: Number) = economy.withdrawPlayer(
        uuid.toOfflinePlayer(),
        amount.toDouble()
    ).let {
        EconomyResult(amount.toDouble() - it.balance)
    }

    fun hasMoney(amount: Number) = get() >= amount.toDouble()

    fun get() = economy.getBalance(uuid.toOfflinePlayer())
}

val OfflinePlayer.money get() = EconomyHolder(uniqueId)

val Player.group get() = permission.getPrimaryGroup(this)

val Player.groups get() = permission.getPlayerGroups(this)