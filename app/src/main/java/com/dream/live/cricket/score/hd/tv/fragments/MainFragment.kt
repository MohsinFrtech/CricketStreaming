package com.dream.live.cricket.score.hd.tv.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.ViewModelProvider
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.date.ProcessingFile
import com.dream.live.cricket.score.hd.streaming.models.Channel
import com.dream.live.cricket.score.hd.streaming.models.Event
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Defamation
import com.dream.live.cricket.score.hd.streaming.viewmodel.OneViewModel
import com.dream.live.cricket.score.hd.tv.activities.TvChannelActivity
import com.dream.live.cricket.score.hd.tv.activities.TvPlayScreen
import com.dream.live.cricket.score.hd.tv.presenters.ChannelPresenter
import com.dream.live.cricket.score.hd.tv.presenters.EventPresenter
import java.util.*

class MainFragment : RowsSupportFragment(), AdManagerListener {
    private var mBackgroundManager: BackgroundManager? = null
    private lateinit var mMetrics: DisplayMetrics
    private var adLoaded: Boolean = false
    private var clickWhere: String = ""
    private var localVal = ""
    private var rowsAdapter: ArrayObjectAdapter? = null
    private var clickName: String = ""
    private var adProviderName = "none"
    private var adProviderMiddleName = "none"
    private var adStatus = false

    private var channelUrl: String? = null
    private var channelType: String = ""
    private var managerclassforads: AdManager? = null
    private val modelEvent by lazy {
        ViewModelProvider(requireActivity())[OneViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        managerclassforads =
            AdManager(
                requireContext(),
                requireActivity(), this
            )
        prepareBackgroundManager()
    }


    private fun prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(requireActivity())
        mBackgroundManager?.attach(requireActivity().window)
        mMetrics = DisplayMetrics()
        mBackgroundManager?.color = Color.parseColor("#B31E2D3F")
    }

    override fun onResume() {
        super.onResume()
        loadRows()
    }

    private fun loadRows() {
        // Setup this browser fragment's adapter
        adapter = ArrayObjectAdapter(ListRowPresenter())
        modelEvent?.dataModelList?.observe(viewLifecycleOwner) {
            if(it.live==true){

               if (!it.app_ads.isNullOrEmpty()){
                   adProviderMiddleName =
                       managerclassforads?.checkProvider(it.app_ads!!, Constants.adMiddle).toString()
                   managerclassforads?.loadAdProvider(adProviderMiddleName, Constants.adMiddle, null, null, null,null)

                   adProviderName =
                       managerclassforads?.checkProvider(it.app_ads!!, Constants.adBefore).toString()
                   managerclassforads?.loadAdProvider(adProviderName, Constants.adBefore, null, null, null,null)
               }

                if (it.extra_3 != null) {
                    localVal = it.extra_3.toString()
                }
                ////Checking if event list is not empty...
                if (!it.events.isNullOrEmpty()) {
                    val listRow = ListRowPresenter()
                    rowsAdapter = ArrayObjectAdapter(listRow)
                    val eventsList: MutableList<Event> =
                        ArrayList<Event>()


                    //Iteration through list of events......
                    for (i in it.events!!) {
                        if (i.live!!) {
                            if (!i.channels.isNullOrEmpty()) {
                                eventsList.add(i)
                            }
                        }
                    }


                    eventsList.sortBy { it1 ->
                        it1.priority
                    }
                    settRendingGameData(eventsList, rowsAdapter!!)
                }
            }
        }
    }


