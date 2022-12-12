package app.rutherford.database.transaction

import org.jooq.Configuration
import org.jooq.DSLContext


object TransactionManager { // TODO correct? thread-safe?
    // TODO make TransactionManager a class
    // TODO put dslContext into companion object
    lateinit var dslContext: DSLContext

    // TODO make dslContext private, add newTransaction method and call it to get tx config
}

@DslMarker
annotation class TransactionMarker

@TransactionMarker
class Transaction {
    fun apply(block: Transaction.(Configuration) -> Unit) {
        TransactionManager.dslContext.transaction { tx ->
            block.invoke(this, tx)
        }
    }
}

@TransactionMarker
fun transaction(init: Transaction.(Configuration) -> Unit) {
    Transaction().apply(init)
}

