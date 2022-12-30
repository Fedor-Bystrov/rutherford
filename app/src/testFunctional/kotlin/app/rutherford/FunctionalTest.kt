package app.rutherford

import TestHttpClient
import org.junit.jupiter.api.AfterEach

open class FunctionalTest {
    companion object {
        private val testEnvironment = TestEnvironment()
    }

    protected val authUserRepository = testEnvironment.repository.authUserRepository
    protected val authUserTokenRepository = testEnvironment.repository.authUserTokenRepository

    protected val userManager = testEnvironment.managerModule.userManager

    protected val http = TestHttpClient(testEnvironment.applicationPort)

    @AfterEach
    fun afterEach() {
        testEnvironment.reset()
    }
}
