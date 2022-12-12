package app.rutherford.database.transaction

import org.jooq.Configuration


object TransactionManager {
//    private val dslContext: DSLContext

    init {

    }


}

annotation class TransactionMarker // TODO rename TransactionDsl

@TransactionMarker
class Transaction {
    fun apply(block: Transaction.(Configuration?) -> Unit) {
        block.invoke(this, null)
    }
}

@TransactionMarker
fun transaction(init: Transaction.(Configuration?) -> Unit) {
    Transaction().apply(init)
}

