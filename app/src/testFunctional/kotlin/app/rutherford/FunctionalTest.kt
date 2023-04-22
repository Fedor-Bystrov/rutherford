package app.rutherford

import TestHttpClient
import org.eclipse.jetty.http.HttpHeader.ORIGIN
import org.junit.jupiter.api.AfterEach

open class FunctionalTest {
    companion object {
        private val testEnvironment = TestEnvironment()

        @JvmStatic
        protected val LOCALHOST_ORIGIN = mapOf(ORIGIN to "http://localhost")
    }

    protected val authUserRepository = testEnvironment.repository.authUserRepository

    protected val userManager = testEnvironment.managerModule.userManager

    protected val http = TestHttpClient(testEnvironment.applicationPort)

    @AfterEach
    fun afterEach() {
        testEnvironment.reset()
    }
}
