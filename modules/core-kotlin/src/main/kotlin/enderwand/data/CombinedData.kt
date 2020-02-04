package enderwand.data

/**
 * Created by JunHyung Lim on 2020-01-01
 */
fun combinedDataOf(vararg data: Data) = CombinedData(mutableListOf(*data))

class CombinedData(
    private val data: MutableList<Data> = mutableListOf()
) : Data, MutableList<Data> by data {
    override fun load() {
        data.forEach { it.load() }
    }

    override fun save() {
        data.forEach { it.save() }
    }
}