package com.kunal.getmyparking.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kunal.getmyparking.R

fun View.gone() {
    this.visibility = GONE
}

fun View.visible() {
    this.visibility = VISIBLE
}

fun View.inVisible() {
    this.visibility = INVISIBLE
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun View.showNetworkUnavailableSnackBar(onRetry: () -> Unit) {
    Snackbar.make(
        this,
        this.context.resources.getString(R.string.internet_unavailable),
        Snackbar.LENGTH_INDEFINITE
    ).setAction(this.context.resources.getString(R.string.retry)) {
        onRetry.invoke()
    }.show()
}

fun Context.getDialogWidth(): Int {
    val displayMetrics = DisplayMetrics()
    (this as Activity?)?.windowManager
        ?.defaultDisplay
        ?.getMetrics(displayMetrics)
    val width = displayMetrics.widthPixels
    return (width * 80) / 100
}

fun View.loadBookCoverImage(imageUrl: String?, imageView: ImageView) {
    Glide.with(this.context).load(imageUrl).into(imageView)
}