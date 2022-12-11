//package app.rutherford.database.repository
//
//import app.rutherford.database.entity.AuthUser
//import app.rutherford.database.entity.AuthUser.Builder.Companion.authUser
//import app.rutherford.database.schema.tables.records.AuthUserRecord
//import org.jooq.DSLContext
//
//class AuthUserRepository(private val dslContext: DSLContext) {
//    fun findAll(): List<AuthUser> {
//        return listOf()
//    }
//
//    fun fromRecord(record: AuthUserRecord): AuthUser {
//        return authUser()
//            .id(record.id)
//            .createdAt(record.createdAt)
//            .updatedAt(record.updatedAt)
//            .lastLogin(record.lastLogin)
//            .applicationName(record.applicationName)
//            .email(record.email)
//            .emailConfirmed(record.emailConfirmed)
//            .passwordHash(record.passwordHash)
//            .build()
//    }
//
//    fun toRecord(entity: AuthUser): AuthUserRecord {
//        return AuthUserRecord()
//            .setId(entity.id)
//            .setCreatedAt(entity.createdAt)
//            .setUpdatedAt(entity.updatedAt)
//            .setLastLogin(entity.lastLogin)
//            .setApplicationName(entity.applicationName)
//            .setEmail(entity.email)
//            .setEmailConfirmed(entity.emailConfirmed)
//            .setPasswordHash(entity.passwordHash)
//    }
//}