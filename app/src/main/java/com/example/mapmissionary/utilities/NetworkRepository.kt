package com.example.mapmissionary.utilities

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Scanner


class NetworkRepository {
    private val debugTag = "network_repo"
    suspend fun fetchData(urlString: String): String {
        return withContext(Dispatchers.IO) {
            val url = URL(urlString)

            try {
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 5_000
                connection.readTimeout = 5_000
                Log.d(debugTag, "Connecting to network...")
                connection.connect()

                val responseCode = connection.responseCode

                if (!isActive) return@withContext ""

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d(debugTag, "HTTP Connection successful")
                    val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
                    val response = if (scanner.hasNext()) scanner.next() else ""
                    Log.d(debugTag, "Result: ${System.lineSeparator()}$response")
                    return@withContext response

                } else {
                    Log.d(debugTag, "Connection failed. HTTP Response code $responseCode")
                    return@withContext ""
                }
            } catch (e: IOException) {
                if (!isActive) {
                    Log.d(debugTag, "Caught IOException but job cancelled")
                    return@withContext ""
                }
                Log.d(debugTag, "Caught IO Exception: $e")
                return@withContext ""
            }
        }
    }
}