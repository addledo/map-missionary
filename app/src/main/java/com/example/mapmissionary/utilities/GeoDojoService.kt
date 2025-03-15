package com.example.mapmissionary.utilities

import android.util.Log
import com.example.mapmissionary.data.LatLong
import com.example.mapmissionary.data.Location
import com.example.mapmissionary.interfaces.GridRefProvider
import org.json.JSONObject
import javax.inject.Inject

//  TODO add another API call to get more data
//  https://api.geodojo.net/locate/region?q=SJ4013267361&type[]=major-town-city&type[]=police-force-area&type[]=county-unitary-authority
//  https://api.geodojo.net/locate/nearest?q=SH8105953509&&type[]=police-force-area&type[]=county-unitary-authority&type[]=postcode-centre

class GeoDojoService @Inject constructor(private val networkRepository: NetworkRepository): GridRefProvider {
    object ApiConfig {
        const val SEARCH_BASE_URL = "https://api.geodojo.net/locate/find"
        const val GRID_BASE_URL = "https://api.geodojo.net/locate/grid?type=grid&q="

        const val MAX_RESULTS = 10
    }

    suspend fun searchLocationsByKeywords(keyWords: String): List<Location> {
        val validatedKeyWords = PostcodeValidator.validate(keyWords)
        val url = constructSearchRequestUrl(validatedKeyWords)
        val locationsJSON = networkRepository.fetchData(url)
        return processSearchJSON(locationsJSON)
    }

    suspend fun getGridFromCoordinates(coordinateString: String?): String {
        if (coordinateString == null) {
            return "Not found"
        }
        val url = ApiConfig.GRID_BASE_URL + formatCoordinateStringForApi(coordinateString)
        Log.i("url", url)
        val resultJSON = networkRepository.fetchData(url)
        return processGridJSON(resultJSON)
    }



    private fun formatCoordinateStringForApi(coordinateString: String): String {
        val formattedString = coordinateString.replace(", ", "+")
        Log.i("formatting", "Coordinates formatted to $formattedString")
        return formattedString
    }

    private fun constructSearchRequestUrl(keyWords: String): String {
        val locationArgs = keyWords.replace(Regex("\\s+"), "+")
        val maxResultsArg = "max=${ApiConfig.MAX_RESULTS}"
        val typeArg = "type=grid"
        val requestUrl = "${ApiConfig.SEARCH_BASE_URL}?q=$locationArgs&$maxResultsArg&$typeArg"
        Log.i("url", requestUrl)
        return requestUrl
    }

    private fun processSearchJSON(jsonString: String): List<Location> {
        if (jsonString.isBlank()) {
            return listOf(Location())
        }

        val resultJsonArray = JSONObject(jsonString).getJSONArray("result")
        val locations = mutableListOf<Location>()

        for (i in 0 until resultJsonArray.length()) {
            val locationObj = resultJsonArray.getJSONObject(i)
            locations.add(
                Location(
                    address = locationObj.get("location").toString(),
                    gridRef = locationObj.get("grid").toString()
                )
            )
        }
        return locations.toList()
    }

    private fun processGridJSON(jsonString: String): String {
        if (jsonString.isBlank()) {
            return "Not found"
        }

        // TODO Add try / catch
        val resultJsonObj = JSONObject(jsonString).getJSONObject("result")
        val gridStr = resultJsonObj.get("grid").toString()
        return gridStr
    }

    private fun LatLong.formatForApiQuery(): String {
        return "$lat+$long"
    }

    override suspend fun getFromLatLong(latLong: LatLong): String {
        val url = ApiConfig.GRID_BASE_URL + latLong.formatForApiQuery()
        val resultJSON = networkRepository.fetchData(url)
        return processGridJSON(resultJSON)
    }
}