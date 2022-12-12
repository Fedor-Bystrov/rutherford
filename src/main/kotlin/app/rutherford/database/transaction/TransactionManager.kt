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

