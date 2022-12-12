package app.rutherford.database.repository

import app.rutherford.database.entity.AuthUser
import app.rutherford.database.jooq.generated.tables.records.AuthUserRecord
import app.rutherford.database.jooq.generated.tables.references.AUTH_USER
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.SelectWhereStep
import java.util.*

abstract class JooqRepository(
    private val defaultContext: DSLContext
) {
    protected fun findById(id: UUID): AuthUser? {
        return findOne(
            defaultContext.dsl().selectFrom(AUTH_USER),
            byId(id),
            this::fromRecord
        )
    }

    protected fun findByIdWhere(id: UUID, condition: Condition): AuthUser? {
        return findOne(
            defaultContext.dsl().selectFrom(AUTH_USER),
            byId(id).and(condition),
            this::fromRecord
        )
    }

    private fun findOne(
        query: SelectWhereStep<AuthUserRecord>,
        condition: Condition,
        mapper: (AuthUserRecord) -> AuthUser
    ): AuthUser? {
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
        return AUTH_USER.ID.eq(id)
    }

    protected abstract fun fromRecord(record: AuthUserRecord): AuthUser
    protected abstract fun toRecord(entity: AuthUser): AuthUserRecord
}