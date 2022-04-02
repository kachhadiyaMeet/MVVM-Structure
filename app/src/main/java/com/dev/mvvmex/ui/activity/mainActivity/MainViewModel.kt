package com.dev.mvvmex.ui.activity.mainActivity

import androidx.lifecycle.ViewModel
import com.dev.mvvmex.data.repository.UserRepository
import com.dev.mvvmex.utils.NetworkHelper
import com.dev.mvvmex.utils.performGetOperation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Meet Kachhadiya on 16,July,2021
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    val userRepository: UserRepository,
    val networkHelper: NetworkHelper
) : ViewModel() {

    fun getUser() = performGetOperation(
        networkCall = {
            userRepository.getAllUser()
        },
        networkHelper
    )
}