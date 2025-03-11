import android.util.Log
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Scanner

class GridRefService {
    val maxResults = 10

    fun getGridRef(keyWordsText: String) {
        val url = constructRequestUrl(keyWordsText)
        fetchData(url)
    }

    fun getListOfLocations(keyWords: String): List<Location> {
        val validatedKeyWords = ensureValidPostcode(keyWords)
        val url = constructRequestUrl(validatedKeyWords)
        return fetchData(url)
    }

    fun ensureValidPostcode(inputStr: String): String {
        // The GeoDojo API only recognises postcodes with a space, this method checks for postcodes
        // with missing spaces and adds one if necessary

        //Regex for postcode found here
        // https://stackoverflow.com/a/51885364
        // Note original answer regex is "^[A-Z]{1,2}\d[A-Z\d]? ?\d[A-Z]{2}$"
        // I have removed the " ?" from the middle in order to only detect postcodes without a space
        // The outcode and incode are the first and second parts of the postcode, respectively
        // I derived regexes for these from the above using lookahead and lookbehind
        //TODO Write unit tests for this method

        if (inputStr.length > 7) {
            return inputStr
        }

        val spacelessPostcodeRegex = Regex("^[A-Z]{1,2}\\d[A-Z\\d]?\\d[A-Z]{2}$")
        val outcodeRegex = Regex("^[A-Z]{1,2}\\d[A-Z\\d]?(?=\\d[A-Z]{2}$)")
        val incodeRegex = Regex("(?<=^[A-Z]{1,2}\\d[A-Z\\d]?)\\d[A-Z]{2}$")

        val inputIsInvalidPostcode = inputStr.uppercase().matches(spacelessPostcodeRegex)

        if (inputIsInvalidPostcode) {
            val invalidPostcode = inputStr.uppercase()
            val incode = incodeRegex.find(invalidPostcode)?.value
            val outcode = outcodeRegex.find(invalidPostcode)?.value
            val validPostcode = "$outcode $incode"
            Log.i("custom", "Invalid postcode found: $inputStr, replaced with $validPostcode")
            return validPostcode
        }
        return inputStr
    }

    private fun fetchData(urlString: String): List<Location> {
        val url = URL(urlString)
        var jsonString = ""

        val thread = Thread {
            Log.i("custom", "Thread started")
            try {
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 10_000
                connection.readTimeout = 10_000
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
                    jsonString = if (scanner.hasNext()) scanner.next() else "Scanner empty"
                    Log.i(
                        "custom",
                        "HTTP Connection successful, json string is ${jsonString.take(80)}"
                    )
                } else {
                    Log.w("custom", "Connection failed. HTTP Response code $responseCode")
                }


            } catch (e: IOException) {
                println(e)
                Log.e("custom", "ERROR: $e")
            }
        }
        thread.start()
        thread.join()

//        Log.i("custom", "Running processJSON with string ${jsonString.take(80)}")
        val locations = processJSON(jsonString)
        Log.i("custom", "JSON processing complete, result is ${locations.firstOrNull()?.address}")
        return processJSON(jsonString)

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
        if (jsonString.isBlank()) {
            return listOf(Location().apply { gridRef = "No location found" })
        }

        val resultJsonArray = JSONObject(jsonString).getJSONArray("result")
        val locations = mutableListOf<Location>()

        for (i in 0 until resultJsonArray.length()) {
            val locationObj = resultJsonArray.getJSONObject(i)
            locations.add(Location().apply {
                address = locationObj.get("location").toString()
                gridRef = locationObj.get("grid").toString()
            })
        }

        return locations.toList()
    }


}