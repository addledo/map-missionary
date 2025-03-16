package com.example.mapmissionary.utilities

import com.example.mapmissionary.GeoDojoUrlConfig
import com.example.mapmissionary.data.LatLong
import com.example.mapmissionary.data.Location
import com.example.mapmissionary.interfaces.GridRefProvider
import org.json.JSONObject
import javax.inject.Inject

//  TODO add another API call to get more data
//  https://api.geodojo.net/locate/region?q=SJ4013267361&type[]=major-town-city&type[]=police-force-area&type[]=county-unitary-authority
//  https://api.geodojo.net/locate/nearest?q=SH8105953509&&type[]=police-force-area&type[]=county-unitary-authority&type[]=postcode-centre

class GeoDojoService @Inject constructor(private val networkRepository: NetworkRepository) :
    GridRefProvider {

    suspend fun searchLocationsByKeywords(keyWords: String): List<Location> {
        val validatedKeyWords = PostcodeValidator.validate(keyWords)
        val url = GeoDojoUrlConfig.getGridFromKeywordsUrl(validatedKeyWords)
        val locationsJSON = networkRepository.fetchData(url)
        return processSearchJSON(locationsJSON)
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


    override suspend fun getGridFromLatLong(latLong: LatLong?): String {
        if (latLong == null) {
            return "Not found"
        }
        val url = GeoDojoUrlConfig.getGridFromLatLongUrl(latLong)
        val resultJSON = networkRepository.fetchData(url)
        return processGridJSON(resultJSON)
    }
}