package com.kunal.getmyparking.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.kunal.getmyparking.data.network.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var connectionLiveData: ConnectionLiveData

    abstract fun initializeObservers()

    abstract fun initializeViews()
}