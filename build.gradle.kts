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
    }

    // required for jib (?) to ignore unchanged layers
    tasks.withType<AbstractArchiveTask>().configureEach {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}