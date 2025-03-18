package com.example.mapmissionary.data

sealed class Extra(val name: String, val apiName:String) {
    data object County: Extra("County", "county-unitary-authority")
    data object PoliceForce: Extra("Police Force", "police-force-area")
    data object PostcodeCenter: Extra("Postcode Center", "postcode-centre")
    data object Country: Extra("Country", "country")
}