package com.example.mapmissionary

import android.util.Log
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Scanner

class GridRefService {
    object ApiConfig {
        const val BASE_URL = "https://api.geodojo.net/locate/find"
        const val MAX_RESULTS = 10
    }

    fun getListOfLocations(keyWords: String): List<Location> {
        val validatedKeyWords = PostcodeValidator.validate(keyWords)
        val url = constructRequestUrl(validatedKeyWords)
        return fetchData(url)
    }


    private fun fetchData(urlString: String): List<Location> {
        val url = URL(urlString)
        var jsonString = ""

        val thread = Thread {
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

        val locations = processJSON(jsonString)
        return locations

    }

    private fun constructRequestUrl(keyWords: String): String {
        val locationArgs = keyWords.replace(Regex("\\s+"), "+")
        val maxResultsArg = "max=${ApiConfig.MAX_RESULTS}"
        val typeArg = "type=grid"
        val requestUrl = "${ApiConfig.BASE_URL}?q=$locationArgs&$maxResultsArg&$typeArg"
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
//        return listOf(Location("foo","bar"))
    }


}