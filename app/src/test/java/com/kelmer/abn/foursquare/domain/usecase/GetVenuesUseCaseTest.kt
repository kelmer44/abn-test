package com.kelmer.abn.foursquare.domain.usecase

import com.kelmer.abn.foursquare.common.resource.Resource
import com.kelmer.abn.foursquare.common.usecase.UseCase
import com.kelmer.abn.foursquare.common.util.NetworkInteractor
import com.kelmer.abn.foursquare.data.api.mock.MockUtils
import com.kelmer.abn.foursquare.data.converter.VenueConverter
import com.kelmer.abn.foursquare.data.db.model.Venue
import com.kelmer.abn.foursquare.data.repository.VenueRepository
import com.kelmer.abn.foursquare.domain.model.LatLon
import com.kelmer.abn.foursquare.util.TrampolineSchedulerProvider
import com.kelmer.abn.foursquare.util.checkIsFailure
import com.kelmer.abn.foursquare.util.checkIsSuccess
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import junit.framework.Assert
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.stubbing.Stubber

class GetVenuesUseCaseTest {
    private val schedulers = TrampolineSchedulerProvider()
    private val disposables = mutableListOf<UseCase>()

    @Before
    fun setUp() {
        RxJavaPlugins.setComputationSchedulerHandler { schedulers.computation() }
    }

    private val nextMock: (Resource<List<Venue>>) -> Unit = mock {
        whenever(mock.invoke(any())).thenReturn(Unit)
    }

    @Test
    fun `When retrieval fails, an error is emitted`() {

        val mockRepository: VenueRepository = mock {
            on { getVenues(any(), any()) }
                .doReturn(Single.error(Exception("listError")))
        }
        val mockNetworkInteractor: NetworkInteractor = mock{
            on {
                hasNetworkConnection()
            }.doReturn(true)
        }

        val getVenuesUseCase =
            GetVenuesUseCase(mockRepository, schedulers, mockNetworkInteractor)
                .apply { disposables.add(this) }

        getVenuesUseCase.execute(
            params = GetVenuesUseCase.Params("query", LatLon(52.3,4.89)),
            onNext = nextMock
        )

        argumentCaptor<Resource<List<Venue>>>().apply {
            checkIsFailure(nextMock) {
                Assert.assertEquals("listError", it.errorMessage)
            }
        }
    }

    @Test
    fun `When retrieval is successful, the data is wrapped in a Success object`() {
        val venueData1 = MockUtils.fromVenueDetailsToVenue(MockUtils.mockedVenues[1])
        val venueData2 = MockUtils.fromVenueDetailsToVenue(MockUtils.mockedVenues[2])

        val venue1 = VenueConverter().convert(venueData1)
        val venue2 = VenueConverter().convert(venueData2)

        val mockRepository: VenueRepository = mock {
            on { getVenues(any(), any()) }
                .doReturn(Single.just(listOf(venue1, venue2)))
        }
        val mockNetworkInteractor: NetworkInteractor = mock{
            on {
                hasNetworkConnection()
            }.doReturn(true)
        }
        val getVenuesUseCase =
            GetVenuesUseCase(mockRepository, schedulers, mockNetworkInteractor)
                .apply { disposables.add(this) }

        getVenuesUseCase.execute(
            params = GetVenuesUseCase.Params("bar", LatLon(52.3,4.89)),
            onNext = nextMock
        )


        argumentCaptor<Resource<List<Venue>>>().apply {
            checkIsSuccess(nextMock) {
                assertEquals(listOf(venue1, venue2), it.data)
            }
        }
    }


    @After
    fun tearDown() {
        disposables.clear()
    }
}