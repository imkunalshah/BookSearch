package com.kunal.getmyparking.data.network.apis

import com.kunal.getmyparking.data.network.models.BooksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {

    @GET("volumes")
    suspend fun fetchBooks(
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int
    ): Response<BooksResponse>

}