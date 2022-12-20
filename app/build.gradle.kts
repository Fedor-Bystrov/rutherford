import java.time.Instant.now

plugins {
    kotlin("jvm") version Dependencies.Plugins.Versions.kotlin
    id("org.unbroken-dome.test-sets") version Dependencies.Plugins.Versions.testSets
    id("com.github.johnrengelman.shadow") version Dependencies.Plugins.Versions.shadow
    id("com.google.cloud.tools.jib") version Dependencies.Plugins.Versions.jib
    application
}

testSets {
    create("testFunctional")
}

dependencies {
    implementation(project(":schema"))
    implementation(project(":core"))
    implementation(project(":auth"))
    implementation(Dependencies.javalin)
    implementation(Dependencies.jacksonModuleKotlin)
    implementation(Dependencies.jacksonJsr310)
    implementation(Dependencies.orgJson)
    implementation(Dependencies.logbackCore)
    implementation(Dependencies.logbackClassic)
    implementation(Dependencies.slf4jApi)
    implementation(Dependencies.postgresql)
    implementation(Dependencies.jooq)
    implementation(Dependencies.hikari)

    testImplementation(kotlin("test"))
    testImplementation(project(":core"))
    testImplementation(project(":auth"))
    testImplementation(Dependencies.junitJupiter)
    testImplementation(Dependencies.assertjCore)
    testImplementation(Dependencies.testContainersPg)
    testImplementation(Dependencies.commonsLang3)
}

application {
    mainClass.set("app.rutherford.MainKt")
}

tasks.shadowJar {
    isZip64 = true
    mergeServiceFiles()
    archiveBaseName.set("rutherford")
    archiveVersion.set(now().epochSecond.toString())
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.check {
    dependsOn("testFunctional")
}

tasks.runShadow {
    dependsOn(tasks.check)
}