package com.kelmer.abn.foursquare.data.converter

import com.kelmer.abn.foursquare.data.api.model.LocationData
import com.kelmer.abn.foursquare.data.api.model.list.ContactInfoData
import com.kelmer.abn.foursquare.data.api.model.list.VenueDetailData
import com.kelmer.abn.foursquare.data.api.model.photos.PhotoData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class VenueDetailsConverterTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun convert() {
        val photoList = listOf<PhotoData>()
        val photoConverter: PhotoConverter = PhotoConverter()
        val venueDetailsConverter = VenueDetailsConverter(photoConverter)

        val venueData = VenueDetailData(
            "12345",
            "venue",
            "lorem ipsum",
            LocationData(42.3, 4.89, "address", "netherlands", "amsterdam", "noord holland"),
            ContactInfoData("+3111111111", "+31 111 111 111", "twitter"),
            "https://google.es",
            5.4f,
            PhotoData(
                "34",
                "https://myimage.com/",
                "/image.jpg",
                100,
                200
            )
        )

        val convert = venueDetailsConverter.convert(venueData,photoList)

        assertEquals("12345", convert.id)
        assertEquals("venue", convert.name)
        assertEquals("twitter", convert.contactInfo.twitter)
        assertEquals("+31 111 111 111", convert.contactInfo.phone)
        assertEquals("address, amsterdam", convert.formattedAddress)
    }
}