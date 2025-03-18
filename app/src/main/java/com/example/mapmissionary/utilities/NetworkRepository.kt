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
    suspend fun fetchData(
        urlString: String,
        connectTimeout: Int = 5,
        readTimeout: Int = 5
    ): String? {

        return withContext(Dispatchers.IO) {
            val url = URL(urlString)
            try {
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = connectTimeout * 1_000     //Convert s to ms
                connection.readTimeout = readTimeout * 1_000           //Convert s to ms
                Log.d(debugTag, "Connecting to network...")
                connection.connect()

                val responseCode = connection.responseCode

                if (!isActive) return@withContext null

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d(debugTag, "HTTP Connection successful")
                    val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
                    val response = if (scanner.hasNext()) scanner.next() else ""
                    return@withContext response

                } else {
                    Log.d(debugTag, "Connection failed. HTTP Response code $responseCode")
                    return@withContext null
                }
            } catch (e: IOException) {
                if (!isActive) {
                    Log.d(debugTag, "Caught IOException but job cancelled")
                    return@withContext null
                }
                Log.d(debugTag, "Caught IO Exception: $e")
                throw Exception("Something went wrong - are you sure you're connected to the internet?")
            }
        }
    }
}