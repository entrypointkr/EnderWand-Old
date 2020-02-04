package kr.entree.enderwand.bukkit

import kr.entree.enderwand.bukkit.command.bukkitCommand
import kr.entree.enderwand.bukkit.command.executes
import kr.entree.enderwand.bukkit.command.player
import kr.entree.enderwand.collection.readerOf
import kr.entree.enderwand.collection.toReader
import kr.entree.enderwand.command.argument.double
import kr.entree.enderwand.command.argument.int
import kr.entree.enderwand.command.argument.string
import kr.entree.enderwand.command.executor.execute
import kr.entree.enderwand.command.tabcompleter.tabComplete
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
        var complex = false
        val complexCase = listOf(
            "소유권", "3",
            "a", "1", "1.0",
            "b", "2", "2.0",
            "c", "3", "3.0"
        )
        val cmd = bukkitCommand {
            child("구매") {
                aliases = setOf("buy")
                description = "자신이 서 있는 청크를 구매합니다."
                executes {
                    buy++
                }
            }
            child("소유권") {
                child("추가") {
                    description = "자신이 서 있는 청크의 소유권을 <닉네임> 에게 추가합니다."
                    executes(player("닉네임").option()) { (player) ->
                        if (player == null) {
                            optionalAdd = true
                            return@executes
                        }
                        if (player.name == playerName) {
                            add = true
                        }
                    }
                }
                child("추가2") {
                    description = "테스트"
                    executes {}
                }
                child("3") {
                    executes(
                        string(), int(), double(),
                        string().option(), int().option(), double().option(),
                        string(), int(), double()
                    ) { (s1, i1, d1, s2, i2, d2, s3, i3, d3) ->
                        complex = true
                        listOf(s1, s2, s3).forEach { assert(it is String) }
                        listOf(i1, i2, i3).forEach { assert(it is Int) }
                        listOf(d1, d2, d3).forEach { assert(it is Double) }
                    }
                }
            }
        }
        val defSender = createBukkitSender { println(it) }
        cmd.execute(defSender, readerOf("buy"))
        cmd.execute(defSender, readerOf("구매"))
        cmd.execute(defSender, readerOf("소유권", "추가", playerName))
        cmd.execute(defSender, readerOf("소유권", "추가"))
        cmd.execute(defSender, readerOf("구"))
        assertEquals(2, buy)
        assertTrue(add)
        assertTrue(optionalAdd)
        assertEquals(listOf("추가", "추가2"), cmd.tabComplete(defSender, readerOf("소유권", "추")))
        assertEquals(listOf("추가", "추가2", "3"), cmd.tabComplete(defSender, readerOf("소유권", "")))
        assertEquals(listOf("추가2"), cmd.tabComplete(defSender, readerOf("소유권", "추가2")))
        cmd.execute(defSender, complexCase.toReader())
        assertTrue(complex)
    }

    @Test
    fun help() {
        val cmd = bukkitCommand {
            child("1") {
                child("a") {
                    child("A") {
                        child("ㄱ") {
                            executes { }
                        }
                        child("ㄴ") {
                            executes { }
                        }
                    }
                }
            }
            child("2") {
                executes(string("arg1"), player("args2")) {
                    println("invoked")
                }
            }
        }
        cmd.execute(createBukkitSender { println("1: $it") }, readerOf("1", "a", "A"))
        cmd.execute(createBukkitSender { println("2: $it") }, readerOf("2"))
    }
}