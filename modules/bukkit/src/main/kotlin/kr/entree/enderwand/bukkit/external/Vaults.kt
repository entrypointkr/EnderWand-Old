package kr.entree.enderwand.bukkit.external

import kr.entree.enderwand.bukkit.player.toOfflinePlayer
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.*

/**
 * Created by JunHyung Lim on 2020-01-21
 */
val economy: Economy by lazy {
    Bukkit.getServicesManager().load(Economy::class.java)!!
}

class EconomyResult(val success: Boolean)

inline class EconomyHolder(val uuid: UUID) {
    operator fun plusAssign(amount: Number) {
        give(amount)
    }

    fun give(amount: Number) = EconomyResult(
        economy.depositPlayer(
            uuid.toOfflinePlayer(),
            amount.toDouble()
        ).type == EconomyResponse.ResponseType.SUCCESS
    )

    fun take(amount: Number) = EconomyResult(
        economy.withdrawPlayer(
            uuid.toOfflinePlayer(),
            amount.toDouble()
        ).type == EconomyResponse.ResponseType.SUCCESS
    )

    fun get() = economy.getBalance(uuid.toOfflinePlayer())
}

val OfflinePlayer.money get() = EconomyHolder(uniqueId)