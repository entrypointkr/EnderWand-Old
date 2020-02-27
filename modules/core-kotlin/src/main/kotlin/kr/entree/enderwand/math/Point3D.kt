package kr.entree.enderwand.math

import kotlinx.serialization.Serializable


@Serializable
data class Point3D<T>(
    val x: T,
    val y: T,
    val z: T
) where T : Number, T : Comparable<T>