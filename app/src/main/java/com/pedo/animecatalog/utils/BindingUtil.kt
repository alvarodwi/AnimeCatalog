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

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Anime>?) {
    if(recyclerView.adapter != null){
        val adapter = recyclerView.adapter as AnimeListAdapter
        adapter.submitList(data)
    }
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
            .override(115,170)
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
fun bindStatus(statusImageView: ImageView, status: AnimeListingStatus?) {
    when (status) {
        AnimeListingStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        AnimeListingStatus.LOADING,AnimeListingStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter(value = ["status","repoData"], requireAll = false)
fun ImageView.bindRepoStatus(status: AnimeListingStatus?,repoData : List<Anime>?){
    repoData?.let{
        if(repoData.isEmpty()){
            when(status){
                AnimeListingStatus.ERROR,AnimeListingStatus.DONE-> {
                    this.visibility = View.VISIBLE
                    this.setImageResource(R.drawable.ic_error_outline)
                }
                AnimeListingStatus.LOADING -> {
                    this.visibility = View.GONE
                }
            }
        }else{
            when(status){
                AnimeListingStatus.ERROR, AnimeListingStatus.LOADING,AnimeListingStatus.DONE -> {
                    this.visibility = View.GONE
                }
            }
        }
    }
}


@BindingAdapter("showScore")
fun TextView.showScore(score : Double?){
    score?.let{
        text = if(it.equals(0.0)){
            context.getString(R.string.text_no_score)
        }else {
            val formatted = it.format(2)
            context.resources.getString(R.string.text_score, formatted)
        }
    }
}

@BindingAdapter("showRank")
fun TextView.showRank(rank : Int?){
    rank?.let{
        text = if(it.equals(0)){
            ""
        }else{
            context.getString(R.string.text_rank,rank)
        }
    }
}
