package com.kelmer.abn.foursquare.data.converter

import com.kelmer.abn.foursquare.data.api.mock.MockUtils
import com.kelmer.abn.foursquare.data.api.model.photos.PhotoData
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class PhotoConverterTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `a PhotoConverter should map PhotoData to a Photo with a formed URL`() {
        val testee = PhotoConverter()
        val photoData = PhotoData(
            "1",
            "https://myphoto.com/",
            "/photo.jpg",
            100,
            200
        )

        val result = testee.convert(photoData)
        assertEquals(photoData.id, result.photoId)
        assertEquals("https://myphoto.com/200x100/photo.jpg", result.url)
    }

}