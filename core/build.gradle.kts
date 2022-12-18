plugins {
    kotlin("jvm") version Dependencies.Plugins.Versions.kotlin
    id("org.unbroken-dome.test-sets") version Dependencies.Plugins.Versions.testSets
}

testSets {
    create("testFunctional")
}

dependencies {
    implementation(Dependencies.jooq)
    implementation(Dependencies.dotenv)

    testImplementation(kotlin("test"))
    testImplementation(Dependencies.junitJupiter)
    testImplementation(Dependencies.assertjCore)
    testImplementation(Dependencies.testContainersPg)
}
