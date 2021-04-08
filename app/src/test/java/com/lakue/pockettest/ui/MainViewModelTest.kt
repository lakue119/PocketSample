package com.lakue.pockettest.ui

import android.app.ActivityManager
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.lakue.pockettest.api.ApiHelper
import com.lakue.pockettest.di.NetworkModule
import com.lakue.pockettest.repository.PocketRepository
import com.lakue.pockettest.testPocket
import com.lakue.pockettest.utils.MainCoroutineRule
import com.lakue.pockettest.utils.NetworkHelper
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.testng.Assert.assertTrue
import javax.inject.Inject


@HiltAndroidTest
@ExperimentalCoroutinesApi
@UninstallModules(NetworkModule::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

//    @get:Rule
//    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Mock
    lateinit var mContext: Context

    @Mock
    private lateinit var apiHelper: ApiHelper

    @Mock
    private lateinit var repository: PocketRepository

    @Mock
    private lateinit var networkHelper: NetworkHelper

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {

        repository = PocketRepository(apiHelper)
        viewModel = MainViewModel(networkHelper, repository)
    }



    @Test
    fun get_pocket_name_info() {

        Mockito.`when`(networkHelper.isNetworkConnected()).thenReturn(false)


        viewModel.fetchPocketInfo(1, 1)
        val fakePocket = testPocket
        assertThat(fakePocket).isEqualTo(viewModel.listPocketInfo)
    }
}