package com.lakue.pockettest.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.lakue.pockettest.api.ApiHelper
import com.lakue.pockettest.di.NetworkModule
import com.lakue.pockettest.repository.PocketRepository
import com.lakue.pockettest.testPocket
import com.lakue.pockettest.utils.MainCoroutineRule
import com.lakue.pockettest.utils.NetworkHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

//    @get:Rule
//    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var networkModule: NetworkModule

    @Mock
    lateinit var mContext: Context
    @Mock
    private lateinit var apiHelper: ApiHelper

    @Mock
    private lateinit var repository: PocketRepository

//    var repository = mock(PocketRepository::class.java)

    lateinit var networkHelper: NetworkHelper
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        networkHelper = NetworkHelper(mContext)
        repository = PocketRepository(apiHelper)

        viewModel = MainViewModel(networkHelper, repository)
    }

    @Test
    fun get_pocket_name_info() {
        viewModel.fetchPocketInfo(1, 1)

        val fakePocket = testPocket
        assertThat(fakePocket).isEqualTo(viewModel.listPocketInfo)
    }
}