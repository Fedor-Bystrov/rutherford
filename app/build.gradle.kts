import java.time.Instant.now

plugins {
    kotlin("jvm") version Dependencies.Plugins.Versions.kotlin
    id("org.unbroken-dome.test-sets") version Dependencies.Plugins.Versions.testSets
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

val rutherfordJvmArgs = listOf( // TODO validate this props
    "-server",
    "-Djava.awt.headless=true",
    "-Xms128m",
    "-Xmx256m",
    "-XX:+UseG1GC",
    "-XX:MaxGCPauseMillis=100"
)

application {
    mainClass.set("app.rutherford.MainKt")
    applicationDefaultJvmArgs = rutherfordJvmArgs
}

jib {
    from {
        image = "eclipse-temurin:17-jre-alpine@sha256:15c47cd825f2bf77b40860bc9c18d4659c72584d16ef5f533eb49a232b3702f3"
    }
    to {
        image = "rutherford"
        tags = setOf(now().epochSecond.toString(), "latest")
    }
    container {
        jvmFlags = rutherfordJvmArgs
    }
}

tasks.check {
    dependsOn("testFunctional")
}

tasks.jib {
    dependsOn(tasks.build)
}

tasks.jibDockerBuild {
    dependsOn(tasks.build)
}