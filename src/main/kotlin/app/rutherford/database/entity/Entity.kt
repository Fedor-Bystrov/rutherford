package app.rutherford.database.entity

abstract class Entity {
    enum class State { CREATED }
    abstract class EntityBuilder

    protected abstract fun copy(): EntityBuilder
}