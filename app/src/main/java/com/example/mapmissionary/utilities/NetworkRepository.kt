package com.example.mapmissionary.utilities

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Scanner


class NetworkRepository {
    suspend fun fetchData(urlString: String): String {
        return withContext(Dispatchers.IO) {
            val url = URL(urlString)

            try {
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 10_000
                connection.readTimeout = 10_000
                connection.connect()

                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.i("network", "HTTP Connection successful")
                    val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
                    return@withContext if (scanner.hasNext()) scanner.next() else "Scanner empty"

                } else {
                    Log.w("network", "Connection failed. HTTP Response code $responseCode")
                    return@withContext ""
                }
            } catch (e: IOException) {
                println(e)
                Log.e("custom", "ERROR: $e")
                return@withContext ""
            }
        }
    }
}