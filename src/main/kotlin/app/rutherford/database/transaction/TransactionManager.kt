package app.rutherford.database.transaction

import org.jooq.Configuration
import org.jooq.DSLContext


object TransactionManager { // TODO correct? thread-safe?
    lateinit var dslContext: DSLContext
}

annotation class TransactionDsl

@TransactionDsl
class Transaction {
    fun apply(block: Transaction.(Configuration) -> Unit) {
        TransactionManager.dslContext.transaction { tx ->
            block.invoke(this, tx)
        }
    }
}

@TransactionDsl
fun transaction(init: Transaction.(Configuration) -> Unit) {
    Transaction().apply(init)
}

