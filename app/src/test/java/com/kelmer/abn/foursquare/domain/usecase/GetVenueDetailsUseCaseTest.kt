package com.kelmer.abn.foursquare.domain.usecase

import com.kelmer.abn.foursquare.common.resource.Resource
import com.kelmer.abn.foursquare.common.usecase.UseCase
import com.kelmer.abn.foursquare.data.api.mock.MockUtils
import com.kelmer.abn.foursquare.data.converter.PhotoConverter
import com.kelmer.abn.foursquare.data.converter.VenueDetailsConverter
import com.kelmer.abn.foursquare.data.db.model.LocationInfo
import com.kelmer.abn.foursquare.data.db.model.VenueDetails
import com.kelmer.abn.foursquare.data.repository.VenueRepository
import com.kelmer.abn.foursquare.util.TrampolineSchedulerProvider
import com.kelmer.abn.foursquare.util.checkIsFailure
import com.kelmer.abn.foursquare.util.checkIsSuccess
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import org.hamcrest.core.IsInstanceOf
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.stubbing.Stubber

class GetVenueDetailsUseCaseTest {
    private val schedulers = TrampolineSchedulerProvider()
    private val disposables = mutableListOf<UseCase>()

    @Before
    fun setUp() {
        RxJavaPlugins.setComputationSchedulerHandler { schedulers.computation() }
    }

    private val nextMock : (Resource<VenueDetails>) -> Unit = mock {
        whenever(mock.invoke(any())).thenReturn(Unit)
    }

    @Test
    fun `Requests for known venues result in a VenueDetails instance passed in as Success`() {
        val testVenueData = MockUtils.mockedVenues[0]
        val testId = testVenueData.id

        val testVenue = VenueDetailsConverter(PhotoConverter()).convert(testVenueData, listOf())

        val mockRepository: VenueRepository = mock {
            on { getVenue(testId) }.doReturn(Flowable.just(testVenue))
        }

        val testUseCase = GetVenueDetailsUseCase(mockRepository, schedulers)
            .apply {
                disposables.add(this)
            }

        testUseCase.execute(
            GetVenueDetailsUseCase.Params(testId),
            nextMock
        )

        argumentCaptor<Resource<VenueDetails>>().apply {
            checkIsSuccess(nextMock){
                assertEquals(testVenue.name, it.data.name)
                assertEquals(testVenue.id, it.data.id)
            }
        }
    }


    @Test
    fun `When the use case is unsuccessful, it returns an instance of Failure`() {
        val mockRepository: VenueRepository = mock {
            on { getVenue(any()) }.doReturn(Flowable.error(Exception("this failed")))
        }
        val testUseCase = GetVenueDetailsUseCase(mockRepository, schedulers)
            .apply {
                disposables.add(this)
            }

        testUseCase.execute(
            GetVenueDetailsUseCase.Params("12345abcd"),
            nextMock
        )

        argumentCaptor<Resource<VenueDetails>>().apply {
            checkIsFailure(nextMock) {
                assertEquals(it.errorMessage, "this failed")
            }
        }
    }

    @After
    fun tearDown() {
        disposables.clear()
    }
}