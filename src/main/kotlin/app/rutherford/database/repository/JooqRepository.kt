package app.rutherford.database.repository

import app.rutherford.database.entity.Entity
import app.rutherford.database.jooq.generated.tables.references.AUTH_USER
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.SelectWhereStep
import org.jooq.Table
import java.util.*

abstract class JooqRepository<R : Record, E: Entity>(
    private val defaultContext: DSLContext,
    private val fetchTable: Table<R>,
) {
    protected fun findById(id: UUID): E? {
        return findOne(
            defaultContext.dsl().selectFrom(fetchTable),
            byId(id),
            this::fromRecord
        )
    }

    protected fun findByIdWhere(id: UUID, condition: Condition): E? {
        return findOne(
            defaultContext.dsl().selectFrom(fetchTable),
            byId(id).and(condition),
            this::fromRecord
        )
    }

    private fun findOne(
        query: SelectWhereStep<R>,
        condition: Condition,
        mapper: (R) -> E
    ): E? {
        val entities = query
            .where(condition)
            .fetch()
            .map(mapper)

        if (entities.size > 1) {
            throw RuntimeException(
                "Expected 1 record but got ${entities.size}. " +
                        "Check your condition $condition"
            )
        }

        return entities.getOrNull(0)
    }

    private fun byId(id: UUID): Condition {
        return AUTH_USER.ID.eq(id) // TODO ??? fix
    }

    protected abstract fun fromRecord(record: R): E
    protected abstract fun toRecord(entity: E): R
}