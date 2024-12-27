package com.dream.live.cricket.score.hd.scores.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.FragmentLiveDetailBinding
import com.dream.live.cricket.score.hd.scores.adapter.MatchDetailPagerAdapter
import com.dream.live.cricket.score.hd.scores.model.LiveScoresModel
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.scores.utility.listeners.ApiResponseListener
import com.dream.live.cricket.score.hd.scores.viewmodel.CommentaryViewModel
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.adLocation1Provider
import com.dream.live.cricket.score.hd.utils.Logger
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LiveScoreDetail : Fragment(),AdManagerListener, ApiResponseListener {

    private var binding: FragmentLiveDetailBinding? = null
    private val tags = "LiveScoreDetail"
    private val logger = Logger()
    private var adManager: AdManager?=null

    private var liveScoresModel: LiveScoresModel? = null
    private val commentaryViewModel by lazy {
        ViewModelProvider(this)[CommentaryViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_live_detail, container, false)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
        val liveData: LiveScoreDetailArgs by navArgs()
        liveScoresModel = liveData.getDetails
        binding?.model = liveScoresModel
        commentaryViewModel.apiResponseListener = this
        //load banner Ad
        adManager = AdManager(requireContext(), requireActivity(), this)
        binding?.MainLottie?.visibility = View.VISIBLE
//        if (adLocation1Provider != "none") {
//            binding?.adView?.let { it1 ->
//                binding?.fbAdView?.let { it2 ->
//                    binding?.startAppBanner?.let { it3 ->
//                        adManager?.loadAdProvider(
//                            adLocation1Provider, Constants.adLocation1,
//                            it1, it2, binding?.unityBannerView, it3
//                        )
//                    }
//                }
//            }
//        }


        binding?.backIcon?.setOnClickListener {
            findNavController().popBackStack()
        }
        setUpViewPager()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                setUpViewModel()
            },
            1000
        )
