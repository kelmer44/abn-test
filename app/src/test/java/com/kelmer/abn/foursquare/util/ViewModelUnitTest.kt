package com.kelmer.abn.foursquare.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

abstract class ViewModelUnitTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
}
