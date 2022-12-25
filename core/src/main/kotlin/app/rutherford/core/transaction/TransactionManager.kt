package app.rutherford.core.transaction

import org.jooq.Configuration
import org.jooq.DSLContext

object TransactionManager {
    lateinit var dslContext: DSLContext
        private set

    fun create(dslContext: DSLContext): TransactionManager {
        TransactionManager.dslContext = dslContext
        return this
    }
}

class TransactionContext(val configuration: Configuration)

@DslMarker
annotation class TransactionMarker

@TransactionMarker
fun <E> transaction(init: TransactionContext.() -> E?): E? {
    var e: E? = null
    TransactionManager.dslContext.transaction { tx ->
        e = TransactionContext(tx).init()
    }
    return e
}
