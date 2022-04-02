package com.dev.mvvmex.data.repository

import com.dev.mvvmex.data.APIInterface
import com.dev.mvvmex.utils.BaseCallingApi
import javax.inject.Inject

/**
 * Created by Meet Kachhadiya on 16,July,2021
 */

class UserRepository @Inject constructor(private val apiInterface: APIInterface) :
    BaseCallingApi() {

    suspend fun getAllUser() = getResult {
        apiInterface.getUsers()
    }

}