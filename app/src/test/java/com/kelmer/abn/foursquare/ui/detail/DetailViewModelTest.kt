package com.kelmer.abn.foursquare.ui.detail

import androidx.lifecycle.Observer
import com.kelmer.abn.foursquare.common.resource.Resource
import com.kelmer.abn.foursquare.data.api.mock.MockUtils
import com.kelmer.abn.foursquare.data.db.model.ContactInfo
import com.kelmer.abn.foursquare.data.db.model.LocationInfo
import com.kelmer.abn.foursquare.data.db.model.Photo
import com.kelmer.abn.foursquare.data.db.model.VenueDetails
import com.kelmer.abn.foursquare.domain.usecase.GetVenueDetailsUseCase
import com.kelmer.abn.foursquare.domain.usecase.IGetVenueDetailsUseCase
import com.kelmer.abn.foursquare.util.ViewModelUnitTest
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert
import org.junit.Test
import java.lang.RuntimeException
import java.util.*

class DetailViewModelTest : ViewModelUnitTest() {

    @Test
    fun getVenue() {

        val testId = "123"
        val testVenue = Resource.Success(
            VenueDetails(
                testId,
                "testVenue",
                emptyList(),
                Photo("1", ""),
                "",
                5.7f,
                true,
                ContactInfo("",""),
                LocationInfo("", "", ""),
                ""
            )
        )
        val useCase = mock<IGetVenueDetailsUseCase>{
            whenever(mock.execute(any(), any()))
            .thenAnswer{
                val args = it.arguments
                val params: GetVenueDetailsUseCase.Params =
                    args[0] as GetVenueDetailsUseCase.Params
                val fOnNext: ((Resource<VenueDetails>) -> Unit) =
                    args[1] as (Resource<VenueDetails>) -> Unit

                if (params.id == testId) {
                    fOnNext(testVenue)
                } else fOnNext(Resource.failure(RuntimeException("not found")))
            }
        }
        val viewModel = DetailViewModel(useCase)
        val observer: Observer<Resource<VenueDetails>> = mock()


        val venueDetails = viewModel.getVenue(testId)
        venueDetails.observeForever(observer)

        argumentCaptor<Resource<VenueDetails>>().apply {
            verify(observer, times(1)).onChanged(capture())
            Assert.assertEquals(testVenue, firstValue)
        }
    }
}