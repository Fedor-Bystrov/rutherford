import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Dependencies.Plugins.Versions.kotlin apply false
    id("org.unbroken-dome.test-sets") version Dependencies.Plugins.Versions.testSets apply false
}

allprojects {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    tasks.withType(Test::class.java) {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}