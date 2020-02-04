package enderwand.data

import java.io.File

/**
 * Created by JunHyung Lim on 2020-01-10
 */
infix fun String.of(parent: File) = File(parent, this)