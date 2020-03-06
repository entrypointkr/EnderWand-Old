import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val kotlinVersion = "1.3.70".apply { extra["kotlin-version"] = this }
    repositories {
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    }
}

apply(plugin = "base")

allprojects {
    group = "kr.entree"
    version = "1.0-SNAPSHOT"
}

subprojects {
    val kotlin = name.endsWith("-kotlin")
    if (kotlin) {
        apply(plugin = "org.jetbrains.kotlin.jvm")
        apply(plugin = "org.jetbrains.kotlin.kapt")
        apply(plugin = "kotlinx-serialization")
    } else {
        apply(plugin = "java")
    }

    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        if (kotlin) {
            "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
            "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
            "implementation"("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
        } else {
            "compileOnly"("org.projectlombok:lombok:1.18.10")
            "compileOnly"("org.jetbrains:annotations:18.0.0")
            "annotationProcessor"("org.projectlombok:lombok:1.18.10")
        }
        "testImplementation"("org.junit.jupiter:junit-jupiter-api:5.5.2")
        "testImplementation"("org.junit.jupiter:junit-jupiter-engine:5.5.2")
        "testImplementation"("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    }

    tasks {
        withType<Test> {
            useJUnitPlatform()
        }
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
}