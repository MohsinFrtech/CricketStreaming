package com.dream.live.cricket.score.hd.scores.ui.fragments.matchdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.MatchDetailLayoutBinding
import com.dream.live.cricket.score.hd.scores.model.LiveScoresModel
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.facebook.ads.NativeAd

class MatchDetailFragment:Fragment() , AdManagerListener {
    var binding: MatchDetailLayoutBinding?=null
    private var liveScoresModel: LiveScoresModel? = null
    private var adManager: AdManager? = null
    private var fbNativeAd: NativeAd? = null
    companion object {
        fun newInstance(state: LiveScoresModel?): MatchDetailFragment {
            val matchesFragment = MatchDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable("Fragment_data", state)
            matchesFragment.arguments = bundle
            return matchesFragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.match_detail_layout,container,false)
        binding = DataBindingUtil.bind(layout)
        binding?.lifecycleOwner=this
        adManager = AdManager(requireContext(), requireActivity(), this)

        val bundle = this.arguments
        if (bundle != null) {
            val data = bundle.getParcelable<LiveScoresModel>("Fragment_data")
            binding?.model = data
            liveScoresModel = data
            setTeamImages(data)
            setScoresTextData()
            if (liveScoresModel?.start_time != null && liveScoresModel?.end_time != null) {
                binding?.tvDateTime?.text =
                    Cons.convertLongToTime(liveScoresModel?.start_time!!) + " - " +
                            Cons.convertLongToTime(liveScoresModel?.end_time!!)
            }

        }
         setNativeAd()
        return layout
    }

    //Setting Native Ad
    private fun setNativeAd() {
        if (Constants.checkNativeAdProvider.equals(Constants.admob, true)) {
            if (Cons.currentNativeAd != null) {
                binding?.nativeAdView?.let {
                    adManager?.populateNativeAdView(
                        Cons.currentNativeAd!!,
                        it
                    )
                }
            }
            else {
                binding?.adLoadLay?.visibility = View.VISIBLE
                binding?.nativeAdView?.let {
                    adManager?.loadAdmobNativeAd(
                        null,
                        it,
                        binding?.adLoadLay
                    )
                }
            }

        } else if (Constants.checkNativeAdProvider.equals(Constants.facebook, true)) {

            if (Cons.currentNativeAdFacebook != null) {
                binding?.fbNativeAdContainer?.let {
                    adManager?.inflateFbNativeAd(
                        Cons.currentNativeAdFacebook!!, it
                    )
                }
            }
            else {
                fbNativeAd = NativeAd(context, Constants.nativeFacebook)
                binding?.adLoadLay2?.visibility = View.VISIBLE
                binding?.fbNativeAdContainer?.let {
                    adManager?.loadFacebookNativeAd(
                        fbNativeAd!!,
                        it, binding?.adLoadLay2
                    )
                }
            }
        }
    }

    private fun setTeamImages(data: LiveScoresModel?) {
        when (data?.team_1_id) {
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

        when (data?.team_2_id) {
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
    }
    private fun setScoresTextData() {
        //////////////////////////////////////////////////////////////////////////////////////

        var wicketsTeam1In1 = ""
        var wicketsTeam1In2 = ""
        var wicketsTeam2In1 = ""
        var wicketsTeam2In2 = ""

        wicketsTeam1In1 = if (liveScoresModel?.score_card?.team1Score?.inngs1?.wickets != null) {
            liveScoresModel?.score_card?.team1Score?.inngs1?.wickets.toString()
        } else {
            "0"
        }
        wicketsTeam1In2 = if (liveScoresModel?.score_card?.team1Score?.inngs2?.wickets != null) {
            liveScoresModel?.score_card?.team1Score?.inngs2?.wickets.toString()
        } else {
            "0"
        }
        wicketsTeam2In1 = if (liveScoresModel?.score_card?.team2Score?.inngs1?.wickets != null) {
            liveScoresModel?.score_card?.team2Score?.inngs1?.wickets.toString()
        } else {
            "0"
        }
        wicketsTeam2In2 = if (liveScoresModel?.score_card?.team2Score?.inngs2?.wickets != null) {
            liveScoresModel?.score_card?.team2Score?.inngs2?.wickets.toString()
        } else {
            "0"
        }


        if (liveScoresModel?.match_format.equals(Cons.match_format_test, true)) {

            var scores_team1 = ""
            var scores_team2 = ""
            var overs_team1 = ""
            var overs_team2 = ""


            if (liveScoresModel?.score_card?.team1Score?.inngs1 != null) {
                if (liveScoresModel?.score_card?.team1Score?.inngs2 != null) {
                    scores_team1 =
                        liveScoresModel?.score_card?.team1Score?.inngs1?.runs.toString() + "/" +
                                wicketsTeam1In1 + " & " +
                                liveScoresModel?.score_card?.team1Score?.inngs2?.runs.toString() + "/" +
                                wicketsTeam1In2

                    overs_team1 = ""
                } else {
                    scores_team1 =
                        liveScoresModel?.score_card?.team1Score?.inngs1?.runs.toString() + "/" +
                                wicketsTeam1In1
                    overs_team1 = "(" +
                            liveScoresModel?.score_card?.team1Score?.inngs1?.overs.toString() + " ov)"
                }
                binding?.tvFirstScore?.text = scores_team1 + overs_team1
            }
            if (liveScoresModel?.score_card?.team2Score?.inngs1 != null) {
                if (liveScoresModel?.score_card?.team2Score?.inngs2 != null) {
                    scores_team2 =
                        liveScoresModel?.score_card?.team2Score?.inngs1?.runs.toString() + "/" +
                                wicketsTeam2In1 + " & " +
                                liveScoresModel?.score_card?.team2Score?.inngs2?.runs.toString() + "/" +
                                wicketsTeam2In2

                    overs_team2 = ""
                } else {
                    scores_team2 =
                        liveScoresModel?.score_card?.team2Score?.inngs1?.runs.toString() + "/" +
                                wicketsTeam2In1
                    overs_team2 = "(" +
                            liveScoresModel?.score_card?.team2Score?.inngs1?.overs.toString() + " ov)"
                }
                binding?.tvSecondScore?.text = scores_team2 + overs_team2
            }
        } else {
            if (liveScoresModel?.score_card?.team1Score?.inngs1 != null) {
                binding?.tvFirstScore?.text =
                    liveScoresModel?.score_card?.team1Score?.inngs1?.runs.toString() + "/" +
                            wicketsTeam1In1 + "(" +
                            liveScoresModel?.score_card?.team1Score?.inngs1?.overs.toString() + " ov)"
            }

            if (liveScoresModel?.score_card?.team2Score?.inngs1 != null) {
                binding?.tvSecondScore?.text =
                    liveScoresModel?.score_card?.team2Score?.inngs1?.runs.toString() + "/" +
                            wicketsTeam2In1 + "(" +
                            liveScoresModel?.score_card?.team2Score?.inngs1?.overs.toString() + " ov)"
            }
        }

        //////////////////////////////////////////////////////////////////////////////////////
    }

    override fun onAdLoad(value: String) {

    }

    override fun onAdFinish() {

    }

}