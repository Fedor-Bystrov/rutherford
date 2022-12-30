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

    fun post(uri: String, body: JSONObject? = null): HttpResponse<String> {
        val request = newRequest()
            .uri(URI.create(baseURI + uri))
            .POST(
                if (body == null) BodyPublishers.noBody()
                else BodyPublishers.ofString(body.toString(1))
            )
            .build()

        return httpClient.send(request, BodyHandlers.ofString())
    }

    private fun newRequest(): HttpRequest.Builder = HttpRequest
        .newBuilder()
        .timeout(Duration.ofMinutes(1))
}
