import org.eclipse.jetty.http.HttpHeader
import org.json.JSONObject
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration

class TestHttpClient(applicationPort: Int) {
    private val baseURI = "http://localhost:$applicationPort"

    private val httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofMinutes(1))
        .build()

    fun post(
        uri: String,
        body: JSONObject? = null,
        headers: Map<HttpHeader, String>? = null
    ): HttpResponse<String> {
        val request = newRequest(headers)
            .uri(URI.create(baseURI + uri))
            .POST(
                if (body == null) BodyPublishers.noBody()
                else BodyPublishers.ofString(body.toString(1))
            )

        return httpClient.send(request.build(), BodyHandlers.ofString())
    }

    private fun newRequest(headers: Map<HttpHeader, String>? = null): HttpRequest.Builder {
        val requestBuilder = HttpRequest
            .newBuilder()
            .timeout(Duration.ofMinutes(1))
        headers?.forEach { requestBuilder.setHeader(it.key.asString(), it.value) }
        return requestBuilder
    }
}
