package com.dream.live.cricket.score.hd.tv.presenters
import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.ChannelTvItemBinding
import com.dream.live.cricket.score.hd.streaming.models.Channel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class ChannelPresenter(private val context: Context) : Presenter() {

    var binding: ChannelTvItemBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.channel_tv_item, parent, false)
        binding = DataBindingUtil.bind(view)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val channel_item = item as Channel
        binding?.channelName?.text = channel_item.name
        binding?.channelImage?.let {
            Glide.with(context)
                .load(channel_item.image_url)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.placehold)
                        .error(R.drawable.placehold)
                )
                .into(it)
        }
        try {
            if (!channel_item?.date.isNullOrEmpty()) {
                dateAndTime(channel_item?.date)
            }
        } catch (e: Exception) {

        }
    }
    private fun dateAndTime(channelDate: String?) {
        val df = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = channelDate?.let { df.parse(it) }
        df.timeZone = TimeZone.getDefault()
        val formattedDate = date?.let { df.format(it) }
        val date2: Date? = formattedDate?.let { df.parse(it) }
        val cal = Calendar.getInstance()
        if (date2 != null) {
            cal.time = date2
        }
        var hours = cal[Calendar.HOUR_OF_DAY]
        val minutes = cal[Calendar.MINUTE]
        var timeInAmOrPm = ""

        if (hours > 0) {
            timeInAmOrPm = if (hours >= 12) {
                "PM"
            } else {
                "AM"
            }
        }


        if (hours > 0) {
            if (hours >= 12) {
                if (hours == 12) {
                    /////
                } else {
                    hours -= 12
                }
            }
        }

        if (hours == 0) {
            hours = 12
            timeInAmOrPm = "AM"
        }

        val dayOfTheWeek =
            DateFormat.format("EEEE", date) as String

        val day = DateFormat.format("dd", date) as String

        val monthString =
            DateFormat.format("MMM", date) as String
        val year = DateFormat.format("yyyy", date) as String

        if (minutes < 9) {
            binding?.dateOfChannel?.text =
                "$hours:0$minutes $timeInAmOrPm,  ,$day $monthString, $year"
        } else {
            binding?.dateOfChannel?.text =
                "$hours:$minutes $timeInAmOrPm,  $day $monthString, $year"
        }

        binding?.dateOfChannel?.visibility = View.VISIBLE
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        // Remove references to images so that the garbage collector can free up memory

    }
}