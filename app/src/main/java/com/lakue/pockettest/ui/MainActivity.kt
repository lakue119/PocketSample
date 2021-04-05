package com.lakue.pockettest.ui

import com.lakue.pockettest.R
import com.lakue.pockettest.base.BaseActivity
import com.lakue.pockettest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override fun init() {
        showLoading(true)

        binding.apply{
            vm = viewModel
        }

        viewModel.apply {
            isLoading eventObserve { showLoading(it) }
            toastEvent eventObserve {showToast(it)}

            fetchPocketInfo()
        }
    }

    fun showLoading(isShow: Boolean) {
        if (isShow) {
            showLoadingDialog()
        } else {
            hideLoadingDialog()
        }
    }
}