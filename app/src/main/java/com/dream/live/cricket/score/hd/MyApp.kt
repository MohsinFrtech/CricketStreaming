package com.dream.live.cricket.score.hd

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
/*import com.p2pengine.core.p2p.P2pConfig
import com.p2pengine.core.utils.AnnounceLocation
import com.p2pengine.sdk.P2pEngine*/            // Remove By Haris Abbas (p2p)
import com.dream.live.cricket.score.hd.streaming.adsData.AppOpenManager
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.p2pengine.core.p2p.EngineExceptionListener
import com.p2pengine.core.p2p.P2pConfig
import com.p2pengine.core.segment.StrictHlsSegmentIdGenerator
import com.p2pengine.core.tracking.TrackerZone
import com.p2pengine.core.utils.EngineException
import com.p2pengine.sdk.P2pEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Arrays

class MyApp : Application() {

    private var appOpenManager: AppOpenManager? = null
    override fun onCreate() {
        super.onCreate()
        appOpenManager = AppOpenManager(this)

//        val testDeviceIds = Arrays.asList("890CE0083E0D133594E4E763481D1140")
//        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
//        MobileAds.setRequestConfiguration(configuration)
        try {
            val config = P2pConfig.Builder()
                .p2pEnabled(true)
                //.logEnabled(true)
                //.logLevel(LogLevel.DEBUG)
                .trackerZone(TrackerZone.Europe)
                .insertTimeOffsetTag(0.0)
                .build()
            P2pEngine.init(applicationContext, "WUAlHM1Vg", config)
//            P2pEngine.getInstance()?.setHlsSegmentIdGenerator(StrictHlsSegmentIdGenerator())

            P2pEngine.getInstance()?.registerExceptionListener(object : EngineExceptionListener {
                override fun onOtherException(e: EngineException) {

                }

                override fun onSchedulerException(e: EngineException) {

                }

                override fun onSignalException(e: EngineException) {

                }

                override fun onTrackerException(e: EngineException) {

                }


            })

        } catch (e: Exception) {
            Log.d("Exception", "msg")
        }
    }


}