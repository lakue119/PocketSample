package com.lakue.pockettest

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatDialog
import com.lakue.pockettest.base.BaseApplication
import com.lakue.pockettest.utils.BaseUtils.init
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class PocketApplication : BaseApplication() {

    var progressDialog: AppCompatDialog? = null

    companion object{
        lateinit var pocketApplication: PocketApplication

        fun getInstance(): PocketApplication{
            return pocketApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        pocketApplication = this
        init(this)
    }

    fun showLoading(activity: Activity){
        if(activity.isFinishing){
            return
        }

        if(progressDialog != null && progressDialog?.isShowing!!){

        } else {
            progressDialog = AppCompatDialog(activity)
            progressDialog?.apply{
                setCancelable(false)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setContentView(R.layout.progress_loading)
                show()
            }
        }
    }

    fun hideLoading(){
        if(progressDialog != null && progressDialog?.isShowing!!){
            progressDialog?.dismiss()
        }
    }
}