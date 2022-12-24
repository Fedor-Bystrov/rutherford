/*
 * This file is generated by jOOQ.
 */
package app.rutherford.schema.generated.tables.records


import app.rutherford.core.ApplicationName
import app.rutherford.schema.generated.tables.AuthUser

import java.time.Instant
import java.util.UUID

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record9
import org.jooq.Row9
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class AuthUserRecord() : UpdatableRecordImpl<AuthUserRecord>(AuthUser.AUTH_USER), Record9<UUID?, Instant?, Instant?, Instant?, ApplicationName?, String?, Boolean?, ByteArray?, ByteArray?> {

    open var id: UUID?
        set(value): Unit = set(0, value)
        get(): UUID? = get(0) as UUID?

    open var createdAt: Instant?
        set(value): Unit = set(1, value)
        get(): Instant? = get(1) as Instant?

    open var updatedAt: Instant?
        set(value): Unit = set(2, value)
        get(): Instant? = get(2) as Instant?

    open var lastLogin: Instant?
        set(value): Unit = set(3, value)
        get(): Instant? = get(3) as Instant?

    open var applicationName: ApplicationName?
        set(value): Unit = set(4, value)
        get(): ApplicationName? = get(4) as ApplicationName?

    open var email: String?
        set(value): Unit = set(5, value)
        get(): String? = get(5) as String?

    open var emailConfirmed: Boolean?
        set(value): Unit = set(6, value)
        get(): Boolean? = get(6) as Boolean?

    open var salt: ByteArray?
        set(value): Unit = set(7, value)
        get(): ByteArray? = get(7) as ByteArray?

    open var passwordHash: ByteArray?
        set(value): Unit = set(8, value)
        get(): ByteArray? = get(8) as ByteArray?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row9<UUID?, Instant?, Instant?, Instant?, ApplicationName?, String?, Boolean?, ByteArray?, ByteArray?> = super.fieldsRow() as Row9<UUID?, Instant?, Instant?, Instant?, ApplicationName?, String?, Boolean?, ByteArray?, ByteArray?>
    override fun valuesRow(): Row9<UUID?, Instant?, Instant?, Instant?, ApplicationName?, String?, Boolean?, ByteArray?, ByteArray?> = super.valuesRow() as Row9<UUID?, Instant?, Instant?, Instant?, ApplicationName?, String?, Boolean?, ByteArray?, ByteArray?>
    override fun field1(): Field<UUID?> = AuthUser.AUTH_USER.ID
    override fun field2(): Field<Instant?> = AuthUser.AUTH_USER.CREATED_AT
    override fun field3(): Field<Instant?> = AuthUser.AUTH_USER.UPDATED_AT
    override fun field4(): Field<Instant?> = AuthUser.AUTH_USER.LAST_LOGIN
    override fun field5(): Field<ApplicationName?> = AuthUser.AUTH_USER.APPLICATION_NAME
    override fun field6(): Field<String?> = AuthUser.AUTH_USER.EMAIL
    override fun field7(): Field<Boolean?> = AuthUser.AUTH_USER.EMAIL_CONFIRMED
    override fun field8(): Field<ByteArray?> = AuthUser.AUTH_USER.SALT
    override fun field9(): Field<ByteArray?> = AuthUser.AUTH_USER.PASSWORD_HASH
    override fun component1(): UUID? = id
    override fun component2(): Instant? = createdAt
    override fun component3(): Instant? = updatedAt
    override fun component4(): Instant? = lastLogin
    override fun component5(): ApplicationName? = applicationName
    override fun component6(): String? = email
    override fun component7(): Boolean? = emailConfirmed
    override fun component8(): ByteArray? = salt
    override fun component9(): ByteArray? = passwordHash
    override fun value1(): UUID? = id
    override fun value2(): Instant? = createdAt
    override fun value3(): Instant? = updatedAt
    override fun value4(): Instant? = lastLogin
    override fun value5(): ApplicationName? = applicationName
    override fun value6(): String? = email
    override fun value7(): Boolean? = emailConfirmed
    override fun value8(): ByteArray? = salt
    override fun value9(): ByteArray? = passwordHash

    override fun value1(value: UUID?): AuthUserRecord {
        this.id = value
        return this
    }

    override fun value2(value: Instant?): AuthUserRecord {
        this.createdAt = value
        return this
    }

    override fun value3(value: Instant?): AuthUserRecord {
        this.updatedAt = value
        return this
    }

    override fun value4(value: Instant?): AuthUserRecord {
        this.lastLogin = value
        return this
    }

    override fun value5(value: ApplicationName?): AuthUserRecord {
        this.applicationName = value
        return this
    }

    override fun value6(value: String?): AuthUserRecord {
        this.email = value
        return this
    }

    override fun value7(value: Boolean?): AuthUserRecord {
        this.emailConfirmed = value
        return this
    }

    override fun value8(value: ByteArray?): AuthUserRecord {
        this.salt = value
        return this
    }

    override fun value9(value: ByteArray?): AuthUserRecord {
        this.passwordHash = value
        return this
    }

    override fun values(value1: UUID?, value2: Instant?, value3: Instant?, value4: Instant?, value5: ApplicationName?, value6: String?, value7: Boolean?, value8: ByteArray?, value9: ByteArray?): AuthUserRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        this.value5(value5)
        this.value6(value6)
        this.value7(value7)
        this.value8(value8)
        this.value9(value9)
        return this
    }

    /**
     * Create a detached, initialised AuthUserRecord
     */
    constructor(id: UUID? = null, createdAt: Instant? = null, updatedAt: Instant? = null, lastLogin: Instant? = null, applicationName: ApplicationName? = null, email: String? = null, emailConfirmed: Boolean? = null, salt: ByteArray? = null, passwordHash: ByteArray? = null): this() {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.lastLogin = lastLogin
        this.applicationName = applicationName
        this.email = email
        this.emailConfirmed = emailConfirmed
        this.salt = salt
        this.passwordHash = passwordHash
    }
}
