package com.example.mapmissionary.utilities

import android.util.Log

// The GeoDojo API only recognises postcodes with a space, this method checks for postcodes
// with missing spaces and adds one if necessary

//Regex for postcode found here
// https://stackoverflow.com/a/51885364
// Note original answer regex is "^[A-Z]{1,2}\d[A-Z\d]? ?\d[A-Z]{2}$"
// I have removed the " ?" from the middle in order to only detect postcodes without a space
// The outcode and incode are the first and second parts of the postcode, respectively
// I derived regexes for these from the above using lookahead and lookbehind
//TODO Write unit tests

object PostcodeValidator {
    private val spacelessPostcodeRegex = Regex("^[A-Z]{1,2}\\d[A-Z\\d]?\\d[A-Z]{2}$")
    private val outcodeRegex = Regex("^[A-Z]{1,2}\\d[A-Z\\d]?(?=\\d[A-Z]{2}$)")
    private val incodeRegex = Regex("(?<=^[A-Z]{1,2}\\d[A-Z\\d]?)\\d[A-Z]{2}$")

    fun validate(inputStr: String): String {
        if (inputStr.length > 7) {
            return inputStr
        }

        val inputIsInvalidPostcode = inputStr.uppercase().matches(spacelessPostcodeRegex)

        if (inputIsInvalidPostcode) {
            val invalidPostcode = inputStr.uppercase()
            val incode = incodeRegex.find(invalidPostcode)?.value
            val outcode = outcodeRegex.find(invalidPostcode)?.value
            val validPostcode = "$outcode $incode"
            Log.i("custom", "Invalid postcode found: $inputStr, replaced with $validPostcode")
            return validPostcode
        }
        return inputStr
    }
}