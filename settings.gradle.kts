rootProject.name = "enderwand"
includeFlat("core", "bukkit", "core-kotlin", "bukkit-kotlin")

rootProject.children.forEach { project ->
    project.apply {
        projectDir = file("modules/${name.replace(":", File.separator)}")
        val groovyFileName = "${name.replace(':', '-')}.gradle"
        buildFileName = if (File(projectDir, groovyFileName).isFile)
            groovyFileName
        else
            "${groovyFileName}.kts"
        name = "${rootProject.name}-${name.replace(':', '-')}"
    }
}