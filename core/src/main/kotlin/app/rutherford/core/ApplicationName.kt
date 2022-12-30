package app.rutherford.core

import app.rutherford.core.exception.UnknownOriginException
import java.net.URI

enum class ApplicationName(private val allowedHost: URI) {
    /**
     * Only for tests
     */
    TEST1(URI.create("http://localhost:7070/")), // TODO delete
    TEST2(URI.create("http://localhost2:7070/"));  // TODO delete

    companion object {
        fun getForOrigin(originURI: URI): ApplicationName = ApplicationName
            .values()
            .find { it.allowedHost == originURI }
            ?: throw UnknownOriginException(originURI.toString())
    }
}
