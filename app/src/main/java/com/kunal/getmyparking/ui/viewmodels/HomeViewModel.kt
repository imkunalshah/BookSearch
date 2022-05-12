package com.kunal.getmyparking.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunal.getmyparking.data.network.models.BooksResponse
import com.kunal.getmyparking.data.repositories.BooksRepository
import com.kunal.getmyparking.utils.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val booksRepository: BooksRepository
) : ViewModel() {

    private var _booksList = MutableLiveData<List<BooksResponse.Book>>()
    val booksList: LiveData<List<BooksResponse.Book>> = _booksList
    private var searchJob: Job? = null
    var totalNumberOfItems: Int = 0
    var currentQuery = "a"
    var isLoading = false
    var isFirstLoad = true

    fun getBooks(query: String, startIndex: Int, onError: (t: Throwable) -> Unit) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            kotlin.runCatching {
                val response = booksRepository.getBooks(query, startIndex)
                withContext(Dispatchers.Main) {
                    _booksList.postValue(response.value)
                }
            }.onFailure {
                onError.invoke(it)
            }
        }
    }

}
