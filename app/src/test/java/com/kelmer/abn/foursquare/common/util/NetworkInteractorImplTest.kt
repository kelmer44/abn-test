package com.kelmer.abn.foursquare.common.util

import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.kelmer.abn.foursquare.common.util.NetworkInteractor
import com.kelmer.abn.foursquare.common.util.NetworkInteractorImpl
import io.reactivex.Completable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class NetworkInteractorTest {

    lateinit var networkInteractor: NetworkInteractor

    @Mock
    lateinit var connectivityManager: ConnectivityManager

    @Mock
    lateinit var networkInfo: NetworkInfo

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        networkInteractor = NetworkInteractorImpl(connectivityManager)
        Mockito.`when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
    }

    @Test
    fun `If no network, then it returns false`() {
        Mockito.`when`(connectivityManager.activeNetworkInfo).thenReturn(null)

        Assert.assertFalse(networkInteractor.hasNetworkConnection())
    }

    @Test
    fun `if not connected, it returns false`() {
        Mockito.`when`(networkInfo.isConnectedOrConnecting).thenReturn(false)

        Assert.assertFalse(networkInteractor.hasNetworkConnection())
    }

    @Test
    fun `Returns true when connected`() {
        Mockito.`when`(networkInfo.isConnectedOrConnecting).thenReturn(true)

        Assert.assertTrue(networkInteractor.hasNetworkConnection())
    }

    @Test
    fun `Completable completes when connected`() {
        Mockito.`when`(networkInfo.isConnectedOrConnecting).thenReturn(true)
        Assert.assertEquals(networkInteractor.hasNetworkConnectionCompletable(), Completable.complete())
    }

    @Test
    fun `Completable errors when not connected`() {
        Mockito.`when`(networkInfo.isConnectedOrConnecting).thenReturn(false)

        Assert.assertTrue(networkInteractor.hasNetworkConnectionCompletable().blockingGet() is NetworkInteractor.NetworkUnavailableException)
    }
}