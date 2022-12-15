package app.rutherford.database.repository

import app.rutherford.database.entity.Entity
import app.rutherford.database.exception.EntityNotFoundException
import org.jooq.Condition
import org.jooq.Configuration
import org.jooq.DSLContext
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
    abstract fun get(id: UUID): E
    abstract fun find(id: UUID): E?
    abstract fun find(ids: Collection<UUID>): Collection<E>
    abstract fun insert(conf: Configuration, entity: E): E
    abstract fun insert(conf: Configuration, entities: Collection<E>)
    abstract fun update(conf: Configuration, entity: E): E
    abstract fun delete(conf: Configuration, entities: Collection<E>)

    protected fun findById(id: UUID): E? {
        return findOne(
            defaultContext.dsl().selectFrom(table),
            byId(id),
            this::fromRecord
        )
    }

    protected fun findByIds(ids: Collection<UUID>): Collection<E> {
        return if (ids.isEmpty())
            emptyList()
        else if (ids.size == 1) {
            val entity = findById(ids.first())
            if (entity == null) listOf() else listOf(entity)
        } else
            findAll(
                defaultContext.dsl().selectFrom(table),
                byIds(ids),
                this::fromRecord
            )
    }

    protected fun getById(id: UUID): E = findById(id) ?: throw EntityNotFoundException(table, id);

    protected fun findByIdWhere(id: UUID, condition: Condition): E? {
        return findOne(
            defaultContext.dsl().selectFrom(table),
            byId(id).and(condition),
            this::fromRecord
        )
    }

    protected fun findByIdsWhere(id: Collection<UUID>, condition: Condition): Collection<E> {
        return findAll(
            defaultContext.dsl().selectFrom(table),
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