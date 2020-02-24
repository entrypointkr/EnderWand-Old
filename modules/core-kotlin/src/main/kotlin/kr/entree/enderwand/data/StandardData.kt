package kr.entree.enderwand.data

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.entree.enderwand.scheduler.Scheduler
import java.io.*
import java.time.Duration

/**
 * Created by JunHyung Lim on 2020-01-01
 */
fun dataOf(file: File, data: DynamicData) = StandardData(FileFactory(file), data)

fun StandardData.autoSave(
    syncScheduler: Scheduler,
    asyncRunner: (() -> Unit) -> Any,
    period: Duration
) = syncScheduler.runRepeat(period, period) { saveAsync(asyncRunner) }

class StandardData(
    val standardFactory: StandardFactory,
    val data: DynamicData
) : Data, DynamicData by data {
    inline fun useReader(user: (Reader) -> Unit) {
        try {
            standardFactory.createReader().use(user)
        } catch (ex: FileNotFoundException) {
            // Ignore
        }
    }

    override fun load() = useReader { load(it) }

    inline fun loadAsync(
        asyncRunner: (() -> Unit) -> Any = {
            GlobalScope.launch { it() }
        }, crossinline syncRunner: (() -> Unit) -> Any
    ) {
        asyncRunner {
            useReader {
                val text = it.readText()
                syncRunner {
                    data.load(StringReader(text))
                }
            }
        }
    }

    override fun save() = data.save(standardFactory.createWriter())

    inline fun saveAsync(
        asyncRunner: (() -> Unit) -> Any = {
            GlobalScope.launch { it() }
        }
    ) = StringWriter().run {
        save(this)
        asyncRunner {
            standardFactory.createWriter().use {
                it.write(toString())
            }
        }
    }
}

class FileFactory(
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