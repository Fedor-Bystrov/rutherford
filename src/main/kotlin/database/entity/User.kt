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

@Entity
@Table(name = "users")
class User(
    @field:Id
    @field:GeneratedValue
    @field:UuidGenerator(style = RANDOM)
    val id: UUID?,

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