package com.kelmer.abn.foursquare.ui.detail

import androidx.lifecycle.Observer
import com.kelmer.abn.foursquare.common.resource.Resource
import com.kelmer.abn.foursquare.data.db.model.ContactInfo
import com.kelmer.abn.foursquare.data.db.model.LocationInfo
import com.kelmer.abn.foursquare.data.db.model.Photo
import com.kelmer.abn.foursquare.data.db.model.VenueDetails
import com.kelmer.abn.foursquare.domain.usecase.GetVenueDetailsUseCase
import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Assert
import org.junit.Before

import org.junit.Test
import java.lang.RuntimeException

class DetailViewModelTest {

    @Before
    fun setUp() {
    }



    @Test
    fun `Requesting a specific venue calls the injected use case and delivers its result to the LiveData object`() {

        val testVenue = Resource.success(
            VenueDetails(
                "1",
                "venue",
                listOf(),
                Photo("1", "https://fastly.4sqi.net/img/general/612x612/12599336_c1HXD02egzZJcdogt2P2USFt71khARwzH-wmXAjtCuA.jpg"),
                "lorem ipsum dolor sit amet",
                4.5f,
                true,
                ContactInfo("paradisoamsterdam","+31 657 657 657"),
                LocationInfo("Weterinschans", "Amsterdam", "Netherlands"),
                "Weterinschans, Amsterdam"
            )
        )

        val testId = (testVenue as Resource.Success).data.id


        val mockUseCase = mock<GetVenueDetailsUseCase> {
            whenever(mock.execute(any(), any()))
                .thenAnswer {
                    val args = it.arguments
                    val params: GetVenueDetailsUseCase.Params =
                        args[0] as GetVenueDetailsUseCase.Params
                    val fOnNext: ((Resource<VenueDetails>) -> Unit) =
                        args[1] as (Resource<VenueDetails>) -> Unit

                    if (params.id == testId) {
                        fOnNext(testVenue)
                    } else {
                        fOnNext(Resource.failure(RuntimeException("not found")))
                    }
                }
        }

        val viewModel =
            DetailViewModel(mockUseCase)
        val observer: Observer<Resource<VenueDetails>> = mock()

        val vehicleDetails = viewModel.getVenue(testId)
        vehicleDetails.observeForever(observer)

        argumentCaptor<Resource<VenueDetails>>().apply {
            verify(observer, times(1)).onChanged(capture())
            Assert.assertEquals(testVenue, firstValue)
        }
    }


    @After
    fun tearDown() {
    }
}