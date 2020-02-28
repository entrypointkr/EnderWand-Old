package kr.entree.enderwand.data

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.entree.enderwand.scheduler.Scheduler
import java.io.*
import java.time.Duration
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Created by JunHyung Lim on 2020-01-01
 */
fun dataOf(file: File, data: DynamicData, logger: Logger) = StandardData(FileFactory(file), data, logger)

fun StandardData.autoSave(
    syncScheduler: Scheduler,
    asyncRunner: (() -> Unit) -> Any,
    period: Duration
) = syncScheduler.runRepeat(period, period) { saveAsync(asyncRunner) }

class StandardData(
    val standardFactory: StandardFactory,
    val data: DynamicData,
    val logger: Logger
) : Data, DynamicData by data {
    inline fun useReader(user: (Reader) -> Unit) {
        try {
            standardFactory.createReader().use(user)
        } catch (ex: FileNotFoundException) {
            // Ignore
        }
    }

    fun read(reader: Reader) = try {
        load(reader)
    } catch (ex: Exception) {
        logger.log(Level.SEVERE, ex) { "Error while file $standardFactory reading" }
    }

    fun write(writer: Writer) = try {
        save(writer)
    } catch (ex: Exception) {
        logger.log(Level.SEVERE, ex) { "Error while file $standardFactory writing" }
    }

    override fun load() = useReader { read(it) }

    inline fun loadAsync(
        asyncRunner: (() -> Unit) -> Any = {
            GlobalScope.launch { it() }
        }, crossinline syncRunner: (() -> Unit) -> Any
    ) {
        asyncRunner {
            useReader {
                val text = it.readText()
                syncRunner {
                    read(StringReader(text))
                }
            }
        }
    }

    override fun save() = standardFactory.createWriter().use {
        data.save(it)
    }

    inline fun saveAsync(
        asyncRunner: (() -> Unit) -> Any = {
            GlobalScope.launch { it() }
        }
    ) = StringWriter().run {
        write(this)
        asyncRunner {
            standardFactory.createWriter().use {
                it.write(toString())
            }
        }
    }
}

data class FileFactory(
    private val file: File
) : StandardFactory {
    override fun createReader() = file.bufferedReader()

    override fun createWriter(): Writer {
        file.parentFile?.mkdirs()
        return file.bufferedWriter()
    }
}

interface DynamicData {
    fun load(reader: Reader)

    fun save(writer: Writer)
}

interface StandardFactory {
    fun createReader(): Reader

    fun createWriter(): Writer
}