package com.kunal.getmyparking.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.kunal.getmyparking.data.network.models.BooksResponse
import com.kunal.getmyparking.databinding.LayoutBookCardBinding
import com.kunal.getmyparking.utils.gone
import com.kunal.getmyparking.utils.loadBookCoverImage

class BookListAdapter(
    _bookList: List<BooksResponse.Book>,
    private val onImageClick: (position: Int, list: List<BooksResponse.Book>?) -> Unit
) : RecyclerView.Adapter<BookListAdapter.BookListViewHolder>() {

    var bookList: MutableList<BooksResponse.Book>? = null

    init {
        bookList = _bookList.toMutableList()
    }

    inner class BookListViewHolder(
        private val binding: LayoutBookCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val book = bookList?.get(adapterPosition)
            val bookTitle = book?.volumeInfo?.title
            val bookSubTitle = book?.volumeInfo?.subTitle
            val imageUrl = book?.volumeInfo?.imageLinks?.thumbnail ?: ""
            val retailPrice = book?.saleInfo?.retailPrice?.amount
            val retailPriceCurrency = book?.saleInfo?.retailPrice?.currencyCode
            val price = "$retailPriceCurrency $retailPrice"
            if (bookTitle.isNullOrBlank() || bookTitle.isNullOrEmpty()) {
                binding.titleTV.gone()
            }
            if (bookSubTitle.isNullOrBlank() || bookSubTitle.isNullOrEmpty()) {
                binding.subTitleTV.gone()
            }
            if (retailPrice.isNullOrBlank() || retailPrice.isNullOrEmpty()) {
                binding.priceTV.gone()
            }
            binding.titleTV.text = book?.volumeInfo?.title
            binding.subTitleTV.text = book?.volumeInfo?.subTitle
            binding.priceTV.text = price
            binding.coverImage.loadBookCoverImage(imageUrl, binding.coverImage)

            binding.root.setOnClickListener {
                onImageClick.invoke(adapterPosition, bookList?.toList())
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookListViewHolder {
        val binding =
            LayoutBookCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind()
    }

    override fun getItemCount(): Int = bookList?.size ?: 0

    fun updateList(bookList: List<BooksResponse.Book>) {
        val initialPosition = this.bookList?.size
        this.bookList?.addAll(bookList.toMutableList())
        val finalPosition = this.bookList?.size
        notifyItemRangeInserted(initialPosition!!, finalPosition!!)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetData() {
        bookList?.clear()
        notifyDataSetChanged()
    }

}