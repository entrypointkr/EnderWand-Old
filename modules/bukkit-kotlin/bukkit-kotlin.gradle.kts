import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import kr.entree.spigradle.kotlin.dependency.protocolLib
import kr.entree.spigradle.kotlin.dependency.spigot
import kr.entree.spigradle.kotlin.dependency.vault
import kr.entree.spigradle.kotlin.repository.jitpack
import kr.entree.spigradle.kotlin.repository.protocolLib
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("kr.entree.spigradle") version "1.1.4"
    id("maven-publish")
}

spigot {
    name = "EnderWand"
    authors = mutableListOf("EntryPoint")
    apiVersion = "1.15"
    softDepends = mutableListOf("Vault", "ProtocolLib", "LuckPerms")
}

repositories {
    jitpack()
    protocolLib()
    mavenLocal() // For protocollib
}

dependencies {
    api(project(":enderwand-core-kotlin"))
    compileOnly(spigot())
    compileOnly(vault()) {
        exclude(module = "spigot-api")
    }
    compileOnly(protocolLib())
    compileOnly("net.luckperms:api:5.0")
    "com.google.auto.service:auto-service:1.0-rc6".let {
        compileOnly(it)
        kapt(it)
    }
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
    testImplementation(spigot())
}

tasks {
    shadowJar {
        archiveClassifier.set("shaded")
        minimize {
            exclude(dependency("org.jetbrains.kotlin:.*"))
            exclude(dependency("org.jetbrains.kotlinx:.*"))
        }
    }
    val nostdlibJar = create<ShadowJar>("nostdlibJar") {
        dependsOn(shadowJar)
        group = "shadow"
        archiveClassifier.set("nostdlib")
        doFirst {
            spigot.softDepends.add("Spikot")
            spigotPluginYaml { actions.forEach { it.execute(this) } }
        }
        from(project.convention.getPlugin<JavaPluginConvention>().sourceSets["main"].output)
        configurations = listOf(project.configurations.runtimeClasspath.get())
        dependencies {
            exclude(dependency("org.jetbrains.kotlin:.*"))
            exclude(dependency("org.jetbrains.kotlinx:.*"))
        }
    }
    build { dependsOn(shadowJar, nostdlibJar) }
}


publishing {
    publications {
        create<MavenPublication>("bukkitKotlin") {
            from(components["java"])
            artifact(tasks["shadowJar"])
            afterEvaluate {
                artifact(tasks["kotlinSourcesJar"])
            }
        }
    }
}

artifacts {
    archives(tasks.kotlinSourcesJar)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf(
                "-Xuse-experimental=kotlin.Experimental",
                "-Xinline-classes"
            )
        }
    }
}