//        setScoresTextData()
//        setTeamImages()
        return view
    }

    private fun setUpViewPager() {
        val recentStatus = resources.getStringArray(R.array.matches_details)
        val fragmentAdapter = MatchDetailPagerAdapter(this, liveScoresModel)
        binding?.viewPagerRecent?.isUserInputEnabled = true
        binding?.tabsLiveDetail?.tabGravity = TabLayout.GRAVITY_FILL
        binding?.viewPagerRecent?.currentItem = 0
        binding?.viewPagerRecent?.adapter = fragmentAdapter
        binding?.tabsLiveDetail?.let {
            binding?.viewPagerRecent?.let { it1 ->
                TabLayoutMediator(
                    it, it1
                ) { tab: TabLayout.Tab, position: Int ->

                    tab.text = recentStatus[position]
                }.attach()
            }
        }


        binding?.tabsLiveDetail?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
//
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {


            }
        })
    }


    private fun setUpViewModel() {
        commentaryViewModel.loadCommentary()
        commentaryViewModel.loadScoreBoard()
        commentaryViewModel.getAllNewsList()
        commentaryViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding?.MainLottie?.visibility = View.VISIBLE
            } else {
                binding?.MainLottie?.visibility = View.GONE
            }
        })
    }


    override fun onAdLoad(value: String) {

    }

    override fun onAdFinish() {

    }

    override fun onStarted() {

    }

    override fun onSuccess() {

    }

    override fun onFailure(message: String) {
//        binding?.recentOvs?.visibility=View.GONE
//        binding?.liveMatchDetail?.visibility=View.GONE
//        binding?.recyclerViewCommentary?.visibility=View.GONE
//        binding?.ketStatResult?.visibility=View.GONE
//        binding?.playerOftheMatch?.visibility=View.GONE
//        binding?.playerOftheMatchName?.visibility=View.GONE
//        CustomDialogue(this).showDialog(
//            requireContext(), "title", message,
//            "", "Exit", "eventValue"
//        )
    }

    fun onPositiveDialogText(key: String) {

    }

    fun onNegativeDialogText(key: String) {

    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val view: View = inflater.inflate(R.layout.fragment_live_detail, container, false)
//        binding = DataBindingUtil.bind(view)
//
//
//        val liveData: LiveScoreDetailArgs by navArgs()
//        liveScoresModel = liveData.getDetails
//        binding?.lifecycleOwner = this
//        binding?.model = liveScoresModel
//        adManager= context?.let { activity?.let { it1 -> AdManager(it, it1,this) } }
//
//        logger.printLog(tags, "live details : $liveScoresModel")
//
//        if (liveScoresModel?.start_time != null && liveScoresModel?.end_time != null) {
//            binding?.tvDateTime?.text =
//                Cons.convertLongToTime(liveScoresModel?.start_time!!) + " - " +
//                        Cons.convertLongToTime(liveScoresModel?.end_time!!)
//        }
//
//
//
//        binding?.backIcon?.setOnClickListener {
//            findNavController().popBackStack()
//        }
//
//        setScoresTextData()
//        setTeamImages()
//        return view
//    }
//
//
//
//    private fun setTeamImages() {
//        when (liveScoresModel?.team_1_id) {
//            4 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.australia)
//            }
//            2 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.india)
//            }
//            6 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.bangladesh)
//            }
//            5 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.srilanka)
//            }
//            13 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.newzealand)
//            }
//            9 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.england)
//            }
//            3 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.pakistan)
//            }
//            10 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.westindies)
//            }
//            96 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.afghanistan)
//            }
//            11 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.southafrica)
//            }
//            27 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.ireland)
//            }
//            23 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.scotland)
//            }
//            12 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.zimbabwe)
//            }
//            24 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.netherland)
//            }
//            72 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.nepal)
//            }
//            304 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.oman)
//            }
//            161 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.namibia)
//            }
//            15 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.us)
//            }
//            7 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.uae)
//            }
//            287 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.papuanew)
//            }
//            63 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.kolkta_knight_rider)
//            }
//            59 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.royal_challenger_bangolore)
//            }
//            64 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.rajistan_royals)
//            }
//            65 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.punjabkings)
//            }
//            61 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.delhi_capitals)
//            }
//            971 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.gujrat_titans)
//            }
//            58 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.chennai_super_kings)
//            }
//            26 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.canada)
//            }
//            255 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.sunrises_hyderabad)
//            }
//            62 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.mumbai_indians)
//            }
//            966 -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.lucknow_super_gaints)
//            }
//            else -> {
//                binding?.ivFirstTeam?.setImageResource(R.drawable.placehold)
//            }
//        }
//
//        when (liveScoresModel?.team_2_id) {
//            4 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.australia)
//            }
//            2 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.india)
//            }
//            6 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.bangladesh)
//            }
//            5 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.srilanka)
//            }
//            13 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.newzealand)
//            }
//            9 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.england)
//            }
//            3 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.pakistan)
//            }
//            10 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.westindies)
//            }
//            96 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.afghanistan)
//            }
//            11 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.southafrica)
//            }
//            27 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.ireland)
//            }
//            23 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.scotland)
//            }
//            12 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.zimbabwe)
//            }
//            24 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.netherland)
//            }
//            72 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.nepal)
//            }
//            304 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.oman)
//            }
//            161 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.namibia)
//            }
//            15 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.us)
//            }
//            7 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.uae)
//            }
//            287 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.papuanew)
//            }
//            63 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.kolkta_knight_rider)
//            }
//            59 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.royal_challenger_bangolore)
//            }
//            64 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.rajistan_royals)
//            }
//            65 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.punjabkings)
//            }
//            61 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.delhi_capitals)
//            }
//            971 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.gujrat_titans)
//            }
//            58 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.chennai_super_kings)
//            }
//            26 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.canada)
//            }
//            255 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.sunrises_hyderabad)
//            }
//            62 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.mumbai_indians)
//            }
//            966 -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.lucknow_super_gaints)
//            }
//            else -> {
//                binding?.ivSecondTeam?.setImageResource(R.drawable.placehold)
//            }
//        }
//    }
//
//    private fun setScoresTextData() {
//        //////////////////////////////////////////////////////////////////////////////////////
//
//        var wicketsTeam1In1 = ""
//        var wicketsTeam1In2 = ""
//        var wicketsTeam2In1 = ""
//        var wicketsTeam2In2 = ""
//
//        wicketsTeam1In1 = if (liveScoresModel?.score_card?.team1Score?.inngs1?.wickets != null) {
//            liveScoresModel?.score_card?.team1Score?.inngs1?.wickets.toString()
//        } else {
//            "0"
//        }
//        wicketsTeam1In2 = if (liveScoresModel?.score_card?.team1Score?.inngs2?.wickets != null) {
//            liveScoresModel?.score_card?.team1Score?.inngs2?.wickets.toString()
//        } else {
//            "0"
//        }
//        wicketsTeam2In1 = if (liveScoresModel?.score_card?.team2Score?.inngs1?.wickets != null) {
//            liveScoresModel?.score_card?.team2Score?.inngs1?.wickets.toString()
//        } else {
//            "0"
//        }
//        wicketsTeam2In2 = if (liveScoresModel?.score_card?.team2Score?.inngs2?.wickets != null) {
//            liveScoresModel?.score_card?.team2Score?.inngs2?.wickets.toString()
//        } else {
//            "0"
//        }
//
//
//        if (liveScoresModel?.match_format.equals(Cons.match_format_test, true)) {
//
//            var scores_team1 = ""
//            var scores_team2 = ""
//            var overs_team1 = ""
//            var overs_team2 = ""
//
//
//            if (liveScoresModel?.score_card?.team1Score?.inngs1 != null) {
//                if (liveScoresModel?.score_card?.team1Score?.inngs2 != null) {
//                    scores_team1 =
//                        liveScoresModel?.score_card?.team1Score?.inngs1?.runs.toString() + "/" +
//                                wicketsTeam1In1 + " & " +
//                                liveScoresModel?.score_card?.team1Score?.inngs2?.runs.toString() + "/" +
//                                wicketsTeam1In2
//
//                    overs_team1 = ""
//                } else {
//                    scores_team1 =
//                        liveScoresModel?.score_card?.team1Score?.inngs1?.runs.toString() + "/" +
//                                wicketsTeam1In1
//                    overs_team1 = "(" +
//                            liveScoresModel?.score_card?.team1Score?.inngs1?.overs.toString() + " ov)"
//                }
//                binding?.tvFirstScore?.text = scores_team1 + overs_team1
//            }
//            if (liveScoresModel?.score_card?.team2Score?.inngs1 != null) {
//                if (liveScoresModel?.score_card?.team2Score?.inngs2 != null) {
//                    scores_team2 =
//                        liveScoresModel?.score_card?.team2Score?.inngs1?.runs.toString() + "/" +
//                                wicketsTeam2In1 + " & " +
//                                liveScoresModel?.score_card?.team2Score?.inngs2?.runs.toString() + "/" +
//                                wicketsTeam2In2
//
//                    overs_team2 = ""
//                } else {
//                    scores_team2 =
//                        liveScoresModel?.score_card?.team2Score?.inngs1?.runs.toString() + "/" +
//                                wicketsTeam2In1
//                    overs_team2 = "(" +
//                            liveScoresModel?.score_card?.team2Score?.inngs1?.overs.toString() + " ov)"
//                }
//                binding?.tvSecondScore?.text = scores_team2 + overs_team2
//            }
//        } else {
//            if (liveScoresModel?.score_card?.team1Score?.inngs1 != null) {
//                binding?.tvFirstScore?.text =
//                    liveScoresModel?.score_card?.team1Score?.inngs1?.runs.toString() + "/" +
//                            wicketsTeam1In1 + "(" +
//                            liveScoresModel?.score_card?.team1Score?.inngs1?.overs.toString() + " ov)"
//            }
//
//            if (liveScoresModel?.score_card?.team2Score?.inngs1 != null) {
//                binding?.tvSecondScore?.text =
//                    liveScoresModel?.score_card?.team2Score?.inngs1?.runs.toString() + "/" +
//                            wicketsTeam2In1 + "(" +
//                            liveScoresModel?.score_card?.team2Score?.inngs1?.overs.toString() + " ov)"
//            }
//        }
//
//        //////////////////////////////////////////////////////////////////////////////////////
//    }
//
//    override fun onAdLoad(value: String) {
//
//    }
//
//    override fun onAdFinish() {
//
//    }
}