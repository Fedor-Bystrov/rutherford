/*
 * This file is generated by jOOQ.
 */
package app.rutherford.database.schema;


import app.rutherford.database.schema.tables.UserRefreshTokens;

import javax.annotation.processing.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables in public.
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
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index USER_REFRESH_TOKENS_TOKEN_HASH_IDX = Internal.createIndex(DSL.name("user_refresh_tokens_token_hash_idx"), UserRefreshTokens.USER_REFRESH_TOKENS, new OrderField[] { UserRefreshTokens.USER_REFRESH_TOKENS.TOKEN_HASH }, false);
}
