plugins {
    kotlin("jvm") version "1.7.20"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())

    implementation("org.flywaydb:flyway-core:9.10.0")
    implementation("org.postgresql:postgresql:42.5.1")
    implementation("org.testcontainers:postgresql:1.17.6")
    implementation("org.jooq:jooq-codegen:3.17.6")
    implementation("org.jooq:jooq-meta:3.17.6")
}