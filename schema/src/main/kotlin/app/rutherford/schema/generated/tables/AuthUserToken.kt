/*
 * This file is generated by jOOQ.
 */
package app.rutherford.schema.generated.tables


import app.rutherford.core.abstract.entity.Entity.State
import app.rutherford.schema.converter.InstantConverter
import app.rutherford.schema.generated.Public
import app.rutherford.schema.generated.indexes.AUTH_USER_TOKEN_TOKEN_HASH_IDX
import app.rutherford.schema.generated.keys.AUTH_USER_TOKEN_PKEY
import app.rutherford.schema.generated.keys.AUTH_USER_TOKEN__AUTH_USER_TOKEN_USER_ID_FKEY
import app.rutherford.schema.generated.tables.records.AuthUserTokenRecord

import java.time.Instant
import java.util.UUID
import java.util.function.Function

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Index
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row8
import org.jooq.Schema
import org.jooq.SelectField
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.EnumConverter
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class AuthUserToken(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, AuthUserTokenRecord>?,
    aliased: Table<AuthUserTokenRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<AuthUserTokenRecord>(
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
         * The reference instance of <code>public.auth_user_token</code>
         */
        val AUTH_USER_TOKEN: AuthUserToken = AuthUserToken()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<AuthUserTokenRecord> = AuthUserTokenRecord::class.java

    /**
     * The column <code>public.auth_user_token.id</code>.
     */
    val ID: TableField<AuthUserTokenRecord, UUID?> = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>public.auth_user_token.created_at</code>.
     */
    val CREATED_AT: TableField<AuthUserTokenRecord, Instant?> = createField(DSL.name("created_at"), SQLDataType.TIMESTAMP(6).nullable(false), this, "", InstantConverter())

    /**
     * The column <code>public.auth_user_token.updated_at</code>.
     */
    val UPDATED_AT: TableField<AuthUserTokenRecord, Instant?> = createField(DSL.name("updated_at"), SQLDataType.TIMESTAMP(6).nullable(false), this, "", InstantConverter())

    /**
     * The column <code>public.auth_user_token.expiration</code>.
     */
    val EXPIRATION: TableField<AuthUserTokenRecord, Instant?> = createField(DSL.name("expiration"), SQLDataType.TIMESTAMP(6), this, "", InstantConverter())

    /**
     * The column <code>public.auth_user_token.state</code>.
     */
    val STATE: TableField<AuthUserTokenRecord, State?> = createField(DSL.name("state"), SQLDataType.VARCHAR(32).nullable(false), this, "", EnumConverter<String, State>(String::class.java, State::class.java))

    /**
     * The column <code>public.auth_user_token.salt</code>.
     */
    val SALT: TableField<AuthUserTokenRecord, ByteArray?> = createField(DSL.name("salt"), SQLDataType.BLOB.nullable(false), this, "")

    /**
     * The column <code>public.auth_user_token.token_hash</code>.
     */
    val TOKEN_HASH: TableField<AuthUserTokenRecord, ByteArray?> = createField(DSL.name("token_hash"), SQLDataType.BLOB.nullable(false), this, "")

    /**
     * The column <code>public.auth_user_token.user_id</code>.
     */
    val USER_ID: TableField<AuthUserTokenRecord, UUID?> = createField(DSL.name("user_id"), SQLDataType.UUID.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<AuthUserTokenRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<AuthUserTokenRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>public.auth_user_token</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.auth_user_token</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.auth_user_token</code> table reference
     */
    constructor(): this(DSL.name("auth_user_token"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, AuthUserTokenRecord>): this(Internal.createPathAlias(child, key), child, key, AUTH_USER_TOKEN, null)
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getIndexes(): List<Index> = listOf(AUTH_USER_TOKEN_TOKEN_HASH_IDX)
    override fun getPrimaryKey(): UniqueKey<AuthUserTokenRecord> = AUTH_USER_TOKEN_PKEY
    override fun getReferences(): List<ForeignKey<AuthUserTokenRecord, *>> = listOf(AUTH_USER_TOKEN__AUTH_USER_TOKEN_USER_ID_FKEY)

    private lateinit var _authUser: AuthUser

    /**
     * Get the implicit join path to the <code>public.auth_user</code> table.
     */
    fun authUser(): AuthUser {
        if (!this::_authUser.isInitialized)
            _authUser = AuthUser(this, AUTH_USER_TOKEN__AUTH_USER_TOKEN_USER_ID_FKEY)

        return _authUser;
    }

    val authUser: AuthUser
        get(): AuthUser = authUser()
    override fun `as`(alias: String): AuthUserToken = AuthUserToken(DSL.name(alias), this)
    override fun `as`(alias: Name): AuthUserToken = AuthUserToken(alias, this)
    override fun `as`(alias: Table<*>): AuthUserToken = AuthUserToken(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): AuthUserToken = AuthUserToken(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): AuthUserToken = AuthUserToken(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): AuthUserToken = AuthUserToken(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row8<UUID?, Instant?, Instant?, Instant?, State?, ByteArray?, ByteArray?, UUID?> = super.fieldsRow() as Row8<UUID?, Instant?, Instant?, Instant?, State?, ByteArray?, ByteArray?, UUID?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (UUID?, Instant?, Instant?, Instant?, State?, ByteArray?, ByteArray?, UUID?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (UUID?, Instant?, Instant?, Instant?, State?, ByteArray?, ByteArray?, UUID?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
