package kr.entree.enderwand.bukkit.inventory

inline class JobResult(val remain: Int) {
    val success get() = remain <= 0
    val failure get() = !success

    companion object {
        val SUCCESS = JobResult(0)
    }

    inline fun onSuccess(block: JobResult.() -> Unit): JobResult {
        if (success) block(this)
        return this
    }

    inline fun onFailure(block: JobResult.() -> Unit): JobResult {
        if (failure) block(this)
        return this
    }
}