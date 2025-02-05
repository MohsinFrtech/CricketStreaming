package com.dream.live.cricket.score.hd.scores.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dream.live.cricket.score.hd.MainFragmentDirections
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.NativeAdLayoutBinding
import com.dream.live.cricket.score.hd.databinding.NativeAdLayoutNew2Binding
import com.dream.live.cricket.score.hd.databinding.RecentitemBinding
import com.dream.live.cricket.score.hd.scores.model.LiveScoresModel
import com.dream.live.cricket.score.hd.scores.ui.fragments.RecentFragmentDirections
import com.dream.live.cricket.score.hd.scores.ui.fragments.SeriesMatchFragmentDirections
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.NavigateData
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.scores.utility.Cons.convertDateAndTime
import com.dream.live.cricket.score.hd.scores.utility.Cons.convertLongToTime
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.utils.objects.CodeUtils.setSafeOnClickListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.cas_Ai
import com.google.android.gms.ads.nativead.NativeAd
import java.text.SimpleDateFormat
import java.util.*


class RecentMatchesAdapter(
    val context: Context,
    private val navigateData: NavigateData,
    val source: String,
    private val list: List<LiveScoresModel?>,
    private val adType: String,
    private val adManager: AdManager,
    private val nativeAdVal: NativeAd?
) :
    ListAdapter<LiveScoresModel, RecyclerView.ViewHolder>(
        LiveSliderAdapterDiffUtilCallback
    ) {

    private val nativeAdsLayout = 1
    private val simpleMenuLayout = 0
    private var binding2: NativeAdLayoutBinding? = null
    private var fbNativeAd: com.facebook.ads.NativeAd? = null

    class LiveSliderAdapterViewHolder(
        private var binding: RecentitemBinding,
        private var context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindMatchesData(liveScoresModel: LiveScoresModel, source: String) {

            binding.model = liveScoresModel
            val data = liveScoresModel
            binding.executePendingBindings()



            if (data.status != null) {
                binding.startTime.text = data.status
            }

            if (data.state.equals("Upcoming", true)) {
                binding.startTime.text = context.resources.getString(
                    R.string.venue, data.venue_info?.ground, data.venue_info?.city
                )

            }

            if (source.equals("main", true)) {

                binding.matchDateAndTime.visibility = View.GONE
            } else {
                binding.matchDateAndTime.visibility = View.VISIBLE

            }


            when (data.team_1_id) {
                4 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.australia)
                }

                2 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.india)
                }

                6 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.bangladesh)
                }

                5 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.srilanka)
                }

                13 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.newzealand)
                }

                9 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.england)
                }

                3 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.pakistan)
                }

                10 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.westindies)
                }

                96 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.afghanistan)
                }

                11 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.southafrica)
                }

                27 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.ireland)
                }

                23 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.scotland)
                }

                12 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.zimbabwe)
                }

                24 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.netherland)
                }

                72 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.nepal)
                }

                304 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.oman)
                }

                161 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.namibia)
                }

                15 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.us)
                }

                7 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.uae)
                }

                287 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.papuanew)
                }

                63 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.kolkta_knight_rider)
                }

                59 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.royal_challenger_bangolore)
                }

                64 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.rajistan_royals)
                }

                65 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.punjabkings)
                }

                61 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.delhi_capitals)
                }

                971 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.gujrat_titans)
                }

                58 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.chennai_super_kings)
                }

                26 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.canada)
                }

                255 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.sunrises_hyderabad)
                }

                62 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.mumbai_indians)
                }

                966 -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.lucknow_super_gaints)
                }

                else -> {
                    binding?.ivFirstTeam?.setImageResource(R.drawable.placehold)
                }
            }

            when (data.team_2_id) {
                4 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.australia)
                }

                2 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.india)
                }

                6 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.bangladesh)
                }

                5 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.srilanka)
                }

                13 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.newzealand)
                }

                9 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.england)
                }

                3 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.pakistan)
                }

                10 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.westindies)
                }

                96 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.afghanistan)
                }

                11 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.southafrica)
                }

                27 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.ireland)
                }

                23 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.scotland)
                }

                12 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.zimbabwe)
                }

                24 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.netherland)
                }

                72 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.nepal)
                }

                304 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.oman)
                }

                161 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.namibia)
                }

                15 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.us)
                }

                7 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.uae)
                }

                287 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.papuanew)
                }

                63 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.kolkta_knight_rider)
                }

                59 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.royal_challenger_bangolore)
                }

                64 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.rajistan_royals)
                }

                65 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.punjabkings)
                }

                61 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.delhi_capitals)
                }

                971 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.gujrat_titans)
                }

                58 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.chennai_super_kings)
                }

                26 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.canada)
                }

                255 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.sunrises_hyderabad)
                }

                62 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.mumbai_indians)
                }

                966 -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.lucknow_super_gaints)
                }

                else -> {
                    binding?.ivSecondTeam?.setImageResource(R.drawable.placehold)
                }
            }


            //////////////////////////////////////////////////////////////////////////////////////

            var wicketsTeam1In1 = ""
            var wicketsTeam1In2 = ""
            var wicketsTeam2In1 = ""
            var wicketsTeam2In2 = ""

            wicketsTeam1In1 = if (data.score_card?.team1Score?.inngs1?.wickets != null) {
                data.score_card?.team1Score?.inngs1?.wickets.toString()
            } else {
                "0"
            }
            wicketsTeam1In2 = if (data.score_card?.team1Score?.inngs2?.wickets != null) {
                data.score_card?.team1Score?.inngs2?.wickets.toString()
            } else {
                "0"
            }
            wicketsTeam2In1 = if (data.score_card?.team2Score?.inngs1?.wickets != null) {
                data.score_card?.team2Score?.inngs1?.wickets.toString()
            } else {
                "0"
            }
            wicketsTeam2In2 = if (data.score_card?.team2Score?.inngs2?.wickets != null) {
                data.score_card?.team2Score?.inngs2?.wickets.toString()
            } else {
                "0"
            }

            if (data.match_format.equals(Cons.match_format_test, true)) {
                var scoresTeam1 = ""
                var scoresTeam2 = ""
                var oversTeam1 = ""

                var oversTeam2 = ""


                if (data.score_card?.team1Score?.inngs1 != null) {
                    if (data.score_card?.team1Score?.inngs2 != null) {

                        scoresTeam1 = data.score_card?.team1Score?.inngs1?.runs.toString() + "/" +
                                wicketsTeam1In1 + " & " +
                                data.score_card?.team1Score?.inngs2?.runs.toString() + "/" +
                                wicketsTeam1In2

                        oversTeam1 = ""
                    } else {

                        scoresTeam1 = data.score_card?.team1Score?.inngs1?.runs.toString() + "/" +
                                wicketsTeam1In1
                        oversTeam1 =
                            "(" + data.score_card?.team1Score?.inngs1?.overs.toString() + " ov" + ")"
                    }
                    binding?.tvTeam1Score?.text = scoresTeam1
                    binding?.tvTeam1Over?.text = oversTeam1
                }
                if (data.score_card?.team2Score?.inngs1 != null) {
                    if (data.score_card?.team2Score?.inngs2 != null) {

                        scoresTeam2 = data.score_card?.team2Score?.inngs1?.runs.toString() + "/" +
                                wicketsTeam2In1 + " & " +
                                data.score_card?.team2Score?.inngs2?.runs.toString() + "/" +
                                wicketsTeam2In2

                        oversTeam2 = ""
                    } else {

                        scoresTeam2 = data.score_card?.team2Score?.inngs1?.runs.toString() + "/" +
                                wicketsTeam2In1
                        oversTeam2 =
                            "(" + data.score_card?.team2Score?.inngs1?.overs.toString() + " ov" + ")"
                    }
                    binding?.tvTeam2Score?.text = scoresTeam2
                    binding?.tvTeam2Over?.text = oversTeam2
                }
            } else {
                if (data.score_card?.team1Score?.inngs1 != null) {
                    binding?.tvTeam1Score?.text =
                        data.score_card?.team1Score?.inngs1?.runs.toString() + "/" +
                                wicketsTeam1In1
                    binding?.tvTeam1Over?.text = "(" +
                            data.score_card?.team1Score?.inngs1?.overs.toString() + " ov" + ")"
                }

                if (data.score_card?.team2Score?.inngs1 != null) {
                    binding?.tvTeam2Score?.text =
                        data.score_card?.team2Score?.inngs1?.runs.toString() + "/" +
                                wicketsTeam2In1
                    binding?.tvTeam2Over?.text = "(" +
                            data.score_card?.team2Score?.inngs1?.overs.toString() + " ov" + ")"
                }
            }

            //////////////////////////////////////////////////////////////////////////////////////

            if (data.start_time != null && data.end_time != null) {


                val valueShow = convertDateAndTime(data.start_time!!)
                dateAndTime(valueShow, data.start_time!!)

            }
        }

        private fun dateAndTime(channelDate: String?, startTime: Long) {
            val df = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH)
            val date = channelDate.let { it?.let { it1 -> df.parse(it1) } }
            val dayOfTheWeek =
                DateFormat.format("EEEE", date) as String
            val day = DateFormat.format("dd", date) as String
            val monthString =
                DateFormat.format("MMM", date) as String
            val year = DateFormat.format("yyyy", date) as String


            binding?.matchDateAndTime?.text =
                context.getString(R.string.dateAndTime, dayOfTheWeek, day, monthString, year)

            binding?.matchDateAndTime?.visibility = View.VISIBLE

            binding?.tvTime?.text = "$day $monthString . " + convertLongToTime(startTime)


        }

    }

    object LiveSliderAdapterDiffUtilCallback : DiffUtil.ItemCallback<LiveScoresModel>() {
        override fun areItemsTheSame(oldItem: LiveScoresModel, newItem: LiveScoresModel): Boolean {
            return oldItem.match_id == newItem.match_id
        }

        override fun areContentsTheSame(
            oldItem: LiveScoresModel,
            newItem: LiveScoresModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    ///Ads View holder class
    class NativeAdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        holder.setIsRecyclable(false)

        when (getItemViewType(position)) {
            nativeAdsLayout -> {
                ////For native ads if ads_provider provide native ads..
                val viewHolder: NativeAdViewHolder = holder as NativeAdViewHolder

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
                        fbNativeAd = com.facebook.ads.NativeAd(context, Constants.nativeFacebook)
                        binding2?.adLoadLay2?.visibility = View.VISIBLE
                        binding2?.fbNativeAdContainer?.let {
                            adManager.loadFacebookNativeAd(
                                fbNativeAd!!,
                                it, binding2?.adLoadLay2
                            )
                        }
                    }
                }else if (adType.equals(Constants.adManagerAds, true)) {
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
                }else if(adType.equals(cas_Ai, true)){
                    binding2?.casAdContainer?.let{
                        adManager.loadNativeAdCasAi(binding2?.adLoadLay3,it)
                    }
                }
            }

            else -> {
                val viewHolderMatch: LiveSliderAdapterViewHolder =
                    holder as LiveSliderAdapterViewHolder
                viewHolderMatch.bindMatchesData(currentList[position], source)
                val data = currentList[position]
                holder.itemView.setSafeOnClickListener {
                    Cons.matchId =currentList[position].match_id

                    if (!data.status.isNullOrEmpty()) {
                        if (source.equals("main", true)) {

                            val itemDirection = MainFragmentDirections.actionLiveToDetails(data)
                            navigateData.navigation(itemDirection)
                        } else if (source.equals("recent", true)) {

                            val itemDirection2 =
                                RecentFragmentDirections.actionRecentFragmentToLiveDetails(data)
                            navigateData.navigation(itemDirection2)
                        } else if (source.equals("seriesMatch", true)) {
                            if (!data.status.equals("Upcoming", true)) {
                                val itemDirection2 =
                                    SeriesMatchFragmentDirections.actionSeriesMatchFragmentToLiveDetails(
                                        data
                                    )
                                navigateData.navigation(itemDirection2)
                            }


                        }

                    }
                }

                ////////////
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        if (list[position] == null) {
            return nativeAdsLayout
        }
        return simpleMenuLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            nativeAdsLayout -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.native_ad_layout, parent, false)
                binding2 = DataBindingUtil.bind(view)
                NativeAdViewHolder(view)
            }

            else -> {
                val binding: RecentitemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(context), R.layout.recentitem, parent, false
                )
                LiveSliderAdapterViewHolder(binding, context)
            }
        }
    }

}