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
    suspend fun fetchData(
        urlString: String, connectTimeout: Int = 7, readTimeout: Int = 7
    ): String? {

        return withContext(Dispatchers.IO) {
            val url = URL(urlString)
            try {
                val connection = getConnection(url, connectTimeout, readTimeout)

                connection.connect()
                val responseCode = connection.responseCode
                Log.d("network_repo", "Connection: $responseCode")

                // Gives the coroutine a chance to stop if it's been cancelled
                if (!isActive) {
                    return@withContext null
                }

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return@withContext getStringFromResponse(connection)

                } else {
                    return@withContext null
                }
            } catch (e: IOException) {
                if (!isActive) {
                    return@withContext null
                }
                throw Exception("Something went wrong - are you sure you're connected to the internet?")
            }
        }
    }

    private fun getConnection(url: URL, connectTimeout: Int, readTimeout: Int): HttpURLConnection {
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = connectTimeout * 1_000     //Convert s to ms
        connection.readTimeout = readTimeout * 1_000           //Convert s to ms
        return connection
    }

    private fun getStringFromResponse(connection: HttpURLConnection): String? {
        val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
        val response = if (scanner.hasNext()) scanner.next() else ""
        return response
    }
}