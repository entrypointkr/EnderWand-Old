package kr.entree.enderwand.bukkit.player

import kotlinx.coroutines.launch
import kr.entree.enderwand.bukkit.coroutine.awaitJoin
import kr.entree.enderwand.bukkit.coroutine.scope
import kr.entree.enderwand.bukkit.enderWand
import kr.entree.enderwand.bukkit.sender.tell
import org.bukkit.OfflinePlayer

/**
 * Created by JunHyung Lim on 2020-02-19
 */
fun OfflinePlayer.tellOrLater(message: String) {
    if (player?.isOnline == true) {
        player?.tell(message)
    } else {
        enderWand.scope.launch {
            awaitJoin(uniqueId).tell(message)
        }
    }
}