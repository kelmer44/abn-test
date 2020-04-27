package com.kelmer.abn.foursquare.domain.usecase

import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.kelmer.abn.foursquare.common.resource.Resource
import com.kelmer.abn.foursquare.common.usecase.UseCase
import com.kelmer.abn.foursquare.common.util.NetworkInteractor
import com.kelmer.abn.foursquare.data.api.mock.MockUtils
import com.kelmer.abn.foursquare.data.converter.PhotoConverter
import com.kelmer.abn.foursquare.data.converter.VenueDetailsConverter
import com.kelmer.abn.foursquare.data.db.model.VenueDetails
import com.kelmer.abn.foursquare.data.repository.VenueRepository
import com.kelmer.abn.foursquare.util.TrampolineSchedulerProvider
import com.kelmer.abn.foursquare.util.checkIsFailure
import com.kelmer.abn.foursquare.util.checkIsSuccess
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import io.reactivex.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetVenueDetailsUseCaseTest {
    private val schedulers = TrampolineSchedulerProvider()
    private val disposables = mutableListOf<UseCase>()

    lateinit var networkInteractor: NetworkInteractor

    @Mock
    lateinit var connectivityManager: ConnectivityManager

    @Mock
    lateinit var networkInfo: NetworkInfo


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setComputationSchedulerHandler { schedulers.computation() }
        Mockito.`when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        Mockito.`when`(networkInfo.isConnectedOrConnecting).thenReturn(true)
    }

    private val nextMock: (Resource<VenueDetails>) -> Unit = mock {
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

        val mockNetworkInteractor: NetworkInteractor = mock()
        val testUseCase = GetVenueDetailsUseCase(mockRepository, schedulers, mockNetworkInteractor)
            .apply {
                disposables.add(this)
            }

        testUseCase.execute(
            GetVenueDetailsUseCase.Params(testId),
            nextMock
        )

        argumentCaptor<Resource<VenueDetails>>().apply {
            checkIsSuccess(nextMock) {
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

        val mockNetworkInteractor: NetworkInteractor = mock{
            on {
                hasNetworkConnection()
            }.doReturn(true)
        }

        val testUseCase = GetVenueDetailsUseCase(mockRepository, schedulers, mockNetworkInteractor)
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