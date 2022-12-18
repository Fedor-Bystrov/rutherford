plugins {
    kotlin("jvm") version "1.7.20"
    id("org.unbroken-dome.test-sets") version "4.0.0"
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
