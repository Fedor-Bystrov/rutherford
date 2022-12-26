package app.rutherford.core

import app.rutherford.core.exception.UnknownOriginException
import java.net.URL

enum class ApplicationName(private val allowedHost: URL) {
    /**
     * Only for tests
     */
    TEST1(URL("http://localhost:7070/")), // TODO delete
    TEST2(URL("http://localhost2:7070/"));  // TODO delete

    companion object {
        fun getForOrigin(originURL: URL): ApplicationName = ApplicationName
            .values()
            .find { it.allowedHost == originURL }
            ?: throw UnknownOriginException(originURL.toString())
    }
}
