package com.dream.live.cricket.score.hd.scores.ui.fragments.matchdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.SquadFragmentBinding
import com.dream.live.cricket.score.hd.scores.adapter.SquadAdapter
import com.dream.live.cricket.score.hd.scores.model.PlayerDetails
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.scores.viewmodel.CommentaryViewModel
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.facebook.ads.NativeAd

class SquadFragment:Fragment(), AdManagerListener {
    private val commentaryViewModel by lazy {
        ViewModelProvider(requireParentFragment())[CommentaryViewModel::class.java]
    }
    var binding: SquadFragmentBinding?=null
    private var adManager: AdManager? = null
    private var fbNativeAd: NativeAd? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.squad_fragment,container,false)
        binding = DataBindingUtil.bind(layout)
        binding?.lifecycleOwner=this
        adManager = AdManager(requireContext(), requireActivity(), this)
        getTeamSquadInfo()
        return layout
    }

    private fun getTeamSquadInfo() {
        commentaryViewModel.squadModel.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                if (it.matchInfo!=null){
                    if (it.matchInfo?.team1!=null){
                        if (!it.matchInfo!!.team1?.playerDetails.isNullOrEmpty()){
                            binding?.tvName?.text = it.matchInfo!!.team1?.name.toString()
                            binding?.team1Lay?.visibility=View.VISIBLE
                            binding?.squadNotAvailable?.visibility=View.GONE
                            setTeam1Adapter(it.matchInfo!!.team1?.playerDetails)
                        }
                        else
                        {
                            binding?.team1Lay?.visibility=View.GONE
                            binding?.squadNotAvailable?.visibility=View.VISIBLE
                        }
                    }
                    /////
                    if (it.matchInfo?.team2!=null){
                        if (!it.matchInfo!!.team2?.playerDetails.isNullOrEmpty()){
                            binding?.tvName2?.text = it.matchInfo!!.team2?.name?.toString()
                            binding?.team2Lay?.visibility=View.VISIBLE
                            binding?.squadNotAvailable?.visibility=View.GONE
                            setTeam2Adapter(it.matchInfo!!.team2?.playerDetails)
                        }
                        else
                        {
                            binding?.team2Lay?.visibility=View.GONE
                            binding?.squadNotAvailable?.visibility=View.VISIBLE
                        }
                    }
                }

            }
        })

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
        binding?.team1Lay?.setOnClickListener {
            if (binding!!.InTeamRecycler?.isVisible == true){
                binding?.InTeamRecycler?.visibility=View.GONE
                binding?.imageViewArrow?.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.arrow_right))
            }
            else
            {
                binding?.InTeamRecycler?.visibility=View.VISIBLE
                binding?.imageViewArrow?.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.arrow_down))
            }

        }
        binding?.team2Lay?.setOnClickListener {
            if (binding!!.InTeamRecycler2?.isVisible == true){
                binding?.InTeamRecycler2?.visibility=View.GONE
                binding?.imageViewArrow2?.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.arrow_right))
            }
            else
            {
                binding?.InTeamRecycler2?.visibility=View.VISIBLE
                binding?.imageViewArrow2?.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.arrow_down))
            }

        }


    }

    private fun setTeam1Adapter(it: ArrayList<PlayerDetails>?) {

        val listAdapter =
            SquadAdapter()
        binding?.InTeamRecycler?.layoutManager = LinearLayoutManager(requireContext())
        binding?.InTeamRecycler?.adapter = listAdapter
        listAdapter.submitList(it)
    }

    private fun setTeam2Adapter(it: ArrayList<PlayerDetails>?) {

        val listAdapter =
            SquadAdapter()
        binding?.InTeamRecycler2?.layoutManager = LinearLayoutManager(requireContext())
        binding?.InTeamRecycler2?.adapter = listAdapter
        listAdapter.submitList(it)
    }

    override fun onAdLoad(value: String) {

    }

    override fun onAdFinish() {

    }

}