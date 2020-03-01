plugins {
    id("maven-publish")
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
}

publishing {
    publications {
        create<MavenPublication>("coreKotlin") {
            from(components["java"])
            afterEvaluate {
                artifact(tasks["kotlinSourcesJar"])
            }
        }
    }
}

tasks {
    compileKotlin {
        kotlinOptions.freeCompilerArgs += listOf("-Xuse-experimental=kotlin.Experimental")
    }
}