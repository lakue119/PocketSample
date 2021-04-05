package com.lakue.pockettest.ui

import com.lakue.pockettest.R
import com.lakue.pockettest.base.BaseActivity
import com.lakue.pockettest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override fun init() {
        showLoadingDialog()
    }
}