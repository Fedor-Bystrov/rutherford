package app.rutherford.core

import app.rutherford.core.exception.UnknownOriginException
import java.net.URI

enum class ApplicationName(val allowedHost: URI) {
    /**
     * Only for tests
     */
    TEST1(URI.create("http://localhost/")), // TODO delete
    TEST2(URI.create("http://localhost2/"));  // TODO delete

    companion object {
        fun getForOrigin(originURI: URI): ApplicationName = ApplicationName
            .values()
            .find { it.allowedHost == formatUri(originURI) }
            ?: throw UnknownOriginException(originURI.toString())

        private fun formatUri(originURI: URI): URI {
            var stringUri = originURI.toString().trim()

            if (!stringUri.endsWith('/')) {
                stringUri += '/'
            }

            return URI.create(stringUri)
        }
    }
}
