package app.rutherford.database.entity

import java.util.*

abstract class Entity {
    data class Id<E : Entity>(val value: UUID)

    enum class State { CREATED }
    abstract class EntityBuilder

    abstract fun id(): Id<*>

    protected abstract fun copy(): EntityBuilder
}