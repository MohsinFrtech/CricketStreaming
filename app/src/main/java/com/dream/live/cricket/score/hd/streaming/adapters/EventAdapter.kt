package com.dream.live.cricket.score.hd.streaming.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.ItemLayoutEventsBinding
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.models.Event
import com.dream.live.cricket.score.hd.streaming.ui.fragments.StreamingFragmentDirections
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.NavigateData
import com.dream.live.cricket.score.hd.streaming.utils.objects.CodeUtils.setSafeOnClickListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants

class EventAdapter(val context: Context, private val navigateData: NavigateData, val list: List<Event?>,
                   private val adType:String, private val adManager: AdManager
): ListAdapter<Event, EventAdapter.EventAdapterViewHolder>(EventAdapterDiffUtilCallback) {



    private var bindingEvent: ItemLayoutEventsBinding?=null
    private var liveChannelCount=0
    object EventAdapterDiffUtilCallback: DiffUtil.ItemCallback<Event>()
    {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
           return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
           return oldItem==newItem
        }

    }



    class EventAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



    }




    override fun onBindViewHolder(holder: EventAdapterViewHolder, position: Int) {

        holder.setIsRecyclable(false)
        bindingEvent?.data=currentList[position]
        if (!currentList[position].channels.isNullOrEmpty()) {

            liveChannelCount=0
            for (i in currentList[position].channels!!)
            {
                if (i.live == true)
                {
                    var channel_belongs_country = false

                    if (!i.country_codes.isNullOrEmpty()){
                        i.country_codes?.forEach {
                            code->
                            if (code?.equals(Constants.currentCountryCode, true) == true){
                                channel_belongs_country = true
                            }
                        }
                        if (channel_belongs_country){
                            liveChannelCount++
                        }
                    }else{
                        liveChannelCount++
                    }
                }
            }
            bindingEvent?.liveChannels?.text= "$liveChannelCount Channels"

        }

        holder.itemView.setSafeOnClickListener {

            val direction= StreamingFragmentDirections.actionStreamingToChannel(currentList[position])
            navigateData.navigation(direction)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapterViewHolder {
        val view =LayoutInflater.from(context).inflate(R.layout.item_layout_events,parent,false)
        bindingEvent=DataBindingUtil.bind(view)
        return EventAdapterViewHolder(view)
    }
}