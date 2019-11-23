package com.pedo.animecatalog.utils

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pedo.animecatalog.R
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.ui.adapter.AnimeListAdapter
import com.pedo.animecatalog.ui.animelist.JikanApiStatus

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,data : List<Anime>?){
    val adapter = recyclerView.adapter as AnimeListAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .override(100,200
            '[]')
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("jikanApiStatus")
fun bindStatus(statusImageView: ImageView, status: JikanApiStatus?) {
    when (status) {
        JikanApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        JikanApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        JikanApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
