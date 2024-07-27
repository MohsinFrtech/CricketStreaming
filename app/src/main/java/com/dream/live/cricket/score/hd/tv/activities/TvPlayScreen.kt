package com.dream.live.cricket.score.hd.tv.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.adAfter
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.locationAfter
import com.dream.live.cricket.score.hd.tv.fragments.PlaybackVideoFragment
import com.dream.live.cricket.score.hd.utils.Logger

class TvPlayScreen : FragmentActivity(), AdManagerListener {

    private var adStatus = false
    private val tags = "TvPlayScreen"
    private val logger = Logger()
    private var adManager: AdManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, PlaybackVideoFragment())
                .commit()
        }


        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                try {
                    if (adStatus) {
                        if (!locationAfter.equals("none", true)) {
                            adManager?.showAds(locationAfter)
                        }
                    } else {
                        finish()
                    }
                } catch (e: Exception) {
                    logger.printLog(tags, "Exception : ${e.cause}")
                    logger.printLog(tags, "Exception : ${e.localizedMessage}")
                }
            }
        })

        checkForAds()

    }

    private fun checkForAds() {
        adManager = AdManager(this, this, this)

        adManager?.loadAdProvider(
            locationAfter, adAfter,
            null, null, null,null
        )
    }

    override fun onAdLoad(value: String) {
        adStatus = value.equals("success", true)
    }

    override fun onAdFinish() {
        finish()
    }

}