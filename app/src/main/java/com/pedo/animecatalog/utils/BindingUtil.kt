package com.pedo.animecatalog.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.pedo.animecatalog.R
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.utils.adapter.AnimeListAdapter
import com.pedo.animecatalog.ui.listing.JikanApiStatus

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Anime>?) {
    val adapter = recyclerView.adapter as AnimeListAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("coverImg")
fun bindCoverImage(imgView : ImageView,imgUrl : String?){
    imgUrl?.let{
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        val transformOptions = RequestOptions()
            .override(57,85)
            .transform(RoundedCorners(10))
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                transformOptions
            )
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

@BindingAdapter("showScore")
fun TextView.showScore(score : Double?){
    score?.let{
        val formatted = it.format(2)
        text = context.resources.getString(R.string.text_score,formatted)
    }
}

@BindingAdapter("showRank")
fun TextView.showRank(rank : Int?){
    rank?.let{
        text = context.getString(R.string.text_rank,rank)
    }
}

@BindingAdapter("setVisibility")
fun TextView.setVisibility(flag :Boolean){
    if(flag){
        text = context.getString(R.string.add_fav)
    }else{
        text = context.getString(R.string.remove_fav)
    }
}
