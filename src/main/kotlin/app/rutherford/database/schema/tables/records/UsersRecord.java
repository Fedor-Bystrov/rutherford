/*
 * This file is generated by jOOQ.
 */
package app.rutherford.database.schema.tables.records;


import app.rutherford.database.schema.tables.Users;

import java.time.Instant;
import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6",
        "schema version:public_1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UsersRecord extends UpdatableRecordImpl<UsersRecord> implements Record8<UUID, Instant, Instant, Instant, String, String, Boolean, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.users.id</code>.
     */
    public UsersRecord setId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.users.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.users.created_at</code>.
     */
    public UsersRecord setCreatedAt(Instant value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.users.created_at</code>.
     */
    public Instant getCreatedAt() {
        return (Instant) get(1);
    }

    /**
     * Setter for <code>public.users.updated_at</code>.
     */
    public UsersRecord setUpdatedAt(Instant value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.users.updated_at</code>.
     */
    public Instant getUpdatedAt() {
        return (Instant) get(2);
    }

    /**
     * Setter for <code>public.users.last_login</code>.
     */
    public UsersRecord setLastLogin(Instant value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.users.last_login</code>.
     */
    public Instant getLastLogin() {
        return (Instant) get(3);
    }

    /**
     * Setter for <code>public.users.application_name</code>.
     */
    public UsersRecord setApplicationName(String value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>public.users.application_name</code>.
     */
    public String getApplicationName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.users.email</code>.
     */
    public UsersRecord setEmail(String value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>public.users.email</code>.
     */
    public String getEmail() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.users.email_confirmed</code>.
     */
    public UsersRecord setEmailConfirmed(Boolean value) {
        set(6, value);
        return this;
    }

    /**
     * Getter for <code>public.users.email_confirmed</code>.
     */
    public Boolean getEmailConfirmed() {
        return (Boolean) get(6);
    }

    /**
     * Setter for <code>public.users.password_hash</code>.
     */
    public UsersRecord setPasswordHash(String value) {
        set(7, value);
        return this;
    }

    /**
     * Getter for <code>public.users.password_hash</code>.
     */
    public String getPasswordHash() {
        return (String) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row8<UUID, Instant, Instant, Instant, String, String, Boolean, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<UUID, Instant, Instant, Instant, String, String, Boolean, String> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Users.USERS.ID;
    }

    @Override
    public Field<Instant> field2() {
        return Users.USERS.CREATED_AT;
    }

    @Override
    public Field<Instant> field3() {
        return Users.USERS.UPDATED_AT;
    }

    @Override
    public Field<Instant> field4() {
        return Users.USERS.LAST_LOGIN;
    }

    @Override
    public Field<String> field5() {
        return Users.USERS.APPLICATION_NAME;
    }

    @Override
    public Field<String> field6() {
        return Users.USERS.EMAIL;
    }

    @Override
    public Field<Boolean> field7() {
        return Users.USERS.EMAIL_CONFIRMED;
    }

    @Override
    public Field<String> field8() {
        return Users.USERS.PASSWORD_HASH;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public Instant component2() {
        return getCreatedAt();
    }

    @Override
    public Instant component3() {
        return getUpdatedAt();
    }

    @Override
    public Instant component4() {
        return getLastLogin();
    }

    @Override
    public String component5() {
        return getApplicationName();
    }

    @Override
    public String component6() {
        return getEmail();
    }

    @Override
    public Boolean component7() {
        return getEmailConfirmed();
    }

    @Override
    public String component8() {
        return getPasswordHash();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public Instant value2() {
        return getCreatedAt();
    }

    @Override
    public Instant value3() {
        return getUpdatedAt();
    }

    @Override
    public Instant value4() {
        return getLastLogin();
    }

    @Override
    public String value5() {
        return getApplicationName();
    }

    @Override
    public String value6() {
        return getEmail();
    }

    @Override
    public Boolean value7() {
        return getEmailConfirmed();
    }

    @Override
    public String value8() {
        return getPasswordHash();
    }

    @Override
    public UsersRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public UsersRecord value2(Instant value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public UsersRecord value3(Instant value) {
        setUpdatedAt(value);
        return this;
    }

    @Override
    public UsersRecord value4(Instant value) {
        setLastLogin(value);
        return this;
    }

    @Override
    public UsersRecord value5(String value) {
        setApplicationName(value);
        return this;
    }

    @Override
    public UsersRecord value6(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public UsersRecord value7(Boolean value) {
        setEmailConfirmed(value);
        return this;
    }

    @Override
    public UsersRecord value8(String value) {
        setPasswordHash(value);
        return this;
    }

    @Override
    public UsersRecord values(UUID value1, Instant value2, Instant value3, Instant value4, String value5, String value6, Boolean value7, String value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UsersRecord
     */
    public UsersRecord() {
        super(Users.USERS);
    }

    /**
     * Create a detached, initialised UsersRecord
     */
    public UsersRecord(UUID id, Instant createdAt, Instant updatedAt, Instant lastLogin, String applicationName, String email, Boolean emailConfirmed, String passwordHash) {
        super(Users.USERS);

        setId(id);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        setLastLogin(lastLogin);
        setApplicationName(applicationName);
        setEmail(email);
        setEmailConfirmed(emailConfirmed);
        setPasswordHash(passwordHash);
    }

    /**
     * Create a detached, initialised UsersRecord
     */
    public UsersRecord(app.rutherford.database.schema.tables.pojos.Users value) {
        super(Users.USERS);

        if (value != null) {
            setId(value.getId());
            setCreatedAt(value.getCreatedAt());
            setUpdatedAt(value.getUpdatedAt());
            setLastLogin(value.getLastLogin());
            setApplicationName(value.getApplicationName());
            setEmail(value.getEmail());
            setEmailConfirmed(value.getEmailConfirmed());
            setPasswordHash(value.getPasswordHash());
        }
    }
}
