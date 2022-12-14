/*
 * This file is generated by jOOQ.
 */
package app.rutherford.database.jooq.generated.tables


import app.rutherford.database.jooq.converter.InstantConverter
import app.rutherford.database.jooq.generated.Public
import app.rutherford.database.jooq.generated.indexes.USER_REFRESH_TOKENS_TOKEN_HASH_IDX
import app.rutherford.database.jooq.generated.keys.AUTH_USER_REFRESH_TOKEN_PKEY
import app.rutherford.database.jooq.generated.keys.AUTH_USER_REFRESH_TOKEN__AUTH_USER_REFRESH_TOKEN_USER_ID_FKEY
import app.rutherford.database.jooq.generated.tables.records.AuthUserRefreshTokenRecord

import java.time.Instant
import java.util.UUID
import java.util.function.Function

import javax.annotation.processing.Generated

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Index
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row7
import org.jooq.Schema
import org.jooq.SelectField
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = [
        "https://www.jooq.org",
        "jOOQ version:3.17.6",
        "schema version:public_2"
    ],
    comments = "This class is generated by jOOQ"
)
@Suppress("UNCHECKED_CAST")
open class AuthUserRefreshToken(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, AuthUserRefreshTokenRecord>?,
    aliased: Table<AuthUserRefreshTokenRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<AuthUserRefreshTokenRecord>(
    alias,
    Public.PUBLIC,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>public.auth_user_refresh_token</code>
         */
        val AUTH_USER_REFRESH_TOKEN: AuthUserRefreshToken = AuthUserRefreshToken()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<AuthUserRefreshTokenRecord> = AuthUserRefreshTokenRecord::class.java

    /**
     * The column <code>public.auth_user_refresh_token.id</code>.
     */
    val ID: TableField<AuthUserRefreshTokenRecord, UUID?> = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>public.auth_user_refresh_token.created_at</code>.
     */
    val CREATED_AT: TableField<AuthUserRefreshTokenRecord, Instant?> = createField(DSL.name("created_at"), SQLDataType.TIMESTAMP(6).nullable(false).defaultValue(DSL.field("now()", SQLDataType.TIMESTAMP)), this, "", InstantConverter())

    /**
     * The column <code>public.auth_user_refresh_token.updated_at</code>.
     */
    val UPDATED_AT: TableField<AuthUserRefreshTokenRecord, Instant?> = createField(DSL.name("updated_at"), SQLDataType.TIMESTAMP(6).nullable(false).defaultValue(DSL.field("now()", SQLDataType.TIMESTAMP)), this, "", InstantConverter())

    /**
     * The column <code>public.auth_user_refresh_token.expiration</code>.
     */
    val EXPIRATION: TableField<AuthUserRefreshTokenRecord, Instant?> = createField(DSL.name("expiration"), SQLDataType.TIMESTAMP(6), this, "", InstantConverter())

    /**
     * The column <code>public.auth_user_refresh_token.state</code>.
     */
    val STATE: TableField<AuthUserRefreshTokenRecord, String?> = createField(DSL.name("state"), SQLDataType.VARCHAR(32).nullable(false), this, "")

    /**
     * The column <code>public.auth_user_refresh_token.token_hash</code>.
     */
    val TOKEN_HASH: TableField<AuthUserRefreshTokenRecord, String?> = createField(DSL.name("token_hash"), SQLDataType.CLOB.nullable(false), this, "")

    /**
     * The column <code>public.auth_user_refresh_token.user_id</code>.
     */
    val USER_ID: TableField<AuthUserRefreshTokenRecord, UUID?> = createField(DSL.name("user_id"), SQLDataType.UUID, this, "")

    private constructor(alias: Name, aliased: Table<AuthUserRefreshTokenRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<AuthUserRefreshTokenRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>public.auth_user_refresh_token</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.auth_user_refresh_token</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.auth_user_refresh_token</code> table reference
     */
    constructor(): this(DSL.name("auth_user_refresh_token"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, AuthUserRefreshTokenRecord>): this(Internal.createPathAlias(child, key), child, key, AUTH_USER_REFRESH_TOKEN, null)
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getIndexes(): List<Index> = listOf(USER_REFRESH_TOKENS_TOKEN_HASH_IDX)
    override fun getPrimaryKey(): UniqueKey<AuthUserRefreshTokenRecord> = AUTH_USER_REFRESH_TOKEN_PKEY
    override fun getReferences(): List<ForeignKey<AuthUserRefreshTokenRecord, *>> = listOf(AUTH_USER_REFRESH_TOKEN__AUTH_USER_REFRESH_TOKEN_USER_ID_FKEY)

    private lateinit var _authUser: AuthUser

    /**
     * Get the implicit join path to the <code>public.auth_user</code> table.
     */
    fun authUser(): AuthUser {
        if (!this::_authUser.isInitialized)
            _authUser = AuthUser(this, AUTH_USER_REFRESH_TOKEN__AUTH_USER_REFRESH_TOKEN_USER_ID_FKEY)

        return _authUser;
    }

    val authUser: AuthUser
        get(): AuthUser = authUser()
    override fun `as`(alias: String): AuthUserRefreshToken = AuthUserRefreshToken(DSL.name(alias), this)
    override fun `as`(alias: Name): AuthUserRefreshToken = AuthUserRefreshToken(alias, this)
    override fun `as`(alias: Table<*>): AuthUserRefreshToken = AuthUserRefreshToken(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): AuthUserRefreshToken = AuthUserRefreshToken(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): AuthUserRefreshToken = AuthUserRefreshToken(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): AuthUserRefreshToken = AuthUserRefreshToken(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row7<UUID?, Instant?, Instant?, Instant?, String?, String?, UUID?> = super.fieldsRow() as Row7<UUID?, Instant?, Instant?, Instant?, String?, String?, UUID?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (UUID?, Instant?, Instant?, Instant?, String?, String?, UUID?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (UUID?, Instant?, Instant?, Instant?, String?, String?, UUID?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
