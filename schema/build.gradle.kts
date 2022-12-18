plugins {
    kotlin("jvm") version Dependencies.Plugins.Versions.kotlin
}

dependencies {
    implementation(Dependencies.jooq)

    testImplementation(kotlin("test"))
}


tasks.register<GenerateSchemaTask>("generateSchema")
