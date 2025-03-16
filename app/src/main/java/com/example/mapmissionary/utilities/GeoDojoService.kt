package com.example.mapmissionary.utilities

import com.example.mapmissionary.GeoDojoJsonParser
import com.example.mapmissionary.GeoDojoUrlConfig
import com.example.mapmissionary.data.LatLong
import com.example.mapmissionary.data.Location
import com.example.mapmissionary.interfaces.GridRefProvider
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
        return GeoDojoJsonParser.parseSearchJSON(locationsJSON)
    }

    override suspend fun getGridFromLatLong(latLong: LatLong?): String {
        if (latLong == null) {
            return "Not found"
        }
        val url = GeoDojoUrlConfig.getGridFromLatLongUrl(latLong)
        val resultJSON = networkRepository.fetchData(url)
        return GeoDojoJsonParser.parseSingleGridJSON(resultJSON)
    }
}