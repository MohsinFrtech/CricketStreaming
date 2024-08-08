package com.dream.live.cricket.score.hd

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.dream.live.cricket.score.hd.databinding.FragmentEventBinding
import com.dream.live.cricket.score.hd.scores.adapter.LiveSliderAdapter
import com.dream.live.cricket.score.hd.scores.model.LiveScoresModel
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.scores.viewmodel.LiveViewModel
import com.dream.live.cricket.score.hd.streaming.adapters.ChannelAdapter
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.models.Channel
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.NavigateData
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.checkNativeAdProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.nativeAdProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.nativeFacebook
import com.dream.live.cricket.score.hd.streaming.viewmodel.OneViewModel
import com.dream.live.cricket.score.hd.utils.InternetUtil
import com.facebook.ads.NativeAd
import java.util.ArrayList

class MainFragment : Fragment(), NavigateData, AdManagerListener {


    var binding: FragmentEventBinding? = null
    private val modelEvent by lazy {
        activity?.let { ViewModelProvider(it)[OneViewModel::class.java] }
    }
    private var adProviderName = "none"
    private var nativeAdProviderName = "none"
    private var adManager: AdManager? = null
    private var liveChannelCount = 0
    private var navDirections: NavDirections? = null
    private var adStatus = false
    private val liveViewModel by lazy {
        ViewModelProvider(requireActivity())[LiveViewModel::class.java]
    }
    private var nativeFieldVal = ""
    private var fbNativeAd: NativeAd? = null

    private var listWithAd: List<Channel?> =
        ArrayList<Channel?>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
        binding?.model = modelEvent
        binding?.viewModel = liveViewModel

