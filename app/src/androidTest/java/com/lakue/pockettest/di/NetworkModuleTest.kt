//package com.lakue.pockettest.di
//
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import dagger.Binds
//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.android.internal.managers.ApplicationComponentManager
//import dagger.hilt.android.testing.HiltAndroidTest
//import dagger.hilt.android.testing.UninstallModules
//import junit.framework.TestCase
//import org.junit.runner.RunWith
//
//@HiltAndroidTest
//@UninstallModules(NetworkModule::class)
//@RunWith(AndroidJUnit4::class)
//class NetworkModuleTest{
//    @Module
//    @InstallIn(ApplicationComponentManager::class)
//    abstract class TestNetworkModule{
//        @Binds
//        abstract fun bindprovideOkHttpClient(
//            provideOkHttpClient: TestNetworkModule
//        )
//    }
//}