import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
}

group = "app.rutherford"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:5.2.0")
    implementation("org.slf4j:slf4j-simple:2.0.5")
    implementation("org.flywaydb:flyway-core:9.10.0")
    implementation("org.postgresql:postgresql:42.5.1")
    implementation("org.jooq:jooq:3.17.6")
    implementation("org.jooq:jooq-codegen:3.17.6")
    implementation("org.jooq:jooq-meta:3.17.6")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("app.rutherford.MainKt")
}