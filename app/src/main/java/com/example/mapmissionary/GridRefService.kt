package com.example.mapmissionary

import android.util.Log
import org.json.JSONObject
import javax.inject.Inject

//  TODO add another API call to get more data
//  https://api.geodojo.net/locate/region?q=SJ4013267361&type[]=major-town-city&type[]=police-force-area&type[]=county-unitary-authority
//  https://api.geodojo.net/locate/nearest?q=SH8105953509&&type[]=police-force-area&type[]=county-unitary-authority&type[]=postcode-centre

class GridRefService @Inject constructor(private val networkRepository: NetworkRepository) {
    object ApiConfig {
        const val BASE_URL = "https://api.geodojo.net/locate/find"
        const val MAX_RESULTS = 10
    }

    suspend fun getListOfLocations(keyWords: String): List<Location> {
        val validatedKeyWords = PostcodeValidator.validate(keyWords)
        val url = constructRequestUrl(validatedKeyWords)
        val locationsJSON = networkRepository.fetchData(url)
        return processJSON(locationsJSON)
    }


    private fun constructRequestUrl(keyWords: String): String {
        val locationArgs = keyWords.replace(Regex("\\s+"), "+")
        val maxResultsArg = "max=${ApiConfig.MAX_RESULTS}"
        val typeArg = "type=grid"
        val requestUrl = "${ApiConfig.BASE_URL}?q=$locationArgs&$maxResultsArg&$typeArg"
        Log.i("data", requestUrl)
        return requestUrl
    }

    private fun processJSON(jsonString: String): List<Location> {
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


}