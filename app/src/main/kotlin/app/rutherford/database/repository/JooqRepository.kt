package app.rutherford.database.repository

import app.rutherford.core.entity.Entity
import app.rutherford.core.exception.EntityNotFoundException
import org.jooq.Condition
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.DeleteUsingStep
import org.jooq.SelectWhereStep
import org.jooq.Table
import org.jooq.TableField
import org.jooq.UpdatableRecord
import java.util.*

abstract class JooqRepository<R : UpdatableRecord<*>, E : Entity>(
    private val defaultContext: DSLContext,
    private val table: Table<R>,
    private val idField: TableField<R, UUID?>
) {
    protected fun findById(conf: Configuration?, id: UUID): E? {
        return findOne(
            (conf ?: defaultContext.configuration()).dsl().selectFrom(table),
            byId(id),
            this::fromRecord
        )
    }

    protected fun findByIds(conf: Configuration?, ids: Collection<UUID>): Collection<E> {
        return if (ids.isEmpty())
            emptyList()
        else if (ids.size == 1) {
            val entity = findById(conf, ids.first())
            if (entity == null) listOf() else listOf(entity)
        } else
            findAll(
                (conf ?: defaultContext.configuration()).dsl().selectFrom(table),
                byIds(ids),
                this::fromRecord
            )
    }

    protected fun getById(conf: Configuration?, id: UUID): E =
        findById(conf, id) ?: throw EntityNotFoundException(table, id);

    protected fun findByIdWhere(conf: Configuration?, id: UUID, condition: Condition): E? {
        return findOne(
            (conf ?: defaultContext.configuration()).dsl().selectFrom(table),
            byId(id).and(condition),
            this::fromRecord
        )
    }

    protected fun findByIdsWhere(conf: Configuration?, id: Collection<UUID>, condition: Condition): Collection<E> {
        return findAll(
            (conf ?: defaultContext.configuration()).dsl().selectFrom(table),
            byIds(id).and(condition),
            this::fromRecord
        )
    }

    protected fun insertOne(conf: Configuration, entity: E): E {
        val record = conf.dsl().newRecord(table, toRecord(entity))
        record.insert()
        return fromRecord(record)
    }

    protected fun insertBatch(conf: Configuration, entities: Collection<E>) {
        val records = entities.map { toRecord(it) }
        records.forEach { it.changed(true) } // https://stackoverflow.com/a/45279364
        conf.dsl().batchInsert(records).execute()
    }

    protected fun updateOne(conf: Configuration, entity: E): E {
        val record = conf.dsl().newRecord(table, toRecord(entity))
        record.update()
        return fromRecord(record)
    }

    protected fun deleteById(conf: Configuration, id: UUID) {
        deleteOne(
            conf.dsl().deleteFrom(table),
            byId(id)
        )
    }

    protected fun deleteBatch(conf: Configuration, entities: Collection<E>) {
        val records = entities.map { toRecord(it) }
        conf.dsl().batchDelete(records).execute()
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

    private fun deleteOne(delete: DeleteUsingStep<R>, condition: Condition) = delete.where(condition).execute()

    private fun findAll(
        query: SelectWhereStep<R>,
        condition: Condition,
        mapper: (R) -> E
    ): Collection<E> {
        return query
            .where(condition)
            .fetch()
            .map(mapper)
    }

    private fun byId(id: UUID): Condition = idField.eq(id)

    private fun byIds(ids: Collection<UUID>): Condition = idField.`in`(ids)

    protected abstract fun fromRecord(record: R): E
    protected abstract fun toRecord(entity: E): R
}