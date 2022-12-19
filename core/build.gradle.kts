plugins {
    kotlin("jvm") version Dependencies.Plugins.Versions.kotlin
    id("org.unbroken-dome.test-sets") version Dependencies.Plugins.Versions.testSets
}

dependencies {
    implementation(Dependencies.jooq)
    implementation(Dependencies.dotenv)

    testImplementation(kotlin("test"))
    testImplementation(Dependencies.junitJupiter)
    testImplementation(Dependencies.assertjCore)
}
