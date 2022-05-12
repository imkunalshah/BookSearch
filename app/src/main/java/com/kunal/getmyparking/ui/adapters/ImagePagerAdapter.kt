package com.kunal.getmyparking.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.kunal.getmyparking.data.network.models.BooksResponse
import com.kunal.getmyparking.databinding.LayoutPagerImageBinding
import com.kunal.getmyparking.utils.loadBookCoverImage
import java.util.*

class ImagePagerAdapter(
    context: Context,
    private val bookList: List<BooksResponse.Book>?
) : PagerAdapter() {

    var layoutInflater: LayoutInflater? = null

    init {
        layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int = bookList?.size ?: 0

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = LayoutPagerImageBinding.inflate(layoutInflater!!)
        val bookImage = bookList?.get(position)?.volumeInfo?.imageLinks?.thumbnail?: ""
        binding.bookImage.loadBookCoverImage(
            bookImage,
            binding.bookImage
        )
        Objects.requireNonNull(container).addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}