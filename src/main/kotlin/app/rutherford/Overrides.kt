package app.rutherford

import app.rutherford.module.configuration.DatabaseConfig
import org.jooq.conf.Settings

class Overrides(
    val databaseConfig: DatabaseConfig? = null,
    val jooqSettings: Settings? = null,
)