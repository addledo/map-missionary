package com.jontypine.mapmissionary.interfaces

import com.jontypine.mapmissionary.data.Location

interface LocationSearchProvider {
    suspend fun searchLocationsByKeywords(keyWords: String): List<Location>
}