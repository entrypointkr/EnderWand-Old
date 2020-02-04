package kr.entree.enderwand.reflection

import java.lang.reflect.AccessibleObject

/**
 * Created by JunHyung Lim on 2020-01-22
 */
inline fun <T : AccessibleObject> T.access(block: (T) -> Unit) {
    isAccessible = true
    block(this)
}