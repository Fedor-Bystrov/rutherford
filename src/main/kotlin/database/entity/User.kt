package database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.UuidGenerator
import org.hibernate.annotations.UuidGenerator.Style.RANDOM
import java.time.Instant
import java.util.*

// TODO builder example
// class FoodOrder private constructor(
//  val bread: String?,
//  val condiments: String?,
//  val meat: String?,
//  val fish: String?) {
//
//    data class Builder(
//      var bread: String? = null,
//      var condiments: String? = null,
//      var meat: String? = null,
//      var fish: String? = null) {
//
//        fun bread(bread: String) = apply { this.bread = bread }
//        fun condiments(condiments: String) = apply { this.condiments = condiments }
//        fun meat(meat: String) = apply { this.meat = meat }
//        fun fish(fish: String) = apply { this.fish = fish }
//        fun build() = FoodOrder(bread, condiments, meat, fish)
//    }
//}

@Entity
@Table(name = "users")
open class User( // TODO use builder
    @field:Id
    @field:GeneratedValue
    @field:UuidGenerator(style = RANDOM)
    var id: UUID? = null,

    @field:Column(name = "created_at", nullable = false)
    var createdAt: Instant,

    @field:Column(name = "updated_at", nullable = false)
    var updatedAt: Instant,

    @field:Column(name = "last_login")
    var lastLogin: Instant? = null,

    @field:Column(name = "application_name", nullable = false, length = 64)
    var applicationName: String, // TODO use enum?

    @field:Column(name = "email", nullable = false, length = 320)
    var email: String,

    @field:Column(name = "email_confirmed", nullable = false)
    var emailConfirmed: Boolean = false,

    @field:Column(name = "password_hash", nullable = false)
    var passwordHash: String, // TODO or base64?
)