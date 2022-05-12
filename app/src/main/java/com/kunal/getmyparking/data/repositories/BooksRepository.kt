package com.kunal.getmyparking.data.repositories

import androidx.lifecycle.MutableLiveData
import com.kunal.getmyparking.data.network.SafeApiRequest
import com.kunal.getmyparking.data.network.apis.BooksApi
import com.kunal.getmyparking.data.network.models.BooksResponse

class BooksRepository(
    private val booksApi: BooksApi
) : SafeApiRequest() {

    private var bookList = MutableLiveData<List<BooksResponse.Book>>()

    suspend fun getBooks(
        query: String,
        startIndex: Int
    ): MutableLiveData<List<BooksResponse.Book>> {
        fetchEvents(query, startIndex)
        return bookList
    }

    private suspend fun fetchEvents(query: String, startIndex: Int) {
        val response = apiRequest { booksApi.fetchBooks(query, startIndex) }
        bookList.postValue(response.bookList)
    }
}