        adManager = activity?.let { AdManager(requireContext(), it, this) }
        return view
    }


    override fun onResume() {
        super.onResume()
        if (InternetUtil.isInternetOn(requireContext())) {

            setUpViewModel()
        }
    }

    private fun setUpViewModel() {
        // Observe Live Data for updating Data in slider
        liveViewModel.sliderList.observe(viewLifecycleOwner) {

            if (it != null) {
                setAdapter2(it)
            }
        }
        modelEvent?.dataModelList?.observe(viewLifecycleOwner) {

            adStatus = false
            if (it.live == true) {
                if (!it.extra_3.isNullOrEmpty()) {
                    nativeFieldVal = it.extra_3!!
                }
                if (!it.app_ads.isNullOrEmpty()) {
                    adProviderName =
                        adManager?.checkProvider(it.app_ads!!, Constants.adBefore).toString()
                    if (adProviderName.equals(Constants.startApp, true)) {
                        if (Constants.videoFinish) {
                            Constants.videoFinish = false
                            adManager?.loadAdProvider(
                                adProviderName,
                                Constants.adBefore, null, null, null, null
                            )

                        }
                    } else {
                        adManager?.loadAdProvider(
                            adProviderName,
                            Constants.adBefore, null, null, null, null
                        )

                    }

//                    adManager?.loadAdProvider(adProviderName,Constants.adBefore,binding?.adView,
//                    binding?.fbAdView,binding?.unityBannerView,binding?.startAppBanner)

                    nativeAdProviderName =
                        adManager?.checkProvider(it.app_ads!!, Constants.nativeAdLocation)
                            .toString()
                    nativeAdProvider = nativeAdProviderName
                    if(nativeAdProvider!="none")
                    {
                        adManager?.loadAdProvider(nativeAdProvider,Constants.nativeAdLocation,null,
                            null,null,null)
                    }
                    checkNativeAdProvider = nativeAdProviderName

                    if (nativeAdProviderName.equals(Constants.admob, true)) {
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

                    } else if (nativeAdProviderName.equals(Constants.facebook, true)) {

                        if (Cons.currentNativeAdFacebook != null) {
                            binding?.fbNativeAdContainer?.let {
                                adManager?.inflateFbNativeAd(
                                    Cons.currentNativeAdFacebook!!, it
                                )
                            }
                        }
                        else {
                            fbNativeAd = NativeAd(context, nativeFacebook)
                            binding?.adLoadLay2?.visibility = View.VISIBLE
                            binding?.fbNativeAdContainer?.let {
                                adManager?.loadFacebookNativeAd(
                                    fbNativeAd!!,
                                    it, binding?.adLoadLay2
                                )
                            }
                        }
                    }




                    Constants.location2TopProvider = adManager?.checkProvider(
                        it.app_ads!!,
                        Constants.adLocation2top
                    ).toString()
                    Constants.location2TopPermanentProvider = adManager?.checkProvider(
                        it.app_ads!!,
                        Constants.adLocation2topPermanent
                    ).toString()
                    Constants.location2BottomProvider = adManager?.checkProvider(
                        it.app_ads!!,
                        Constants.adLocation2bottom
                    ).toString()
                    Constants.locationAfter =
                        adManager?.checkProvider(it.app_ads!!, Constants.adAfter).toString()


                }


                if (!it.events.isNullOrEmpty()) {

                    liveChannelCount = 0
                    val liveAndImpChannels: MutableList<Channel> =
                        ArrayList<Channel>()
                    for (event in it.events!!) {
                        if (event.live == true) {

                            if (!event.channels.isNullOrEmpty()) {

                                for (channel in event.channels!!) {
                                    if (channel.live == true) {

                                        if (channel.important == true) {
                                            liveAndImpChannels.add(channel)

                                        }

                                    }
                                }

                            }
                        }
                    }
                    val channelRemoveDuplicate: MutableList<Channel> = ArrayList<Channel>()
                    if (liveAndImpChannels.isNotEmpty()) {
                        showLiveImpChannels()
                        for (element in liveAndImpChannels) {
                            // If this element is not present in newList
                            // then add it
                            if (!channelRemoveDuplicate.contains(element)) {
                                channelRemoveDuplicate.add(element)
                            }
                        }
                        binding?.gameRecycler?.visibility = View.VISIBLE
                        channelRemoveDuplicate.sortBy { it1 ->
                            it1.priority
                        }
                        setAdapter(channelRemoveDuplicate)
                    } else {
                        hideLiveImpChannels()
                    }

                } else {
                    hideLiveImpChannels()
                }
            } else {
                hideLiveImpChannels()
            }
        }
    }

    private fun showLiveImpChannels() {
        binding?.streamText?.visibility = View.VISIBLE
        binding?.gameRecycler?.visibility = View.VISIBLE
        binding?.noLiveChannel?.visibility = View.GONE
    }

    private fun hideLiveImpChannels() {
        binding?.streamText?.visibility = View.GONE
        binding?.gameRecycler?.visibility = View.GONE
        binding?.noLiveChannel?.visibility = View.VISIBLE
    }


    private fun setAdapter2(liveScores: List<LiveScoresModel?>) {
        try {

            var tFormatList: MutableList<LiveScoresModel?> = liveScores.toMutableList()
            if(!tFormatList.isNullOrEmpty()) {
                tFormatList.sortBy {
                    it?.id
                }
                val listAdapter = LiveSliderAdapter(requireContext(), this, "main")
                binding?.recyclerviewSlider?.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding?.recyclerviewSlider?.adapter = listAdapter
                listAdapter.submitList(tFormatList)

                ////////////////////////////////////////////////
                binding?.viewDots?.attachToRecyclerView(binding!!.recyclerviewSlider)
                if (binding?.recyclerviewSlider?.onFlingListener == null)
                    LinearSnapHelper().attachToRecyclerView(binding?.recyclerviewSlider)
                val animator: DefaultItemAnimator = object : DefaultItemAnimator() {
                    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
                        return true
                    }
                }
                binding?.recyclerviewSlider?.itemAnimator = animator
            }

//            tFormatList.sortBy {
//                it?.id
//            }
//            binding?.progressBar?.visibility=View.GONE

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setAdapter(liveChannels: MutableList<Channel>) {
        listWithAd = if (nativeAdProviderName.equals(Constants.admob, true)) {
            checkNativeAd(liveChannels)
        } else if (nativeAdProviderName.equals(Constants.facebook, true)) {
            checkNativeAd(liveChannels)
        } else {
            liveChannels
        }
        val adapter = context?.let {
            adManager?.let { it1 ->
                ChannelAdapter(
                    it, this, listWithAd, nativeAdProviderName,
                    it1, nativeFieldVal, "main"
                )
            }
        }
        binding?.gameRecycler?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding?.gameRecycler?.adapter = adapter
        adapter?.submitList(listWithAd)


        binding?.gameRecycler?.let { binding?.viewDots2?.attachToRecyclerView(it) }
        if (binding?.gameRecycler?.onFlingListener == null)
            LinearSnapHelper().attachToRecyclerView(binding?.gameRecycler)
        val animator: DefaultItemAnimator = object : DefaultItemAnimator() {
            override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
                return true
            }
        }
        binding?.gameRecycler?.itemAnimator = animator

    }


    ////Function to return list of events with empty positions.....
    private fun checkNativeAd(list: List<Channel>): List<Channel?> {
        val tempChannels: MutableList<Channel?> =
            ArrayList()
        for (i in list.indices) {
            if (list[i].live!!) {
                val diff = i % 5
                if (diff == 2) {

                    tempChannels.add(null)
                }
                tempChannels.add(list[i])
                if (list.size == 2) {
                    if (i == 1) {
                        tempChannels.add(null)

                    }
                }
            }
        }
        return tempChannels
    }

    override fun navigation(viewId: NavDirections) {
        try {
            navDirections = viewId
            if (adStatus) {
                if (!adProviderName.equals("none", true)) {
                    binding?.MainLottie?.visibility=View.VISIBLE
                    adManager?.showAds(adProviderName)
                } else {
                    findNavController().navigate(viewId)
                }
            } else {
                findNavController().navigate(viewId)
            }
        } catch (e: Exception) {
            Log.d("Exception", "message")
        }
    }

    override fun onAdLoad(value: String) {
        adStatus = value.equals("success", true)
    }

    override fun onAdFinish() {
        if(binding?.MainLottie?.isVisible == true){
            binding?.MainLottie?.visibility=View.GONE
        }
        if (navDirections != null) {
            findNavController().navigate(navDirections!!)
        }
    }


}