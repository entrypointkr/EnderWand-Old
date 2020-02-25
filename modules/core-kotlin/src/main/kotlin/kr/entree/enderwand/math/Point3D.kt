package kr.entree.enderwand.math

data class Point3D<T>(
    val x: T,
    val y: T,
    val z: T
) where T : Number, T : Comparable<T>