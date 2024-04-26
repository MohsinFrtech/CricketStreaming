package com.dream.live.cricket.score.hd.streaming.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.dream.live.cricket.score.hd.R

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView,url:String?)
{
    if (url!=null)
    {
            Glide.with(imageView.context)
                .load(url)
                .placeholder(R.drawable.channelplaceholder)
                .error(R.drawable.channelplaceholder)
                .into(imageView)

    }


}