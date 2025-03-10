import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Scanner

class GridRefService {
    val maxResults = 10
    var gridRef by mutableStateOf("")

    fun getGridRef(keyWordsText: String) {
        val url = constructRequestUrl(keyWordsText)
        fetchData(url)

    }

    private fun fetchData(urlString: String) {
        val url = URL(urlString)

        val thread = Thread {
            try {
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 10_000
                connection.readTimeout = 10_000
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
                    val jsonString = if (scanner.hasNext()) scanner.next() else "Scanner empty"

//                    gridRef = processJSON(jsonString)
                }


            } catch (e: IOException) {
                gridRef = "Exception"
                println(e)
            }
        }
        thread.start()

    }

    private fun constructRequestUrl(keyWords: String): String {
        val baseUrl = "https://api.geodojo.net/locate/find"
        val locationArgs = keyWords.replace(Regex("\\s+"), "+")
        val maxResultsArg = "max=$maxResults"
        val typeArg = "type=grid"
        val requestUrl = "$baseUrl?q=$locationArgs&$maxResultsArg&$typeArg"
        return requestUrl
    }

    private fun processJSON(jsonString: String): List<Location> {
        val resultJsonArray = JSONObject(jsonString).getJSONArray("result")
        val locations = mutableListOf<Location>()

        for (i in 0 until resultJsonArray.length()) {
            val locationObj = resultJsonArray.getJSONObject(i)
            locations.add(Location().apply {
                address = locationObj.get("location").toString()
                gridRef = locationObj.get("grid").toString()
            })
        }

        return locations
    }


}