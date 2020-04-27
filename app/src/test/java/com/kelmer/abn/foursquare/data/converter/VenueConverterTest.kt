package com.kelmer.abn.foursquare.data.converter

import com.kelmer.abn.foursquare.data.api.model.LocationData
import com.kelmer.abn.foursquare.data.api.model.detail.VenueData
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VenueConverterTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `a VenueConverter should map VenueData to a Venue`() {
        val testee = VenueConverter()
        val venueData = VenueData(
            "1",
            "venue",
            LocationData(
                52.3,
                4.89,
                "address",
                "netherlands",
                "amsterdam",
                "noord holland"
            )
        )

        val result = testee.convert(venueData)
        assertEquals(venueData.id, result.id)
        assertEquals("address, amsterdam", result.location)
    }


    @Test
    fun `a location is mapped into a formatted location`() {

        val locationData = LocationData(
            45.6,
            4.89,
            "address",
            "netherlands",
            "amsterdam",
            "noord holland"
        )
        val noAddress = locationData.copy(address = "")
        val noAddressNoCity = noAddress.copy(city = "")
        val noAddressNoCityNoState = noAddressNoCity.copy(state = "")
        val nothing = noAddressNoCityNoState.copy(country = "")

        assertEquals("address, amsterdam", LocationConverter.resolveLocation(locationData))
        assertEquals(
            "amsterdam", LocationConverter.resolveLocation(noAddress)
        )

        assertEquals(
            "noord holland",
            LocationConverter.resolveLocation(noAddressNoCity)
        )
        assertEquals(
            "netherlands",
            LocationConverter.resolveLocation(noAddressNoCityNoState)
        )
        assertEquals(
            "",
            LocationConverter.resolveLocation(nothing)
        )
    }

}