    private fun settRendingGameData(
        eventsList: MutableList<Event>,
        rowsAdapter: ArrayObjectAdapter
    ) {

        val liveChannelList: MutableList<Channel> = mutableListOf()
        if (eventsList.isNotEmpty()) {

            for (event in eventsList) {
                if (!event.channels.isNullOrEmpty()) {
                    for (channel in event.channels!!) {
                        if (channel.live == true) {

                            if (channel.important == true) {
                                liveChannelList.add(channel)
                            }
                        }


                    }
                }

            }

            val channelRemoveDuplicate =
                liveChannelList.distinctBy { it1 -> it1.name }.toMutableList()

            channelRemoveDuplicate.sortBy { it1 ->
                it1.priority
            }



            if (channelRemoveDuplicate.isNotEmpty()) {
                val headerItem = HeaderItem(0, "Live Streaming")
                val presenterAdapter = ArrayObjectAdapter(ChannelPresenter(requireContext()))
                for (tvChannel in 0 until channelRemoveDuplicate.size) {
                    presenterAdapter.add(channelRemoveDuplicate[tvChannel])

                }
                rowsAdapter.add(ListRow(headerItem, presenterAdapter))

            }

//
            val eventHeader = HeaderItem(1, "Events")
            val presenterEvent = ArrayObjectAdapter(EventPresenter(requireContext()))
            for (j in 0 until eventsList.size) {
                presenterEvent.add(eventsList[j])
            }
            rowsAdapter.add(ListRow(eventHeader, presenterEvent))

        }
        adapter = rowsAdapter
        onItemViewSelectedListener = getDefaultItemSelectedListener()
        onItemViewClickedListener = getDefaultItemViewClickedListener()
    }


    override fun setExpand(expand: Boolean) {
        super.setExpand(expand)
    }

    private fun getDefaultItemViewClickedListener(): OnItemViewClickedListener {
        return OnItemViewClickedListener { _, item, _, _ ->
            if (item is Event) {
                clickWhere = "Event"
                clickName = item.name.toString()
                if (adStatus) {
                    if (!adProviderMiddleName.equals("none", true)) {
                        managerclassforads?.showAds(adProviderMiddleName)
                    } else {
                        moveToChannelScreen()
                    }
                } else {
                    moveToChannelScreen()
                }

            }

            if (item is Channel) {
                clickWhere = "channel"
                clickName = item.name.toString()
                channelType = item.channel_type.toString()
                channelUrl = item.url
                if (adStatus) {
                    if (!adProviderName.equals("none", true)) {
                        managerclassforads?.showAds(adProviderName)
                    } else {
                        intentUser()
                    }
                } else {
                    intentUser()
                }


            }

        }
    }

    private fun moveToChannelScreen() {
        val intent = Intent(requireContext(), TvChannelActivity::class.java)
        intent.putExtra("desti", clickWhere)
        intent.putExtra("name", clickName)
        startActivity(intent)
    }

    private fun intentUser() {
        if (channelType.equals(
                Constants.userType2,
                true
            )
        ) {
            val intent = Intent(context, TvPlayScreen::class.java)
            intent.putExtra("link_append", "linkAppend")
            intent.putExtra("channel_type", Constants.userType2)
            startActivity(intent)


        } else {
            if (localVal?.isNotEmpty() == true) {

                val processingFile = ProcessingFile()
                Constants.defaultString = processingFile.getChannelType(localVal)

                val token = channelUrl?.let { it1 -> Defamation.improveDeprecatedCode(it1) }
                val linkAppend = channelUrl + token
                // select channel for p2p
                val userType = if (channelType.equals(
                        Constants.userType1, true
                    )
                ) {
                    Constants.userType1
                } else {
                    Constants.userType3
                }

                val intent = Intent(context, TvPlayScreen::class.java)
                intent.putExtra("link_append", linkAppend)
                intent.putExtra("channel_type", userType)
                startActivity(intent)
            }
        }
    }


    private fun getDefaultItemSelectedListener(): OnItemViewSelectedListener? {
        return OnItemViewSelectedListener { _, item, _, row ->
            if (item is Event) {
                clickWhere = "Event"
                clickName = item.name.toString()
            }


            if (item is Channel) {
                clickWhere = "channel"
                clickName = item.name.toString()
                channelType = item.channel_type.toString()
                channelUrl = item.url
            }
        }
    }


    override fun onAdLoad(value: String) {
        adStatus = value.equals("success", true)

    }

    override fun onAdFinish() {

        if (clickWhere.equals("channel", true)) {
            intentUser()
        } else if (clickWhere.equals("Event", true)) {
            moveToChannelScreen()
        }
    }
}


