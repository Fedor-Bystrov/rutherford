package app.rutherford.core.transaction

import app.rutherford.FunctionalTest
import app.rutherford.core.abstract.entity.Entity.Id
import app.rutherford.fixtures.anAuthUser
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.util.UUID.randomUUID
import kotlin.test.assertNotNull

class TransactionManagerTest : FunctionalTest() {
    @Test
    fun `should commit transaction`() {
        val user = anAuthUser().build()

        // when
        val result = transaction {
            authUserRepository.insert(this.tx, user)
        }

        // then
        assertNotNull(result)
        assertThat(result).isEqualTo(user)

        // and
        val created = authUserRepository.get(id = user.id())
        assertThat(created).isEqualTo(user)
    }

    @Test
    fun `should commit transactionResult`() {
        val user = anAuthUser().build()

        // when
        val result = transactionResult {
            authUserRepository.insert(this.tx, user)
        }

        // then
        assertThat(result).isEqualTo(user)

        // and
        val created = authUserRepository.get(id = user.id())
        assertThat(created).isEqualTo(user)
    }

    @Test
    fun `should rollback transaction on runtime exception`() {
        // given
        val user = anAuthUser().build()

        // when
        try {
            transaction {
                authUserRepository.insert(this.tx, user)
                throw RuntimeException("should revert transaction")
            }
        } catch (_: RuntimeException) {
        }

        // then
        val created = authUserRepository.find(id = user.id())
        assertThat(created).isNull()
    }

    @Test
    fun `should rollback transactionResult on runtime exception`() {
        // given
        val user = anAuthUser().build()

        // when
        try {
            transactionResult {
                authUserRepository.insert(this.tx, user)
                throw RuntimeException("should revert transaction")
            }
        } catch (_: RuntimeException) {
        }

        // then
        val created = authUserRepository.find(id = user.id())
        assertThat(created).isNull()
    }

    @Test
    fun `should isolate changes in the transaction`() {
        val user = anAuthUser().build()

        // when
        transaction {
            authUserRepository.insert(this.tx, user)

            // then
            val findOutside = authUserRepository.find(id = user.id())
            assertThat(findOutside).isNull()

            // and
            val created = authUserRepository.find(this.tx, id = user.id())
            assertNotNull(created)
            assertThat(user).isEqualTo(user)
        }

        // and
        val created = authUserRepository.get(id = user.id())
        assertThat(created).isEqualTo(user)
    }

    @Test
    fun `should isolate changes in the transactionResult`() {
        val user = anAuthUser().build()

        // when
        transactionResult {
            authUserRepository.insert(this.tx, user)

            // then
            val findOutside = authUserRepository.find(id = user.id())
            assertThat(findOutside).isNull()

            // and
            val created = authUserRepository.find(this.tx, id = user.id())
            assertNotNull(created)
            assertThat(user).isEqualTo(user)
        }

        // and
        val created = authUserRepository.get(id = user.id())
        assertThat(created).isEqualTo(user)
    }

    @Test
    fun `transactionResult should throw if return value is null`() {
        // when
        assertThatThrownBy {
            transactionResult {
                authUserRepository.find(id = Id(randomUUID()))
            }.run {}
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Transaction returned null")
    }
}
