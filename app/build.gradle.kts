plugins {
    kotlin("jvm") version Dependencies.Plugins.Versions.kotlin
    id("org.unbroken-dome.test-sets") version Dependencies.Plugins.Versions.testSets
    application
}

group = "app.rutherford"
version = "0.1"

testSets {
    create("testFunctional")
}

dependencies {
    implementation(project(":core"))
    implementation(Dependencies.javalin)
    implementation(Dependencies.jacksonModuleKotlin)
    implementation(Dependencies.jacksonJsr310)
    implementation(Dependencies.orgJson)
    implementation(Dependencies.logbackCore)
    implementation(Dependencies.logbackClassic)
    implementation(Dependencies.slf4jApi)
    implementation(Dependencies.flywayCore)
    implementation(Dependencies.postgresql)
    implementation(Dependencies.hikari)
    implementation(Dependencies.jooq)
    implementation(Dependencies.jooqCodegen)
    implementation(Dependencies.jooqMeta)

    testImplementation(kotlin("test"))
    testImplementation(project(":core"))
    testImplementation(Dependencies.junitJupiter)
    testImplementation(Dependencies.assertjCore)
    testImplementation(Dependencies.testContainersPg)
    testImplementation(Dependencies.commonsLang3)
}

application {
    mainClass.set("app.rutherford.MainKt")
}