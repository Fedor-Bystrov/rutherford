object Dependencies {
    object Plugins {
        object Versions {
            val kotlin = "1.7.20"
            val testSets = "4.0.0"
            val jib = "3.3.1"
        }
    }

    object Versions {
        val javalin = "5.2.0"
        val jackson = "2.14.1"
        val orgJson = "20220924"
        val logback = "1.4.5"
        val slf4j = "2.0.5"
        val flyway = "9.10.0"
        val postgresql = "42.5.1"
        val hikari = "5.0.1"
        val jooq = "3.17.6"
        val dotnev = "6.4.0"
        val javaJwt = "4.2.1"

        // test
        val junit = "5.9.0"
        val assertJ = "3.23.1"
        val testContainersPg = "1.17.6"
        val commonsLang3 = "3.12.0"
    }

    val javalin = "io.javalin:javalin:${Versions.javalin}"
    val jacksonModuleKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}"
    val jacksonJsr310 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jackson}"
    val orgJson = "org.json:json:${Versions.orgJson}"
    val logbackCore = "ch.qos.logback:logback-core:${Versions.logback}"
    val logbackClassic = "ch.qos.logback:logback-classic:${Versions.logback}"
    val slf4jApi = "org.slf4j:slf4j-api:${Versions.slf4j}"
    val flywayCore = "org.flywaydb:flyway-core:${Versions.flyway}"
    val postgresql = "org.postgresql:postgresql:${Versions.postgresql}"
    val hikari = "com.zaxxer:HikariCP:${Versions.hikari}"
    val jooq = "org.jooq:jooq:${Versions.jooq}"
    val jooqCodegen = "org.jooq:jooq-codegen:${Versions.jooq}"
    val jooqMeta = "org.jooq:jooq-meta:${Versions.jooq}"
    val dotenv = "io.github.cdimascio:dotenv-kotlin:${Versions.dotnev}"
    val javaJwt = "com.auth0:java-jwt:${Versions.javaJwt}"

    // test
    val junitJupiter = "org.junit.jupiter:junit-jupiter:${Versions.junit}"
    val assertjCore = "org.assertj:assertj-core:${Versions.assertJ}"
    val testContainersPg = "org.testcontainers:postgresql:${Versions.testContainersPg}"
    val commonsLang3 = "org.apache.commons:commons-lang3:${Versions.commonsLang3}"
}