plugins {
    kotlin("jvm") version "1.7.20"
    id("org.unbroken-dome.test-sets") version "4.0.0"
}

testSets {
    create("testFunctional")
}

dependencies {
    implementation("io.javalin:javalin:5.2.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.1")
    implementation("org.json:json:20220924")
    implementation("ch.qos.logback:logback-core:1.4.5")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("org.flywaydb:flyway-core:9.10.0")
    implementation("org.postgresql:postgresql:42.5.1")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.jooq:jooq:3.17.6")
    implementation("org.jooq:jooq-codegen:3.17.6")
    implementation("org.jooq:jooq-meta:3.17.6")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.0")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.testcontainers:postgresql:1.17.6")
    testImplementation("org.apache.commons:commons-lang3:3.12.0")
}
