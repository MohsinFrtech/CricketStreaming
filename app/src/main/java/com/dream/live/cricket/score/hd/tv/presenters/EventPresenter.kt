package com.dream.live.cricket.score.hd.tv.presenters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.EventPresenterLayoutBinding
import com.dream.live.cricket.score.hd.streaming.models.Event

class EventPresenter(private val context: Context) : Presenter() {
    var binding: EventPresenterLayoutBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.event_presenter_layout, parent, false)
        binding = DataBindingUtil.bind(view)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val event = item as Event
        binding?.eventName?.text = event.name
        binding?.eventImg?.let {
            Glide.with(context)
                .load(event.image_url)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.placehold)
                        .error(R.drawable.placehold)
                )
                .into(it)
        }

        if (!event.channels.isNullOrEmpty()){
           val numberChannels=event.channels!!.filter {
                it.live==true
            }.size

           binding?.liveChannels?.text = ""+numberChannels+"  Channels"
        }
        

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        // Remove references to images so that the garbage collector can free up memory
    }
}