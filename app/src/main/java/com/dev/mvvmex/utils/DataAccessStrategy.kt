package com.dev.mvvmex.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dev.mvvmex.utils.Resource.Status.ERROR
import com.dev.mvvmex.utils.Resource.Status.SUCCESS
import kotlinx.coroutines.Dispatchers

fun <T> performGetOperation(
    networkCall: suspend () -> Resource<T>,
    networkHelper: NetworkHelper
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        if (networkHelper.isNetworkConnected()) {
            val responseStatus = networkCall.invoke()
            if (responseStatus.status == SUCCESS) {
                emit(Resource.success(responseStatus.data!!))
            } else if (responseStatus.status == ERROR) {
                emit(Resource.error(responseStatus.message!!))
            }
            return@liveData
        }
        emit(Resource.error("Internet issue"))
    }