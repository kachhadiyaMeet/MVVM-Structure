package com.dev.mvvmex.di

import com.dev.mvvmex.data.APIInterface
import com.dev.mvvmex.data.repository.UserRepository
import com.dev.mvvmex.utils.constant.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Meet Kachhadiya on 16,July,2021
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(100, TimeUnit.SECONDS)
        httpClient.readTimeout(100, TimeUnit.SECONDS)
        httpClient.writeTimeout(100, TimeUnit.SECONDS)

        val logging = HttpLoggingInterceptor()
        logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun createAPIInterface(retrofit: Retrofit): APIInterface =
        retrofit.create(APIInterface::class.java)

    @Singleton
    @Provides
    fun provideAPIRepository(userRepository: APIInterface) = UserRepository(userRepository)

    @Singleton
    @Provides
    fun provideNetwork(network: ApplicationContext) = network
}