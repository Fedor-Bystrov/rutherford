package app.rutherford.core.transaction

import org.jooq.Configuration
import org.jooq.DSLContext

object TransactionManager {
    lateinit var dslContext: DSLContext
        private set

    fun of(dslContext: DSLContext): TransactionManager {
        TransactionManager.dslContext = dslContext
        return this
    }
}

@DslMarker
annotation class TransactionMarker

// TODO
//  1. Make two separate transaction creators: transaction and transaction_with_result
//  2. improve add repositories available inside transaction

@TransactionMarker
class Transaction(val tx: Configuration)

@TransactionMarker
fun <E> transaction(init: Transaction.() -> E?): E? {
    var e: E? = null
    TransactionManager.dslContext.transaction { tx ->
        e = Transaction(tx).init()
    }
    return e
}
