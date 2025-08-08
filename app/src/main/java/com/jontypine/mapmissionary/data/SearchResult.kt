package com.jontypine.mapmissionary.data

sealed class SearchResult() {
    data class FAIL(val info: String): SearchResult()
    data class SUCCESS(val info: String? = null): SearchResult()
    data object CONVERSION : SearchResult()
}