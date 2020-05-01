package com.kelmer.abn.foursquare.ui.list

import com.kelmer.abn.foursquare.domain.usecase.GetVenuesUseCase
import com.kelmer.abn.foursquare.util.ViewModelUnitTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ListViewModelTest : ViewModelUnitTest(){

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getVenues() {

        val useCase = mock<GetVenuesUseCase>{
            whenever(mock.execute(any(), any()))
                .thenAnswer {

                }
        }
        val listViewModel = ListViewModel(useCase)

        listViewModel.getVenues()
    }

    @Test
    fun doSearch() {
    }
}