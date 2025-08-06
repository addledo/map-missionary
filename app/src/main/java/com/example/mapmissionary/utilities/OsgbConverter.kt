package com.example.mapmissionary.utilities

import com.example.mapmissionary.data.LatLong
import com.example.mapmissionary.interfaces.GridRefProvider
import com.example.mapmissionary.interfaces.LatLongProvider
import uk.gov.dstl.geo.osgb.Constants
import uk.gov.dstl.geo.osgb.EastingNorthingConversion
import uk.gov.dstl.geo.osgb.NationalGrid
import uk.gov.dstl.geo.osgb.OSGB36
import kotlin.jvm.optionals.getOrNull

class OsgbConverter() : GridRefProvider, LatLongProvider {

    override suspend fun getGridFromLatLong(latLong: LatLong?): String? {
        if (latLong == null) {
            return "Invalid entry"
        }

        val osgb = OSGB36.fromWGS84(latLong.lat, latLong.long)

        val eastingNorthing = EastingNorthingConversion.fromLatLon(
            osgb,
            Constants.ELLIPSOID_AIRY1830_MAJORAXIS,
            Constants.ELLIPSOID_AIRY1830_MINORAXIS,
            Constants.NATIONALGRID_N0,
            Constants.NATIONALGRID_E0,
            Constants.NATIONALGRID_F0,
            Constants.NATIONALGRID_LAT0,
            Constants.NATIONALGRID_LON0
        )

        val gridRef = NationalGrid.toNationalGrid(eastingNorthing)
        return gridRef.getOrNull() ?: "Not found"
    }


    override suspend fun getLatLongFromGridRef(gridRef: String): LatLong? {
        // Convert string grid ref to Easting & Northing
        val eastingNorthing: DoubleArray
        try {
            eastingNorthing = NationalGrid.fromNationalGrid(gridRef)
        } catch (e: IllegalArgumentException) {
            return null
        }

        // Convert Easting and Northing to OSGB Lat & Long
        // This does not match the WGS84 latitude longitude used by most online maps
        val osgb = EastingNorthingConversion.toLatLon(
            eastingNorthing,
            Constants.ELLIPSOID_AIRY1830_MAJORAXIS,
            Constants.ELLIPSOID_AIRY1830_MINORAXIS,
            Constants.NATIONALGRID_N0,
            Constants.NATIONALGRID_E0,
            Constants.NATIONALGRID_F0,
            Constants.NATIONALGRID_LAT0,
            Constants.NATIONALGRID_LON0
        )

        // Convert to WGS84 lat & long
        val wgs84 = OSGB36.toWGS84(osgb[0], osgb[1])

        val latLong = LatLong(wgs84[0], wgs84[1])
        return latLong
    }

}