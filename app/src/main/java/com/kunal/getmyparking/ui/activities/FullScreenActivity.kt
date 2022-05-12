package com.kunal.getmyparking.ui.activities

import android.os.Bundle
import com.kunal.getmyparking.data.network.models.BooksResponse
import com.kunal.getmyparking.databinding.ActivityFullScreenBinding
import com.kunal.getmyparking.ui.adapters.ImagePagerAdapter
import com.kunal.getmyparking.ui.base.BaseActivity
import com.kunal.getmyparking.utils.anim.CubeInScalingAnimation
import com.kunal.getmyparking.utils.showNetworkUnavailableSnackBar


class FullScreenActivity : BaseActivity() {

    lateinit var binding: ActivityFullScreenBinding

    companion object {
        const val POSITION = "position"
        const val BOOKS_LIST = "booksList"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        initializeViews()
        initializeObservers()
    }

    override fun initializeViews() {
        val bookList = intent.extras?.getSerializable(BOOKS_LIST) as? ArrayList<BooksResponse.Book>
        val imagePagerAdapter = ImagePagerAdapter(this, ArrayList(bookList))
        binding.imageSlider.setPageTransformer(false, CubeInScalingAnimation())
        binding.imageSlider.apply {
            adapter = imagePagerAdapter
            currentItem = intent?.extras?.getInt(POSITION) ?: 0
        }
        binding.closeButton.setOnClickListener {
            finish()
        }
        binding.nextButton.setOnClickListener {
            binding.imageSlider.currentItem = binding.imageSlider.currentItem.plus(1)
        }
        binding.previousButton.setOnClickListener {
            binding.imageSlider.currentItem = binding.imageSlider.currentItem.minus(1)
        }
    }

    override fun initializeObservers() {
        connectionLiveData.observe(this) { isConnected ->
            if (!isConnected) {
                binding.root.showNetworkUnavailableSnackBar {}
            }
        }
    }
}