package app.rutherford.database.transaction

import org.jooq.Configuration
import org.jooq.DSLContext


object TransactionManager {
    lateinit var dslContext: DSLContext
        private set

    fun of(dslContext: DSLContext): TransactionManager {
        this.dslContext = dslContext
        return this
    }
}

@DslMarker
annotation class TransactionMarker

@TransactionMarker
class Transaction {
    fun <E> apply(block: Transaction.(Configuration) -> E?): E? {
        var e: E? = null
        TransactionManager.dslContext.transaction { tx ->
            e = block.invoke(this, tx)
        }
        return e
    }
}

@TransactionMarker
fun <E> transaction(init: Transaction.(Configuration) -> E?): E? {
    return Transaction().apply(init)
}

