package com.dream.live.cricket.score.hd.streaming.adapters

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dream.live.cricket.score.hd.MainFragmentDirections
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.ItemLayoutChannelsBinding
import com.dream.live.cricket.score.hd.databinding.NativeAdLayoutBinding
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.date.ProcessingFile
import com.dream.live.cricket.score.hd.streaming.models.Channel
import com.dream.live.cricket.score.hd.streaming.ui.fragments.ChannelFragmentDirections
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.NavigateData
import com.dream.live.cricket.score.hd.streaming.utils.objects.CodeUtils.setSafeOnClickListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.channel_url_val
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.defaultString
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.nativeFacebook
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.passphraseVal
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.positionClick
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.previousClick
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.sepUrl
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userType1
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userType2
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userType3
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userType4
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userType5
import com.dream.live.cricket.score.hd.streaming.utils.objects.Defamation
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import java.text.SimpleDateFormat
import java.util.*


class ChannelAdapter(
    val context: Context, private val navigateData: NavigateData, val list: List<Channel?>,
    private val adType: String, private val adManager: AdManager, private val localVal: String,
    private val destination: String
) : ListAdapter<Channel, RecyclerView.ViewHolder>(
    ChannelAdapterDiffUtilCallback
) {


    private var channelAdapterBinding: ItemLayoutChannelsBinding? = null
    private val nativeAdsLayout = 1
    private val simpleMenuLayout = 0
    private var binding2: NativeAdLayoutBinding? = null
    private var fbNativeAd: com.facebook.ads.NativeAd? = null

    object ChannelAdapterDiffUtilCallback : DiffUtil.ItemCallback<Channel>() {
        override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean {

            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            nativeAdsLayout -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.native_ad_layout, parent, false)
                binding2 = DataBindingUtil.bind(view)
                NativeChannelViewHolder(view)
            }

            else -> {

                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_layout_channels, parent, false)
                channelAdapterBinding = DataBindingUtil.bind(view)
                ChannelAdapterViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position] == null) {
            return nativeAdsLayout
        }
        return simpleMenuLayout
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        holder.setIsRecyclable(false)
        when (getItemViewType(position)) {
            nativeAdsLayout -> {
                ////For native ads if ads_provider provide native ads..
                val viewHolder: NativeChannelViewHolder = holder as NativeChannelViewHolder

                if (adType.equals(Constants.facebook, true)) {

                    if (Cons.currentNativeAdFacebook != null) {
                        binding2?.fbNativeAdContainer?.let {
                            adManager.inflateFbNativeAd(
                                Cons.currentNativeAdFacebook!!, it
                            )
                        }
                    }
                    else
                    {
                        fbNativeAd = com.facebook.ads.NativeAd(context, nativeFacebook)
                        binding2?.adLoadLay2?.visibility = View.VISIBLE
                        binding2?.fbNativeAdContainer?.let {
                            adManager.loadFacebookNativeAd(
                                fbNativeAd!!,
                                it, binding2?.adLoadLay2
                            )
                        }
                    }
                }
                else if (adType.equals(Constants.adManagerAds, true)) {
                    binding2?.adLoadLay?.visibility = View.VISIBLE
                    binding2?.nativeAdView?.let {
                        adManager.loadAdmobNativeAdWithManager(
                            viewHolder,
                            it,
                            binding2?.adLoadLay
                        )
                    }
                }
                else if (adType.equals(Constants.admob, true)) {

                    if (Cons.currentNativeAd != null) {
                        binding2?.nativeAdView?.let {
                            adManager.populateNativeAdView(
                                Cons.currentNativeAd!!,
                                it
                            )
                        }
//                        binding2?.nativeAdView?.let { adManager.loadAdmobNativeAd(viewHolder, it) }
                    } else {
                        binding2?.adLoadLay?.visibility = View.VISIBLE
                        binding2?.nativeAdView?.let {
                            adManager.loadAdmobNativeAd(
                                viewHolder,
                                it,
                                binding2?.adLoadLay
                            )
                        }
                    }
                }

            }

            else -> {
                ///To set remaining events
//                channelAdapterBinding?.dataChannel = currentList[position]
                val viewHolder: ChannelAdapterViewHolder = holder as ChannelAdapterViewHolder
                viewHolder.textChannel.text = currentList[position].name
                loadImage(viewHolder.imageViewChannel,currentList[position].image_url)


                if (position == positionClick) {
                    viewHolder?.channelBck?.setBackgroundColor(Color.parseColor("#A9A9A9"))
                } else {
                    viewHolder?.channelBck?.setBackgroundColor(Color.parseColor("#00FFFFFF"))
                }

                if (!currentList[position].date.isNullOrEmpty()) {
                    dateAndTime(currentList[position].date,viewHolder)
                }

                holder.itemView.setSafeOnClickListener {
                    positionClick = holder.absoluteAdapterPosition
                    if (previousClick == -1)
                        previousClick = positionClick
                    else {
                        notifyItemChanged(previousClick)
                        previousClick = positionClick
                    }
                    notifyItemChanged(positionClick)
                    try {

                        if (currentList[position]?.channel_type.equals(
                                userType1, true
                            )
                        ) {

                            if (localVal.isNotEmpty()) {
                                val processingFile = ProcessingFile()
                                defaultString = processingFile.getChannelType(localVal)
                            }

                            val token = currentList[position].url?.let { it1 ->
                                Defamation.improveDeprecatedCode(it1)
                            }
                            val linkAppend = currentList[position].url + token

                            if (destination.equals("channel", true)) {
                                val channelDirection =
                                    ChannelFragmentDirections.actionChannelToPlayer(
                                        currentList[position].url, linkAppend,
                                        userType1
                                    )
                                navigateData.navigation(channelDirection)
                            } else {
                                val channelDirection = MainFragmentDirections.actionHomeToPlayer(
                                    currentList[position].url, linkAppend,
                                    userType1
                                )
                                navigateData.navigation(channelDirection)
                            }


                        } else if (currentList[position]?.channel_type.equals(
                                userType2, true
                            )
                        ) {

                            if (currentList[position]?.url?.contains(sepUrl) == true
                                && passphraseVal.isNotEmpty()
                            ) {

                                val separatedPart =
                                    currentList[position]?.url?.split(sepUrl)

                                channel_url_val = separatedPart?.get(1).toString()

                                if (destination.equals("channel", true)) {
                                    val channelDirection =
                                        ChannelFragmentDirections.actionChannelToPlayer(
                                            "baseLink",
                                            "linkAppend", userType2
                                        )
                                    navigateData.navigation(channelDirection)
                                } else {
                                    val channelDirection =
                                        MainFragmentDirections.actionHomeToPlayer(
                                            "baseLink",
                                            "linkAppend", userType2
                                        )
                                    navigateData.navigation(channelDirection)
                                }
                            }
                        } else if (currentList[position]?.channel_type.equals(
                                userType3, true
                            )
                        ) {
                            if (localVal.isNotEmpty()) {
                                val processingFile = ProcessingFile()
                                defaultString = processingFile.getChannelType(localVal)
                            }

                            val token = currentList[position].url?.let { it1 ->
                                Defamation.improveDeprecatedCode(it1)
                            }
                            val linkAppend = currentList[position].url + token
                            if (destination.equals("channel", true)) {

                                val channelDirection =
                                    ChannelFragmentDirections.actionChannelToPlayer(
                                        currentList[position].url, linkAppend,
                                        userType3
                                    )
                                navigateData.navigation(channelDirection)
                            } else {

                                val channelDirection = MainFragmentDirections.actionHomeToPlayer(
                                    currentList[position].url, linkAppend,
                                    userType3
                                )
                                navigateData.navigation(channelDirection)
                            }
                        }
                        else if (currentList[position]?.channel_type.equals(
                                userType5, true)){
                            val urlToPlay = currentList[position]?.url
                            if (!urlToPlay.isNullOrEmpty()){
                                try {
                                    val url = urlToPlay
                                    val i = Intent(Intent.ACTION_VIEW)
                                    i.data = Uri.parse(url)
                                    context?.startActivity(i)
                                } catch (e: ActivityNotFoundException) {
//                                    logger.printLog(tags, "exception : ${e.localizedMessage}")
                                }
                            }
                        }
                        else {
                            if (localVal.isNotEmpty()) {
                                val processingFile = ProcessingFile()
                                defaultString = processingFile.getChannelType(localVal)
                            }

                            val token = currentList[position].url?.let { it1 ->
                                Defamation.improveDeprecatedCode(it1)
                            }
                            val linkAppend = currentList[position].url + token

                            if (destination.equals("channel", true)) {
                                val channelDirection =
                                    ChannelFragmentDirections.actionChannelToPlayer(
                                        currentList[position].url, linkAppend,
                                        userType1
                                    )
                                navigateData.navigation(channelDirection)
                            } else {
                                val channelDirection = MainFragmentDirections.actionHomeToPlayer(
                                    currentList[position].url, linkAppend,
                                    userType1
                                )
                                navigateData.navigation(channelDirection)
                            }

                        }

                    } catch (e: Exception) {
                        Log.d("Token", "exception" + e.message)
                    }

                }


            }

        }

    }

    private fun dateAndTime(channelDate: String?, viewHolder: ChannelAdapterViewHolder) {
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

        var timeValue = ""
        if (minutes < 9) {
            timeValue =
                "$hours:0$minutes $timeInAmOrPm"


            viewHolder?.channelDateTime?.text =
                "$timeValue     . $dayOfTheWeek,$day $monthString $year"
        } else {
            timeValue =
                "$hours:$minutes $timeInAmOrPm"

            viewHolder?.channelDateTime?.text =
                "$timeValue     . $dayOfTheWeek,$day $monthString $year"
        }

        viewHolder?.channelDateTime?.visibility = View.VISIBLE
    }

    class ChannelAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewChannel = itemView.findViewById<ImageView>(R.id.channelImage)
        val textChannel = itemView.findViewById<TextView>(R.id.channelName)
        val channelDateTime = itemView.findViewById<TextView>(R.id.dateOfChannel)
        val channelBck = itemView.findViewById<ConstraintLayout>(R.id.channelBack)

    }

    ///View holder class
    class NativeChannelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

}