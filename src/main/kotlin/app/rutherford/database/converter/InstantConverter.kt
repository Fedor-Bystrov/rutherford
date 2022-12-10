package app.rutherford.database.converter

import org.jooq.Converter
import java.sql.Timestamp
import java.time.Instant

class InstantConverter : Converter<Timestamp, Instant> {
    override fun from(databaseObject: Timestamp?): Instant? {
        return databaseObject?.toInstant()
    }

    override fun to(userObject: Instant?): Timestamp? {
        return if (userObject == null) null else Timestamp.from(userObject)
    }

    override fun fromType(): Class<Timestamp> {
        return Timestamp::class.java
    }

    override fun toType(): Class<Instant> {
        return Instant::class.java
    }
}