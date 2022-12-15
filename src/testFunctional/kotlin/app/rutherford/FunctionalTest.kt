package app.rutherford

import org.junit.jupiter.api.AfterEach

open class FunctionalTest {
    private val testEnvironment = TestEnvironment()

    protected val authUserRepository = testEnvironment.repository.authUserRepository

    @AfterEach
    fun afterEach() {
        testEnvironment.resetMocks()
        testEnvironment.resetDatabase()
    }
}