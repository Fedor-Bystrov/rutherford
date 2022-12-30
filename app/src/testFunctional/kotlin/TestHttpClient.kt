import org.json.JSONObject
import java.net.URI
import java.net.http.HttpClient
import java.time.Duration

const val HOST_URI = "http://localhost"

class TestHttpClient(applicationPort: Int) {
    private val baseURI = URI.create("$HOST_URI:$applicationPort")

    private val http = HttpClient.newBuilder()
        .connectTimeout(Duration.ofMinutes(1))
        .build()

    fun post(uri: String, body: JSONObject? = null): JSONObject? {
        return null // TODO
    }
}
