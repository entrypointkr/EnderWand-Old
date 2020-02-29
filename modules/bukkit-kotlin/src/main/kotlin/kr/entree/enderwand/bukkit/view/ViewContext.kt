package kr.entree.enderwand.bukkit.view

/**
 * Created by JunHyung Lim on 2020-01-14
 */
interface ViewContext<T : View> {
    val view: T
    var previousView: View?
}

class ViewContextImpl<T : View>(
    override val view: T,
    override var previousView: View? = null
) : ViewContext<T>