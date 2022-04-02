package com.dev.mvvmex.ui.activity.mainActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.dev.mvvmex.BaseActivity
import com.dev.mvvmex.R
import com.dev.mvvmex.utils.Resource


class MainActivity : BaseActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callGetUser()
    }

    private fun callGetUser() {
        viewModel.getUser().observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    mProgressDialog.dismiss()
                    Log.e(Companion.TAG, "callGetUser: " + it.data)
                }

                Resource.Status.ERROR -> {
                    Log.e("TAG", "setupObserverER: " + it.message)
                    mProgressDialog.dismiss()
                }

                Resource.Status.LOADING -> {
                    Log.e("TAG", "setupObserverER: Loading")
                    mProgressDialog.show()
                }
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}