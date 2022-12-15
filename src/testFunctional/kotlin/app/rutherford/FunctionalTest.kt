package app.rutherford

import org.junit.jupiter.api.AfterEach

open class FunctionalTest {
    private val testEnvironment = TestEnvironment()

    @AfterEach
    fun afterEach() {
        testEnvironment.resetMocks()
        resetDatabase()
    }

    private fun resetDatabase() {
        // TODO
    }
}