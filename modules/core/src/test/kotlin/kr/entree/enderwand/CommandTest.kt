package kr.entree.enderwand

import kr.entree.enderwand.collection.toReader
import kr.entree.enderwand.command.command
import kr.entree.enderwand.command.executor.execute
import kr.entree.enderwand.command.sender.ConsoleSender
import kr.entree.enderwand.command.sender.Sender
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * Created by JunHyung Lim on 2020-01-09
 */
class CommandTest {
    @Test
    fun simple() {
        val msg = "Done!"
        var result = 0
        var msgReceived = false
        command<Sender> {
            executor {
                result = args[0].toInt() + args[1].toInt()
                sender.sendMessage(msg)
            }
        }.execute(createSender { msgReceived = it == msg }, listOf("2", "3").toReader())
        assertEquals(5, result)
        assertTrue(msgReceived)
    }
}