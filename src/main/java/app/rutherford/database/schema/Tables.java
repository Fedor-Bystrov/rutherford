/*
 * This file is generated by jOOQ.
 */
package app.rutherford.database.schema;


import app.rutherford.database.schema.tables.AuthUser;
import app.rutherford.database.schema.tables.AuthUserRefreshToken;

import javax.annotation.processing.Generated;


/**
 * Convenience access to all tables in public.
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
public class Tables {

    /**
     * The table <code>public.auth_user</code>.
     */
    public static final AuthUser AUTH_USER = AuthUser.AUTH_USER;

    /**
     * The table <code>public.auth_user_refresh_token</code>.
     */
    public static final AuthUserRefreshToken AUTH_USER_REFRESH_TOKEN = AuthUserRefreshToken.AUTH_USER_REFRESH_TOKEN;
}
