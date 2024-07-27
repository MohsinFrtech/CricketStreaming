package com.dream.live.cricket.score.hd.tv.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.ViewModelProvider
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.date.ProcessingFile
import com.dream.live.cricket.score.hd.streaming.models.Category
import com.dream.live.cricket.score.hd.streaming.models.Channel
import com.dream.live.cricket.score.hd.streaming.models.Event
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.adAfter
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.adBefore
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.locationAfter
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userType2
import com.dream.live.cricket.score.hd.streaming.utils.objects.Defamation
import com.dream.live.cricket.score.hd.streaming.viewmodel.OneViewModel
import com.dream.live.cricket.score.hd.tv.activities.TvPlayScreen
import com.dream.live.cricket.score.hd.tv.presenters.ChannelPresenter

class TvChannelFragment : RowsSupportFragment(), AdManagerListener {

    private lateinit var mBackgroundManager: BackgroundManager
    private lateinit var mMetrics: DisplayMetrics
    private var channelUrl: String? = null
    private var channelType: String? = null
    private var channelList: MutableList<Channel> = ArrayList()
    private var adManager: AdManager? = null
    private var adProviderName = "none"
    private var adStatus = false


    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[OneViewModel::class.java]
    }

    private var localVal: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prepareBackgroundManager()
        setupUIElements()

    }

    override fun onResume() {
        super.onResume()
        gettingNavArgument()
    }


    ///Getting nav arguments....
    private fun gettingNavArgument() {
        channelList.clear()

        val destinationName = requireActivity().intent?.getStringExtra("desti")
        val clickName = requireActivity().intent?.getStringExtra("name")
        if (destinationName != null) {

            viewModel.dataModelList.observe(viewLifecycleOwner) {
                if (it != null) {
                    localVal = it.extra_3
                    if (destinationName.equals("Event", true)) {
                        if (!it.events.isNullOrEmpty()) {

                            var channelData: Event? = null
                            for (event in it.events!!) {
                                if (event.name.equals(clickName, true)) {
                                    channelData = event
                                }
                            }

                            if (channelData != null) {
                                if (!channelData.channels.isNullOrEmpty()) {


                                    for (channel in channelData.channels!!) {
                                        if (channel.live!!) {
                                            channelList.add(channel)
                                        }
                                    }

                                    if (channelList.isNotEmpty()) {

                                        ////Sorting list of channels.....
                                        channelList.sortBy { it1 ->
                                            it1.priority
                                        }

                                        settingUpChannels(channelList)
                                    }

                                }
                            }
                        }

                    } else {
                        if (!it.categories.isNullOrEmpty()) {

                            var channelData: Category? = null
                            for (category in it.categories!!) {
                                if (category.name.equals(clickName, true)) {
                                    channelData = category
                                }
                            }

                            if (channelData != null) {
                                if (!channelData.channels.isNullOrEmpty()) {


                                    for (channel in channelData.channels!!) {
                                        if (channel.live!!) {
                                            channelList.add(channel)
                                        }
                                    }

                                    if (channelList.isNotEmpty()) {

                                        ////Sorting list of channels.....
                                        channelList.sortBy { it1 ->
                                            it1.priority
                                        }

                                        settingUpChannels(channelList)
                                    }
                                }
                            }
                        }
                    }

                    if (!it.app_ads.isNullOrEmpty()) {
                        adProviderName =
                            adManager?.checkProvider(it.app_ads!!, adBefore).toString()
                        locationAfter =
                            adManager?.checkProvider(it.app_ads!!, adAfter).toString()
                        adManager?.loadAdProvider(
                            adProviderName, adBefore,
                            null,
                            null,
                            null,
                            null
                        )
                    }
                }
            }
        }
    }


    private fun settingUpChannels(channelList: MutableList<Channel>) {
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())

        val listRowAdapter = ArrayObjectAdapter(ChannelPresenter(requireContext()))
        for (j in 0 until channelList.size) {
            listRowAdapter.add(channelList[j])
        }

        val header = HeaderItem(0, "Channels")
        rowsAdapter.add(ListRow(header, listRowAdapter))
        adapter = rowsAdapter

        onItemViewClickedListener = getDefaultItemViewClickedListener()
        onItemViewSelectedListener = getDefaultItemSelectedListener()
    }

    private fun getDefaultItemViewClickedListener(): OnItemViewClickedListener {
        return OnItemViewClickedListener { _, _, _, _ ->

            if (adStatus) {
                if (!adProviderName.equals("none", true)) {
                    adManager?.showAds(adProviderName)
                } else {
                    intentUser()
                }
            } else {
                intentUser()
            }
        }
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

    private fun getDefaultItemSelectedListener(): OnItemViewSelectedListener {
        return OnItemViewSelectedListener { _, item, _, _ ->
            if (item is Channel) {
                channelUrl = item.url

                channelType = item.channel_type

            }
        }
    }


    private fun prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(requireActivity())
        mBackgroundManager?.attach(requireActivity().window)
        mMetrics = DisplayMetrics()
        mBackgroundManager?.color = Color.parseColor("#B31E2D3F")
    }


    private fun setupUIElements() {
//        val colorText = (""
//                + "<font color=\"#FFFFFF\"><bold>"
//                + requireContext().getString(R.string.app_name)
//                + "</bold></font>")
//        title = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Html.fromHtml(colorText, Html.FROM_HTML_MODE_LEGACY)
//        } else {
//            requireContext().packageName
//        }
//        // over title
//        headersState = HEADERS_ENABLED
//        isHeadersTransitionOnBackEnabled = true
//
//        // set fastLane (or headers) background color
//        brandColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)


        // set search icon color
    }

    override fun onAdLoad(value: String) {
        adStatus = value.equals("success", true)

    }

    override fun onAdFinish() {
        intentUser()
    }

}