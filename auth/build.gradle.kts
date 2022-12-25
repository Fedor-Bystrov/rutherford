plugins {
    kotlin("jvm") version Dependencies.Plugins.Versions.kotlin
    id("org.unbroken-dome.test-sets") version Dependencies.Plugins.Versions.testSets
}

dependencies {
    implementation(project(":core"))
    implementation(project(":schema"))
    implementation(Dependencies.jooq)
    implementation(Dependencies.javaJwt)
    implementation(Dependencies.orgJson)
    implementation(Dependencies.passay)
    implementation(Dependencies.bcprov)
    implementation(Dependencies.javalin)

    testImplementation(kotlin("test"))
    testImplementation(Dependencies.junitJupiter)
    testImplementation(Dependencies.assertjCore)
    testImplementation(Dependencies.commonsLang3)
    testImplementation(Dependencies.mockitoCore)
}
