package com.example.mapmissionary.utilities

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
                        gridRef = locationObj.get("grid").toString(),
                        town = locationObj.get("place").toString()
                    )
                )
            }
            return locations.toList()

        } catch (e: JSONException) {
            Log.e("json", "Caught exception in parseSearchJSON()")
            Log.e("json", e.toString())
            return listOf()
        }
    }


    fun parseGridApiJson(jsonString: String, valueKey: String): String? {
//        The string returned is in this format:
//        {
//            "location": "50,1",
//            "result": {
//            "grid": "SV0005000001"
//        }

//        Where "grid" is the valueKey

        try {
            val json = JSONObject(jsonString)
            val resultJsonObj = json.getJSONObject("result")
            val gridRef = resultJsonObj.get(valueKey).toString()
            return gridRef

        } catch (e: JSONException) {
            Log.e(
                "json",
                "Caught exception in parseGridApiJSON() ${System.lineSeparator()} $e"
            )
            return null
        }
    }



    // Note - despite some duplicate logic, this function is kept separate from the above because
    // they use different endpoints. This way, if one changes, it won't break the other

    fun parseExtrasJson(fieldName: String, jsonString: String): String? {
        // EXAMPLE OF API RESPONSE
//        {
//            "location": "SH100000",
//            "result": {
//            "police-force-area": "North Wales",
//            "police-force-area-distance": 20087,
//            "police-force-area-location": "SH1099620062",
//            "police-force-area-location-distance": 20087,
//            "postcode-centre": "LL53 8DE",
//            "postcode-centre-distance": 22023,
//            "postcode-centre-location": "SH1186121945",
//            "postcode-centre-location-distance": 22023
//        }
        try {
            val json = JSONObject(jsonString)
            val resultJsonObj = json.getJSONObject("result")
            val fieldValue = resultJsonObj.get(fieldName).toString()
            return fieldValue
        } catch (e: JSONException) {
            return null
        }
    }
}