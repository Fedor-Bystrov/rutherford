package app.rutherford.core.transaction

import app.rutherford.FunctionalTest
import app.rutherford.fixtures.anAuthUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
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
}
