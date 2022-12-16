package app.rutherford.database.entity

import java.time.Instant

abstract class Entity {
    abstract class EntityBuilder
    protected abstract fun copy(): EntityBuilder
    abstract fun withUpdateAt(updatedAt: Instant): Entity
}