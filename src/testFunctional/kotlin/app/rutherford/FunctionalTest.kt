package app.rutherford

import app.rutherford.testing.TestEnvironment
import org.junit.jupiter.api.AfterEach

open class FunctionalTest {
    private val testEnvironment = TestEnvironment()

    @AfterEach
    fun afterEach() {
        testEnvironment.resetMocks()
    }
}