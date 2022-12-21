plugins {
    kotlin("jvm") version Dependencies.Plugins.Versions.kotlin
    id("org.unbroken-dome.test-sets") version Dependencies.Plugins.Versions.testSets
}

dependencies {
    implementation(project(":core"))
    implementation(project(":schema"))
    implementation(Dependencies.jooq)
    implementation(Dependencies.javaJwt)

    testImplementation(kotlin("test"))
}
