package enderwand.data

import java.nio.ByteBuffer

/**
 * Created by JunHyung Lim on 2020-01-22
 */
fun ByteBuffer.getVarInt(): Int {
    var i = 0
    var j = 0
    while (true) {
        val b0 = get().toInt()
        i = i or (b0 and 127) shl j++ * 7
        if (j > 5) {
            throw RuntimeException("VarInt too big")
        }
        if (b0 and 128 != 128) {
            break
        }
    }
    return i
}