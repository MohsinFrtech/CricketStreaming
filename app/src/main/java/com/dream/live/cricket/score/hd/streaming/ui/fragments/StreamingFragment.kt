package com.dream.live.cricket.score.hd.streaming.ui.fragments

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
import androidx.recyclerview.widget.GridLayoutManager
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.StreaminglayBinding
import com.dream.live.cricket.score.hd.streaming.adapters.EventAdapter
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.adsData.NewAdManager
import com.dream.live.cricket.score.hd.streaming.models.Event
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.NavigateData
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.currentCountryCode
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.middleAdProvider
import com.dream.live.cricket.score.hd.streaming.viewmodel.OneViewModel
import com.teamd2.live.football.tv.utils.AppContextProvider
import java.util.ArrayList

class StreamingFragment:Fragment(), NavigateData, AdManagerListener {

    var binding: StreaminglayBinding?=null
    private val modelEvent by lazy {
        activity?.let { ViewModelProvider(it)[OneViewModel::class.java] }
    }
    private var nativeAdProviderName="none"
    private var adManager: AdManager?=null
    private var liveChannelCount=0
    private var navDirections: NavDirections?=null
    private var adStatus=false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.streaminglay,container,false)
        binding= DataBindingUtil.bind(view)
        binding?.lifecycleOwner=this
        binding?.model=modelEvent
        adManager= context?.let { activity?.let { it1 -> AdManager(it, it1,this) } }
        NewAdManager.setAdManager(this)
        setUpViewModel()
        return view
    }


    override fun onResume() {
        super.onResume()
        NewAdManager.setAdManager(this)

        Constants.positionClick=-1
        Constants.previousClick=-1
    }
    private fun setUpViewModel() {


        modelEvent?.dataModelList?.observe(viewLifecycleOwner) {
            if (it.live==true)
            {
                binding?.noEventText?.visibility=View.GONE

                if (!it.events.isNullOrEmpty()) {

                    liveChannelCount=0


                    val liveEvents: MutableList<Event> =
                        ArrayList<Event>()

                    for (event in it.events!!) {
                        var event_belongs_country = false
                        if (event.live == true) {
                            if (!event.country_codes.isNullOrEmpty()){
                                event.country_codes!!.forEach {
                                    code->
                                    Log.d("eventCountryCode", code)
                                    if (code?.equals(currentCountryCode, true) == true){
                                        event_belongs_country = true
                                    }
                                }
                                if (event_belongs_country){
                                    if (!event.channels.isNullOrEmpty()) {
                                        for (channel in event.channels!!)
                                        {
                                            if (channel.live == true)
                                            {
                                                liveChannelCount++
                                            }
                                        }
                                        if (liveChannelCount>0)
                                        {
                                            liveEvents.add(event)
                                        }
                                    }
                                }
                            }else{
                                if (!event.channels.isNullOrEmpty()) {
                                    for (channel in event.channels!!)
                                    {
                                        if (channel.live == true)
                                        {
                                            liveChannelCount++
                                        }
                                    }
                                    if (liveChannelCount>0)
                                    {
                                        liveEvents.add(event)
                                    }
                                }
                            }
                        }
                    }

                    if (liveEvents.isNotEmpty())
                    {
                        binding?.eventRecycler?.visibility=View.VISIBLE
                        binding?.noEvent?.visibility=View.GONE
                        binding?.noEventText?.visibility=View.GONE
//
                        liveEvents.sortBy { it1 ->
                            it1.priority
                        }
                        setAdapter(liveEvents)
                    }
                    else
                    {
                        binding?.eventRecycler?.visibility=View.GONE
                        binding?.noEvent?.visibility=View.VISIBLE
                        binding?.noEventText?.visibility=View.VISIBLE

                        //if event list is empty....
                    }

                }
                else
                {
                    binding?.eventRecycler?.visibility=View.GONE
                    binding?.noEvent?.visibility=View.VISIBLE
                    binding?.noEventText?.visibility=View.VISIBLE
                    ///if event list is empty from backend...
                }
            }
            else
            {
                binding?.eventRecycler?.visibility=View.GONE
                binding?.noEventText?.visibility=View.VISIBLE

            }
        }
    }


    private fun setAdapter(liveEvents: MutableList<Event>) {

        val listAdapter = context?.let { adManager?.let { it1 ->
            EventAdapter(it, this,liveEvents,nativeAdProviderName,
                it1
            )
        } }
        binding?.eventRecycler?.layoutManager = GridLayoutManager(context,2)
        binding?.eventRecycler?.adapter = listAdapter
        listAdapter?.submitList(liveEvents)

    }

    override fun navigation(viewId: NavDirections) {
        try {
            Constants.positionClick=-1
            Constants.previousClick=-1
            navDirections=viewId
            if (!middleAdProvider.equals("none", true)) {
                if (!middleAdProvider.equals(Constants.startApp,true)) {
                    binding?.MainLottie?.visibility = View.VISIBLE
                    val local = AppContextProvider.getContext()
                    local?.let { NewAdManager.showAds(middleAdProvider, requireActivity(), it) }
                }
                else{
                    findNavController().navigate(viewId)
                }
            }
            else{
                findNavController().navigate(viewId)
            }
        }
        catch (e:Exception)
        {
            Log.d("Exception","message")
        }
    }

    override fun onAdLoad(value: String) {
        adStatus = value.equals("success", true)
    }

    override fun onAdFinish() {
        if(binding?.MainLottie?.isVisible == true){
            binding?.MainLottie?.visibility=View.GONE
        }
        if (navDirections!=null)
        {
            findNavController().navigate(navDirections!!)
        }
    }
}