package kr.entree.enderwand.bukkit

import kr.entree.enderwand.bukkit.command.bukkitCommand
import kr.entree.enderwand.bukkit.command.player
import kr.entree.enderwand.collection.toReader
import kr.entree.enderwand.command.executor.execute
import kr.entree.enderwand.command.tabcompleter.tabComplete
import org.bukkit.entity.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

/**
 * Created by JunHyung Lim on 2020-01-09
 */
class CommandTest {
    companion object {
        @BeforeAll
        @JvmStatic
        fun initialize() {
            injectServer()
        }
    }

    @Test
    fun full() {
        var buy = 0
        val playerName = "EntryPoint"
        var add = false
        var optionalAdd = false
        val cmd = bukkitCommand {
            child("구매") {
                aliases = setOf("buy")
                description = "자신이 서 있는 청크를 구매합니다."
                executor {
                    buy++
                }
            }
            child("소유권") {
                child("추가") {
                    description = "자신이 서 있는 청크의 소유권을 <닉네임> 에게 추가합니다."
                    arguments {
                        player("닉네임") { optional = true }
                        executor {
                            val player = args.getOrNull(0) as? Player
                            if (player == null) {
                                optionalAdd = true
                                return@executor
                            }
                            if (player.name == playerName) {
                                add = true
                            }
                        }
                    }
                }
                child("추가2") {
                    description = "테스트"
                    executor {

                    }
                }
            }
        }
        val defSender = createBukkitSender { println(it) }
        cmd.execute(defSender, listOf("buy").toReader())
        cmd.execute(defSender, listOf("구매").toReader())
        cmd.execute(defSender, listOf("소유권", "추가", playerName).toReader())
        cmd.execute(defSender, listOf("소유권", "추가").toReader())
        cmd.execute(defSender, listOf("구").toReader())
        assertEquals(2, buy)
        assertTrue(add)
        assertTrue(optionalAdd)
        assertEquals(listOf("추가", "추가2"), cmd.tabComplete(defSender, listOf("소유권", "추").toReader()))
        assertEquals(listOf("추가", "추가2"), cmd.tabComplete(defSender, listOf("소유권", "").toReader()))
        assertEquals(listOf("추가2"), cmd.tabComplete(defSender, listOf("소유권", "추가2").toReader()))
    }

    @Test
    fun help() {
        val cmd = bukkitCommand {
            child("1") {
                child("a") {
                    child("A") {
                        child("ㄱ") {
                            executor { }
                        }
                        child("ㄴ") {
                            executor { }
                        }
                    }
                }
            }
            child("2") {
                arguments {
                    string("arg1")
                    player("arg2") { optional = true }
                    executor { }
                }
            }
        }
        cmd.execute(createBukkitSender { println("1: $it") }, listOf("1", "a", "A").toReader())
        cmd.execute(createBukkitSender { println("2: $it") }, listOf("2").toReader())
    }
}