package com.jontypine.mapmissionary.utilities

import android.util.Log

// The GeoDojo API only recognises postcodes with a space, this method checks for postcodes
// with missing spaces and adds one if necessary

//Regex for postcode found here
// https://stackoverflow.com/a/51885364
// Note original answer regex is "^[A-Z]{1,2}\d[A-Z\d]? ?\d[A-Z]{2}$"
// I have removed the " ?" from the middle in order to only detect postcodes without a space
// The outcode and incode are the first and second parts of the postcode, respectively
// I derived regexes for these from the above using lookahead and lookbehind

object PostcodeValidator {
    private val spacelessPostcodeRegex = Regex("^[A-Z]{1,2}\\d[A-Z\\d]?\\d[A-Z]{2}$")
    private val outcodeRegex = Regex("^[A-Z]{1,2}\\d[A-Z\\d]?(?=\\d[A-Z]{2}$)")
    private val incodeRegex = Regex("(?<=^[A-Z]{1,2}\\d[A-Z\\d]?)\\d[A-Z]{2}$")
    private const val MAXIMUM_POSTCODE_LENGTH = 7

    fun validate(string: String): String {
        if (string.length > MAXIMUM_POSTCODE_LENGTH) {
            return string
        }

        if (string.isBadPostcode()) {
            return reformatPostcode(string)
        }
        return string
    }

    private fun reformatPostcode(postcode: String): String {
        val invalidPostcode = postcode.uppercase()
        val incode = incodeRegex.find(invalidPostcode)?.value
        val outcode = outcodeRegex.find(invalidPostcode)?.value
        val validPostcode = "$outcode $incode"
        Log.d("postcode", "Invalid postcode found: $postcode, replaced with $validPostcode")
        return validPostcode
    }

    private fun String.isBadPostcode(): Boolean {
        return this.uppercase().matches(spacelessPostcodeRegex)
    }
}