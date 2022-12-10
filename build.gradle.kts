import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.7.21"
    application
}

group = "app.rutherford"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:5.2.0")
    implementation("org.slf4j:slf4j-simple:2.0.5")
    implementation("org.flywaydb:flyway-core:9.10.0")
    implementation("org.postgresql:postgresql:42.5.1")
    implementation("jakarta.el:jakarta.el-api:5.0.1") // for hiber
    implementation("org.hibernate.orm:hibernate-core:6.1.6.Final")
    implementation("org.hibernate.orm:hibernate-hikaricp:6.1.5.Final")
    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")

    testImplementation(kotlin("test"))
    testImplementation("org.hibernate.orm:hibernate-testing:6.1.5.Final") // TODO do I need this?
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("MainKt")
}