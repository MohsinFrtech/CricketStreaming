package com.dream.live.cricket.score.hd.tv.fragments

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.MediaPlayerAdapter
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.widget.PlaybackControlsRow
import androidx.lifecycle.ViewModelProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.passphraseVal
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userLink
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userType2
import com.dream.live.cricket.score.hd.streaming.viewmodel.OneViewModel
import com.dream.live.cricket.score.hd.utils.Logger

/** Handles video playback with media controls. */
class PlaybackVideoFragment : VideoSupportFragment() {

    private lateinit var mTransportControlGlue: PlaybackTransportControlGlue<MediaPlayerAdapter>
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[OneViewModel::class.java]
    }
    private val tags = "PlaybackVideoFragment"
    private val logger = Logger()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            var videourl = activity?.intent?.getStringExtra("link_append")
            val channelType = activity?.intent?.getStringExtra("channel_type")

            val glueHost = VideoSupportFragmentGlueHost(this)
            val playerAdapter = MediaPlayerAdapter(activity)
            playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE)
            mTransportControlGlue = PlaybackTransportControlGlue(activity, playerAdapter)
            mTransportControlGlue.host = glueHost
            mTransportControlGlue.title = ""
            mTransportControlGlue.subtitle = ""
            mTransportControlGlue.playWhenPrepared()


            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)


            if (channelType.equals(userType2, true)) {
                viewModel.getDemoData()

                viewModel.userLinkStatus.observe(this) {
                    if (it == true && passphraseVal.isNotEmpty()) {
                        videourl = userLink
                        playerAdapter.setDataSource(Uri.parse(videourl.toString()))

                    }
                }
            } else if (channelType.equals(Constants.userType1, true)) {

                playerAdapter.setDataSource(Uri.parse(videourl.toString()))

            } else {
                //for p2p channel
                logger.printLog(tags, "setUpPlayer P2p")
//                val link = videourl.toString()
//                val parsedUrl = P2pEngine.instance?.parseStreamUrl(link)
//                playerAdapter.setDataSource(Uri.parse(parsedUrl))
            }


        } catch (e: Exception) {
            logger.printLog(tags, "Exception : ${e.localizedMessage}")
            logger.printLog(tags, "Exception : ${e.cause}")
        }

    }


    override fun onPause() {
        super.onPause()
        mTransportControlGlue.pause()
    }
}