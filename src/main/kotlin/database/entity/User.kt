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
class User( // TODO use builder
    @field:Id
    @field:GeneratedValue
    @field:UuidGenerator(style = RANDOM)
    val id: UUID?, // TODO can't use val

    @field:Column(name = "created_at", nullable = false)
    val createdAt: Instant,

    @field:Column(name = "updated_at", nullable = false)
    val updatedAt: Instant,

    @field:Column(name = "last_login")
    val lastLogin: Instant?,

    @field:Column(name = "application_name", nullable = false, length = 64)
    val applicationName: String, // TODO use enum?

    @field:Column(name = "email", nullable = false, length = 320)
    val email: String,

    @field:Column(name = "email_confirmed", nullable = false)
    val emailConfirmed: Boolean,

    @field:Column(name = "password_hash", nullable = false)
    val passwordHash: String, // TODO or base64?
)