package com.kunal.getmyparking.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.kunal.getmyparking.R
import com.kunal.getmyparking.data.network.models.BooksResponse
import com.kunal.getmyparking.databinding.ActivityHomeBinding
import com.kunal.getmyparking.ui.activities.FullScreenActivity.Companion.BOOKS_LIST
import com.kunal.getmyparking.ui.activities.FullScreenActivity.Companion.POSITION
import com.kunal.getmyparking.ui.adapters.BookListAdapter
import com.kunal.getmyparking.ui.base.BaseActivity
import com.kunal.getmyparking.ui.fragments.QuitAppDialogFragment
import com.kunal.getmyparking.ui.viewmodels.HomeViewModel
import com.kunal.getmyparking.utils.*
import com.saksham.customloadingdialog.hideDialog
import com.saksham.customloadingdialog.showDialog
import timber.log.Timber

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()

    private val _bookListAdapter by lazy {
        BookListAdapter(
            emptyList(),
            ::onImageClick
        )
    }

    private var bookListAdapter: BookListAdapter? = null
        get() {
            kotlin.runCatching {
                field = _bookListAdapter
            }.onFailure {
                Timber.d("Error: $it")
                field = null
            }
            return field
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        initializeViews()
        initializeObservers()
        getData(query = homeViewModel.currentQuery, startIndex = homeViewModel.totalNumberOfItems)
    }

    private fun getData(query: String = "a", startIndex: Int = 0) {
        if (homeViewModel.isFirstLoad) {
            showDialog(this, false, R.raw.loading)
        }
        homeViewModel.getBooks(query, startIndex, ::handleApiError)
    }

    private fun handleApiError(throwable: Throwable) {
        hideDialog()
        when (throwable) {
            is NoInternetException -> {
                binding.root.showNetworkUnavailableSnackBar {
                    homeViewModel.totalNumberOfItems = 0
                    getData(query = homeViewModel.currentQuery)
                }
            }
            is ApiException -> {
                binding.root.showSnackBar(throwable.message.toString())
            }
        }
    }

    override fun initializeObservers() {
        homeViewModel.booksList.observe(this) { bookList ->
            hideDialog()
            homeViewModel.isFirstLoad = false
            if (!bookList.isNullOrEmpty()) {
                Timber.d("listSize:${bookList.size}")
                bookListAdapter?.updateList(bookList)
                if (homeViewModel.isLoading) binding.bookList.smoothScrollToPosition((homeViewModel.totalNumberOfItems + 10) - 9)
                homeViewModel.isLoading = false
                binding.notFoundAnimationView.gone()
            } else {
                if (homeViewModel.totalNumberOfItems == 0) {
                    binding.notFoundAnimationView.visible()
                }
                homeViewModel.isLoading = false
                Timber.d("listSize:Empty")
            }
        }

        connectionLiveData.observe(this) { isConnected ->
            if (!isConnected) {
                binding.root.showNetworkUnavailableSnackBar {
                    homeViewModel.totalNumberOfItems = 0
                    getData(query = homeViewModel.currentQuery)
                }
            }
        }
    }

    override fun initializeViews() {
        initializeBookList()
        initializeSearch()
        binding.loadMoreButton.setOnClickListener {
            homeViewModel.isLoading = true
            binding.loadMoreButton.gone()
            binding.progressBarBottom.visible()
            homeViewModel.totalNumberOfItems += 10
            getData(
                query = homeViewModel.currentQuery,
                startIndex = homeViewModel.totalNumberOfItems
            )
        }
        binding.scrollToTop.setOnClickListener {
            binding.bookList.smoothScrollToPosition(0)
        }
    }

    private fun initializeSearch() {
        binding.searchET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length >= 3) {
                    homeViewModel.totalNumberOfItems = 0
                    homeViewModel.currentQuery = s.toString()
                    bookListAdapter?.resetData()
                    getData(
                        query = homeViewModel.currentQuery,
                        startIndex = homeViewModel.totalNumberOfItems
                    )
                    return
                }
                if (s.isEmpty() || s.isBlank() || s.length < 3) {
                    homeViewModel.totalNumberOfItems = 0
                    homeViewModel.currentQuery = "a"
                    bookListAdapter?.resetData()
                    getData(
                        query = homeViewModel.currentQuery,
                        startIndex = homeViewModel.totalNumberOfItems
                    )
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }

    private fun initializeBookList() {
        binding.bookList.apply {
            adapter = bookListAdapter
        }
        binding.bookList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = binding.bookList.getDynamicGridLayoutManager()
                val totalItemCount = gridLayoutManager?.itemCount
                val lastCompletelyVisibleItemPosition =
                    gridLayoutManager?.findLastCompletelyVisibleItemPosition()?.plus(1)
                val firstCompletelyVisibleItemPosition =
                    gridLayoutManager?.findFirstCompletelyVisibleItemPosition()
                if (!homeViewModel.isLoading && totalItemCount == lastCompletelyVisibleItemPosition) {
                    if (lastCompletelyVisibleItemPosition != 0)
                        showLoadMoreLayout()
                } else {
                    if (!homeViewModel.isLoading)
                        hideLoadMoreLayout()
                }
                if (firstCompletelyVisibleItemPosition!! >= 4) {
                    showScrollToTop()
                } else {
                    hideScrollToTop()
                }
            }
        })
    }

    private fun hideScrollToTop() {
        binding.scrollToTop.gone()
    }

    private fun showScrollToTop() {
        binding.scrollToTop.visible()
    }

    private fun hideLoadMoreLayout() {
        binding.loadMoreLayout.gone()
        binding.loadMoreButton.gone()
        binding.progressBarBottom.gone()
    }

    private fun showLoadMoreLayout() {
        binding.loadMoreLayout.visible()
        binding.loadMoreButton.visible()
        binding.progressBarBottom.gone()
    }

    private fun onImageClick(position: Int, booksList: List<BooksResponse.Book>?) {
        val fullScreenIntent = Intent(this, FullScreenActivity::class.java)
        fullScreenIntent.putExtra(POSITION, position)
        fullScreenIntent.putExtra(BOOKS_LIST, ArrayList(booksList))
        startActivity(fullScreenIntent)
    }

    override fun onBackPressed() {
        val quitAppDialogFragment = QuitAppDialogFragment.newInstance().apply {
            isCancelable = false
        }.also {
            it.onQuitClicked = {
                super.onBackPressed()
            }
        }
        quitAppDialogFragment.show(supportFragmentManager, QuitAppDialogFragment.TAG)
    }
}