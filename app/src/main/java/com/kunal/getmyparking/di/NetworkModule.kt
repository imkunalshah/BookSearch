package com.kunal.getmyparking.di

import android.content.Context
import com.kunal.getmyparking.data.network.ConnectionLiveData
import com.kunal.getmyparking.data.network.NetworkConnectionInterceptor
import com.kunal.getmyparking.data.network.apis.BooksApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkConnectionInterceptor(@ApplicationContext context: Context): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideConnectionLiveData(@ApplicationContext context: Context): ConnectionLiveData {
        return ConnectionLiveData(context)
    }

    @Singleton
    @Provides
    fun provideBooksApiService(
        interceptor: NetworkConnectionInterceptor
    ): BooksApi {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }

}