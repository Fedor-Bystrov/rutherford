package app.rutherford.database.entity

abstract class Entity {
    abstract class EntityBuilder

    protected abstract fun copy(): EntityBuilder
}