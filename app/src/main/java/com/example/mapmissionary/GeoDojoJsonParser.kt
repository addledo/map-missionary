package com.example.mapmissionary

import android.util.Log
import com.example.mapmissionary.data.Location
import org.json.JSONException
import org.json.JSONObject

object GeoDojoJsonParser {

    fun parseSearchJSON(jsonString: String): List<Location> {
//         The string returned is in this format:
//        {
//            "location": "",
//            "result": []
//        }

        try {
            val json = JSONObject(jsonString)
            val resultsJsonArray = json.getJSONArray("result")

            val locations = mutableListOf<Location>()

            for (i in 0 until resultsJsonArray.length()) {
                val locationObj = resultsJsonArray.getJSONObject(i)
                locations.add(
                    Location(
                        address = locationObj.get("location").toString(),
                        // TODO Add "place result as town"
                        gridRef = locationObj.get("grid").toString()
                    )
                )
            }
            return locations.toList()

        } catch (e: JSONException) {
            Log.e("json", e.toString())
            return listOf()
        }
    }


    fun parseSingleGridJSON(jsonString: String): String {
//        The string returned is in this format:
//        {
//            "location": "50,1",
//            "result": {
//            "grid": "SV0005000001"
//        }

        try {
            val json = JSONObject(jsonString)
            val resultJsonObj = json.getJSONObject("result")
            val gridRef = resultJsonObj.get("grid").toString()

            return gridRef

        } catch (e: JSONException) {
            Log.e("json", e.toString())
            return "Error parsing JSON"
        }
    }
}