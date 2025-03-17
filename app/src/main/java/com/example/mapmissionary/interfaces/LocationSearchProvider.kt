package com.example.mapmissionary.interfaces

import com.example.mapmissionary.data.Location

interface LocationSearchProvider {
    suspend fun searchLocationsByKeywords(keyWords: String): List<Location>
}