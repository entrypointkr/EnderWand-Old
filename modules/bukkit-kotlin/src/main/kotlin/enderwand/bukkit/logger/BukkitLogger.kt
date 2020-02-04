package enderwand.bukkit.logger

import org.bukkit.Bukkit
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Created by JunHyung Lim on 2020-01-18
 */
val logger get() = Bukkit.getLogger()

fun Logger.warning(ex: Throwable, message: String = "Exception!") =
    log(Level.WARNING, message, ex)