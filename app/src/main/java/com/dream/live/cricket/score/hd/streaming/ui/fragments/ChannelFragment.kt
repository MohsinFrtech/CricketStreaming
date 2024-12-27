package com.dream.live.cricket.score.hd.streaming.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.FragmentChannelsBinding
import com.dream.live.cricket.score.hd.streaming.adapters.ChannelAdapter
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.adsData.NewAdManager
import com.dream.live.cricket.score.hd.streaming.models.Channel
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.NavigateData
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.locationAfter
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.playerActivityInPip
import com.dream.live.cricket.score.hd.streaming.viewmodel.OneViewModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.teamd2.live.football.tv.utils.AppContextProvider
import java.util.ArrayList

class ChannelFragment : Fragment(), NavigateData, AdManagerListener {
    private var bindingChannel: FragmentChannelsBinding? = null
    private var adManager: AdManager? = null
    private var adStatus = false
    private var navDirections: NavDirections? = null
    private var nativeFieldVal = ""
    private var listWithAd: List<Channel>? =
        ArrayList<Channel>()
    private val viewModel by lazy {
        activity?.let { ViewModelProvider(it)[OneViewModel::class.java] }
    }
    private var currentNativeAd: com.google.android.gms.ads.nativead.NativeAd? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_channels, container, false)
        bindingChannel = DataBindingUtil.bind(view)
        bindingChannel?.lifecycleOwner = this
        adManager = context?.let { activity?.let { it1 -> AdManager(it, it1, this) } }
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        if (Constants.middleAdProvider.equals(Constants.startApp, true)) {
            Log.d("providerval", "mid" + Constants.middleAdProvider)

            adManager?.loadAdProvider(
                Constants.middleAdProvider,
                Constants.adMiddle,
                null,
                null,
                null,
                null
            )

        }
        settingChannels()
        bindingChannel?.btnBack?.setOnClickListener {

            this.findNavController().popBackStack()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        NewAdManager.setAdManager(this)

        viewModel?.dataModelList?.observe(viewLifecycleOwner)
        {

            if (!it.extra_3.isNullOrEmpty()) {
                nativeFieldVal = it.extra_3!!
            }

            if (!it.app_ads.isNullOrEmpty()) {

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

                if (locationAfter.equals(Constants.startApp, true)) {
                    if (Constants.videoFinish) {
                        Constants.videoFinish = false
                        adManager?.loadAdProvider(locationAfter, locationAfter, null, null, null, null)
                    }
                }
            }

        }
    }

    ///set channels
    private fun settingChannels() {
        val channelData: ChannelFragmentArgs by navArgs()
        if (channelData.getEvent != null) {
            bindingChannel?.eventNameChannel?.text = channelData.getEvent!!.name
            setChannelAdapter(channelData.getEvent?.channels,currentNativeAd)

        }
    }

    private fun setChannelAdapter(channels: List<Channel>?, currentNativeAd: NativeAd?) {

        viewModel?.dataModelList?.observe(viewLifecycleOwner)
        {

            if (!it.extra_3.isNullOrEmpty()) {
                nativeFieldVal = it.extra_3!!
            }
            val liveChannels: MutableList<Channel> =
                ArrayList<Channel>()
            for (channel in channels!!) {
                if (channel.live == true) {
                    liveChannels.add(channel)
                }

            }

            if (liveChannels.isNotEmpty()) {
                bindingChannel?.noChannelIcon?.visibility = View.GONE
                bindingChannel?.noChannelText?.visibility = View.GONE
                liveChannels.sortBy { it1 ->
                    it1.priority
                }


                val listWithAd: List<Channel?> =
                    if (Constants.checkNativeAdProvider != "none") {
                        checkNativeAd(liveChannels)
                    } else {
                        liveChannels
                    }

                val adapter = context?.let {
                    adManager?.let { it1 ->
                        ChannelAdapter(
                            it, this, listWithAd, Constants.nativeAdProvider,
                            it1, nativeFieldVal, "channel")
                    }
                }
                bindingChannel?.channelRecycler?.layoutManager = LinearLayoutManager(context)
                bindingChannel?.channelRecycler?.adapter = adapter
                adapter?.submitList(listWithAd)
            } else {
                bindingChannel?.noChannelIcon?.visibility = View.VISIBLE
                bindingChannel?.noChannelText?.visibility = View.VISIBLE

            }
        }


    }

    override fun navigation(viewId: NavDirections) {
        try {
            navDirections = viewId
            if (!Constants.locationBeforeProvider.equals("none", true)) {
                if (!Constants.locationBeforeProvider.equals(Constants.startApp,true)){
                    if (!Constants.locationBeforeProvider.equals(Constants.unity,true))
                    {
                        bindingChannel?.MainLottie?.visibility = View.VISIBLE
                        val local = AppContextProvider.getContext()
                        local?.let {
                            NewAdManager.showAds(
                                Constants.locationBeforeProvider,
                                requireActivity(),
                                it
                            )
                        }
                    }
                    else{
                        if (playerActivityInPip){
                            findNavController().navigate(viewId)
                        }
                        else{
                            bindingChannel?.MainLottie?.visibility = View.VISIBLE
                            val local = AppContextProvider.getContext()
                            local?.let {
                                NewAdManager.showAds(
                                    Constants.locationBeforeProvider,
                                    requireActivity(),
                                    it
                                )
                            }
                        }

                    }
//                    requireActivity().window.setFlags(
//                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//                    )
                }
                else{
                    findNavController().navigate(viewId)
                }

            } else {
//                    requireActivity().getWindow()
//                        .clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                findNavController().navigate(viewId)
            }
        } catch (e: Exception) {
            Log.d("Exception", "message")
        }


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

    override fun onAdLoad(value: String) {
        adStatus = value.equals("success", true)

    }

    override fun onAdFinish() {
        if (bindingChannel?.MainLottie?.isVisible == true){
            bindingChannel?.MainLottie?.visibility=View.GONE
        }
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        if (navDirections != null) {
            findNavController().navigate(navDirections!!)
        }
    }


}