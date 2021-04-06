package com.lakue.pockettest.ui

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.lakue.pockettest.base.BaseApplication
import com.lakue.pockettest.repository.PocketRepository
import com.lakue.pockettest.utils.NetworkHelper
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Mock
    lateinit var repository: PocketRepository

    @Mock
    lateinit var networkHelper: NetworkHelper

    lateinit  var viewModel : MainViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = MainViewModel(networkHelper, repository)
    }

    @Test
    fun onBottomCatch() {
    }

    @Test
    fun onEditorTextChange() {
    }

    @Test
    fun fetchPocketInfo() {
        viewModel.fetchPocketInfo()
    }

    @Test
    fun onPocketClilck() {
    }
}