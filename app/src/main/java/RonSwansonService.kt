import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.json.JSONArray
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Scanner

class RonSwansonService {
    // Created as a practice and to reference while making the main app
    // If I've left this in on submission it's by mistake!
    var quote by mutableStateOf("Quote Service Placeholder")
        private set

    val urlStr = "https://ron-swanson-quotes.herokuapp.com/v2/quotes"

    fun updateQuote() {
        fetchData(urlStr)
    }

    private fun processQuoteJSON(jsonString: String): String {
        val jsonArray = JSONArray(jsonString)
        return jsonArray[0].toString()
    }

    private fun fetchData(urlString: String) {
        val thread = Thread {
            try {
                val url = URL(urlString)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 10_000
                connection.readTimeout = 10_000
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
                    val jsonStr = if (scanner.hasNext()) scanner.next() else ""
                    quote = processQuoteJSON(jsonStr)

                }

            } catch (e: IOException) {
                System.err.println(e.message)
            }
        }
        thread.start()
    }
}