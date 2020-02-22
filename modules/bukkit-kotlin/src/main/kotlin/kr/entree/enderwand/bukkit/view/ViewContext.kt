package kr.entree.enderwand.bukkit.view

/**
 * Created by JunHyung Lim on 2020-01-14
 */
interface ViewContext<T> {
    val view: T
}

class ViewContextImpl<T>(override val view: T) : ViewContext<T>