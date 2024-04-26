package com.dream.live.cricket.score.hd.scores.adapter

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dream.live.cricket.score.hd.R

@BindingAdapter("firstTeamImg")
fun bindFirstTeamImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.placehold)
                    .error(R.drawable.placehold)
            )
            .into(imgView)
    }
}

@BindingAdapter("secTeamImg")
fun bindSecImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.placehold)
                    .error(R.drawable.placehold)
            )
            .into(imgView)
    }
}