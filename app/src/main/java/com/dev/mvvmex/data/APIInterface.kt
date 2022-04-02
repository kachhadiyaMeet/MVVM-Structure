package com.dev.mvvmex.data

import com.dev.mvvmex.data.model.GetAllUser.getAllUserModel
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {

    @GET("users?page=2")
    suspend fun getUsers(): Response<getAllUserModel>

}