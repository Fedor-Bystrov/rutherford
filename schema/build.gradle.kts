import org.jooq.meta.jaxb.ForcedType

plugins {
    kotlin("jvm") version Dependencies.Plugins.Versions.kotlin
}

dependencies {
    implementation(Dependencies.jooq) // TODO clean up
    testImplementation(kotlin("test"))
}


tasks.register<GenerateSchemaTask>("generateSchema") {
    dockerimageName.set("postgres:15.1")
    outputPackage.set("app.rutherford.schema.generated")
    forcedTypes.set(
        listOf(
            ForcedType()
                .withUserType("java.time.Instant")
                .withConverter("app.rutherford.schema.converter.InstantConverter")
                .withIncludeTypes("Timestamp"),
//            ForcedType() // TODO uncomment
//                .withUserType("app.rutherford.core.ApplicationName")
//                .withConverter("org.jooq.impl.EnumConverter")
//                .withIncludeExpression(
//                    """.*\.AUTH_USER\.application_name"""
//                ),
//            ForcedType()
//                .withUserType("app.rutherford.core.entity.Entity.State")
//                .withConverter("org.jooq.impl.EnumConverter")
//                .withIncludeExpression(
//                    """.*\.auth_user_token\.state"""
//                )
        )
    )
}
