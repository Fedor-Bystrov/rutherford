import com.google.protobuf.gradle.id
import java.time.Instant.now

plugins {
    kotlin("jvm") version Dependencies.Plugins.Versions.kotlin
    id("org.unbroken-dome.test-sets") version Dependencies.Plugins.Versions.testSets
    id("com.google.cloud.tools.jib") version Dependencies.Plugins.Versions.jib
    id("com.google.protobuf") version "0.9.1" // TODO add to Dependencies.Plugins.Versions
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
    implementation(Dependencies.logbackCore)
    implementation(Dependencies.logbackClassic)
    implementation(Dependencies.slf4jApi)
    implementation(Dependencies.postgresql)
    implementation(Dependencies.jooq)
    implementation(Dependencies.hikari)
    // TODO add to Dependencies
    implementation("io.grpc:grpc-netty:1.51.1")
    implementation("io.grpc:grpc-protobuf:1.51.1")
    implementation("io.grpc:grpc-services:1.51.1")
    implementation("io.grpc:grpc-stub:1.51.1")
    implementation("io.grpc:grpc-kotlin-stub:1.3.0")
    implementation("com.google.protobuf:protobuf-kotlin:3.21.12")

    testImplementation(kotlin("test"))
    testImplementation(project(":core"))
    testImplementation(project(":auth"))
    testImplementation(Dependencies.junitJupiter)
    testImplementation(Dependencies.assertjCore)
    testImplementation(Dependencies.testContainersPg)
    testImplementation(Dependencies.commonsLang3)
    testImplementation(Dependencies.orgJson)
    testImplementation(Dependencies.jsonAssert)
}

val rutherfordJvmArgs = listOf(
    "-server",
    "-XX:+UseG1GC",
    "-XX:MaxRAMPercentage=60",
    "-XX:InitialRAMPercentage=60",
)

application {
    mainClass.set("app.rutherford.MainKt")
    applicationDefaultJvmArgs = rutherfordJvmArgs
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.12"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.51.1"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.3.0:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
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
