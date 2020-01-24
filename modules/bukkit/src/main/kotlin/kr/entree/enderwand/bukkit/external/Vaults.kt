package kr.entree.enderwand.bukkit.external

import kr.entree.enderwand.bukkit.player.toOfflinePlayer
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.*

/**
 * Created by JunHyung Lim on 2020-01-21
 */
val economy: Economy by lazy {
    Bukkit.getServicesManager().load(Economy::class.java)!!
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
        give(amount)
    }

    fun give(amount: Number) = economy.depositPlayer(
        uuid.toOfflinePlayer(),
        amount.toDouble()
    ).let {
        EconomyResult(amount.toDouble() - it.balance)
    }

    fun take(amount: Number) = economy.withdrawPlayer(
        uuid.toOfflinePlayer(),
        amount.toDouble()
    ).let {
        EconomyResult(amount.toDouble() - it.balance)
    }

    fun get() = economy.getBalance(uuid.toOfflinePlayer())
}

val OfflinePlayer.money get() = EconomyHolder(uniqueId)