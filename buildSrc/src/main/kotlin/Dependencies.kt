object Dependencies {
    object Plugins {
        object Versions {
            const val kotlin = "1.8.20"
            const val testSets = "4.0.0"
            const val jib = "3.3.1"
        }
    }

    object Versions {
        const val javalin = "5.2.0"
        const val jackson = "2.14.1"
        const val orgJson = "20220924"
        const val logback = "1.4.5"
        const val slf4j = "2.0.5"
        const val flyway = "9.10.0"
        const val postgresql = "42.5.1"
        const val hikari = "5.0.1"
        const val jooq = "3.17.6"
        const val dotnev = "6.4.0"
        const val javaJwt = "4.2.1"
        const val passay = "1.6.2"
        const val bcprov = "1.70"
        const val commonsValidator = "1.7"

        // test
        const val junit = "5.9.0"
        const val assertJ = "3.23.1"
        const val testContainersPg = "1.17.6"
        const val commonsLang3 = "3.12.0"
        const val mockitoCore = "4.10.0"
        const val jsonAssert = "1.5.1"
    }

    const val javalin = "io.javalin:javalin:${Versions.javalin}"
    const val jacksonModuleKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}"
    const val jacksonJsr310 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jackson}"
    const val orgJson = "org.json:json:${Versions.orgJson}"
    const val logbackCore = "ch.qos.logback:logback-core:${Versions.logback}"
    const val logbackClassic = "ch.qos.logback:logback-classic:${Versions.logback}"
    const val slf4jApi = "org.slf4j:slf4j-api:${Versions.slf4j}"
    const val flywayCore = "org.flywaydb:flyway-core:${Versions.flyway}"
    const val postgresql = "org.postgresql:postgresql:${Versions.postgresql}"
    const val hikari = "com.zaxxer:HikariCP:${Versions.hikari}"
    const val jooq = "org.jooq:jooq:${Versions.jooq}"
    const val dotenv = "io.github.cdimascio:dotenv-kotlin:${Versions.dotnev}"
    const val javaJwt = "com.auth0:java-jwt:${Versions.javaJwt}"
    const val passay = "org.passay:passay:${Versions.passay}"
    const val bcprov = "org.bouncycastle:bcprov-jdk15on:${Versions.bcprov}"
    const val commonsValidator = "commons-validator:commons-validator:${Versions.commonsValidator}"

    // test
    const val junitJupiter = "org.junit.jupiter:junit-jupiter:${Versions.junit}"
    const val assertjCore = "org.assertj:assertj-core:${Versions.assertJ}"
    const val testContainersPg = "org.testcontainers:postgresql:${Versions.testContainersPg}"
    const val commonsLang3 = "org.apache.commons:commons-lang3:${Versions.commonsLang3}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
    const val jsonAssert = "org.skyscreamer:jsonassert:${Versions.jsonAssert}"
}
