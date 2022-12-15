package app.rutherford.database.repository

import app.rutherford.FunctionalTest
import app.rutherford.database.transaction.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AuthUserRepositoryFuncTest : FunctionalTest() {
    @BeforeEach
    fun setUp() {
        transaction {
            authUserRepository.insert(
                it, listOf(

                )
            )
        }
    }

    @Test
    fun `should find correct auth_user by id`() {
        TODO("implement")
    }

    @Test
    fun `should find correct auth_user by collection of ids`() {
        TODO("implement")
    }

    @Test
    fun `should get correct auth_user by id`() {
        TODO("implement")
    }
}