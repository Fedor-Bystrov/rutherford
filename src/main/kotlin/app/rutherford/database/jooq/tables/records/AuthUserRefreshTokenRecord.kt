/*
 * This file is generated by jOOQ.
 */
package app.rutherford.database.jooq.tables.records


import app.rutherford.database.jooq.tables.AuthUserRefreshToken
import app.rutherford.database.jooq.tables.pojos.JAuthUserRefreshToken

import java.time.Instant
import java.util.UUID

import javax.annotation.processing.Generated

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record7
import org.jooq.Row7
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = [
        "https://www.jooq.org",
        "jOOQ version:3.17.6",
        "schema version:public_1"
    ],
    comments = "This class is generated by jOOQ"
)
@Suppress("UNCHECKED_CAST")
open class AuthUserRefreshTokenRecord() : UpdatableRecordImpl<AuthUserRefreshTokenRecord>(AuthUserRefreshToken.AUTH_USER_REFRESH_TOKEN), Record7<UUID?, Instant?, Instant?, Instant?, String?, String?, UUID?> {

    open var id: UUID?
        set(value): Unit = set(0, value)
        get(): UUID? = get(0) as UUID?

    open var createdAt: Instant?
        set(value): Unit = set(1, value)
        get(): Instant? = get(1) as Instant?

    open var updatedAt: Instant?
        set(value): Unit = set(2, value)
        get(): Instant? = get(2) as Instant?

    open var expiration: Instant?
        set(value): Unit = set(3, value)
        get(): Instant? = get(3) as Instant?

    open var state: String?
        set(value): Unit = set(4, value)
        get(): String? = get(4) as String?

    open var tokenHash: String?
        set(value): Unit = set(5, value)
        get(): String? = get(5) as String?

    open var userId: UUID?
        set(value): Unit = set(6, value)
        get(): UUID? = get(6) as UUID?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row7<UUID?, Instant?, Instant?, Instant?, String?, String?, UUID?> = super.fieldsRow() as Row7<UUID?, Instant?, Instant?, Instant?, String?, String?, UUID?>
    override fun valuesRow(): Row7<UUID?, Instant?, Instant?, Instant?, String?, String?, UUID?> = super.valuesRow() as Row7<UUID?, Instant?, Instant?, Instant?, String?, String?, UUID?>
    override fun field1(): Field<UUID?> = AuthUserRefreshToken.AUTH_USER_REFRESH_TOKEN.ID
    override fun field2(): Field<Instant?> = AuthUserRefreshToken.AUTH_USER_REFRESH_TOKEN.CREATED_AT
    override fun field3(): Field<Instant?> = AuthUserRefreshToken.AUTH_USER_REFRESH_TOKEN.UPDATED_AT
    override fun field4(): Field<Instant?> = AuthUserRefreshToken.AUTH_USER_REFRESH_TOKEN.EXPIRATION
    override fun field5(): Field<String?> = AuthUserRefreshToken.AUTH_USER_REFRESH_TOKEN.STATE
    override fun field6(): Field<String?> = AuthUserRefreshToken.AUTH_USER_REFRESH_TOKEN.TOKEN_HASH
    override fun field7(): Field<UUID?> = AuthUserRefreshToken.AUTH_USER_REFRESH_TOKEN.USER_ID
    override fun component1(): UUID? = id
    override fun component2(): Instant? = createdAt
    override fun component3(): Instant? = updatedAt
    override fun component4(): Instant? = expiration
    override fun component5(): String? = state
    override fun component6(): String? = tokenHash
    override fun component7(): UUID? = userId
    override fun value1(): UUID? = id
    override fun value2(): Instant? = createdAt
    override fun value3(): Instant? = updatedAt
    override fun value4(): Instant? = expiration
    override fun value5(): String? = state
    override fun value6(): String? = tokenHash
    override fun value7(): UUID? = userId

    override fun value1(value: UUID?): AuthUserRefreshTokenRecord {
        this.id = value
        return this
    }

    override fun value2(value: Instant?): AuthUserRefreshTokenRecord {
        this.createdAt = value
        return this
    }

    override fun value3(value: Instant?): AuthUserRefreshTokenRecord {
        this.updatedAt = value
        return this
    }

    override fun value4(value: Instant?): AuthUserRefreshTokenRecord {
        this.expiration = value
        return this
    }

    override fun value5(value: String?): AuthUserRefreshTokenRecord {
        this.state = value
        return this
    }

    override fun value6(value: String?): AuthUserRefreshTokenRecord {
        this.tokenHash = value
        return this
    }

    override fun value7(value: UUID?): AuthUserRefreshTokenRecord {
        this.userId = value
        return this
    }

    override fun values(value1: UUID?, value2: Instant?, value3: Instant?, value4: Instant?, value5: String?, value6: String?, value7: UUID?): AuthUserRefreshTokenRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        this.value5(value5)
        this.value6(value6)
        this.value7(value7)
        return this
    }

    /**
     * Create a detached, initialised AuthUserRefreshTokenRecord
     */
    constructor(id: UUID? = null, createdAt: Instant? = null, updatedAt: Instant? = null, expiration: Instant? = null, state: String? = null, tokenHash: String? = null, userId: UUID? = null): this() {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.expiration = expiration
        this.state = state
        this.tokenHash = tokenHash
        this.userId = userId
    }

    /**
     * Create a detached, initialised AuthUserRefreshTokenRecord
     */
    constructor(value: JAuthUserRefreshToken?): this() {
        if (value != null) {
            this.id = value.id
            this.createdAt = value.createdAt
            this.updatedAt = value.updatedAt
            this.expiration = value.expiration
            this.state = value.state
            this.tokenHash = value.tokenHash
            this.userId = value.userId
        }
    }
}
