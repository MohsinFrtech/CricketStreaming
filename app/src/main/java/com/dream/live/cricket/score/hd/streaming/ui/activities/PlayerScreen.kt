package com.dream.live.cricket.score.hd.streaming.ui.activities

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaLoadRequestData
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastSession
import com.google.android.gms.cast.framework.SessionManagerListener
import com.google.android.gms.cast.framework.media.RemoteMediaClient
import com.google.android.gms.tasks.Task
/*import com.p2pengine.core.p2p.EngineExceptionListener
import com.p2pengine.core.p2p.PlayerInteractor
import com.p2pengine.core.utils.EngineException
import com.p2pengine.sdk.P2pEngine*/            // remove By Haris Abbas (p2p)
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.ActivityExoTestPlayerBinding
import com.dream.live.cricket.score.hd.databinding.ExoPlaybackControlViewBinding
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.utils.Logger
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.adLocation2bottom
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.adLocation2topPermanent
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.location2BottomProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.location2TopPermanentProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.locationAfter
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userLinkVal
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userType3
import com.dream.live.cricket.score.hd.streaming.utils.objects.DebugChecker
import com.dream.live.cricket.score.hd.streaming.utils.objects.Defamation
import com.dream.live.cricket.score.hd.streaming.viewmodel.OneViewModel
import com.dream.live.cricket.score.hd.utils.InternetUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class PlayerScreen : AppCompatActivity(), Player.Listener, AdManagerListener,
    SessionManagerListener<CastSession> {


    ///Local class Variables....
    private var binding: ActivityExoTestPlayerBinding? = null
    private var audioManager: AudioManager? = null
    private var maxVolumeValue = 0
    private val logger = Logger()
    private var appSound = 0
    private var path = ""
    private var channel_Type = ""
    private var baseUrl = ""
    private var mCastSession: CastSession? = null
    private var mSessionManagerListener: SessionManagerListener<CastSession>? = null
    private var player: ExoPlayer? = null
    private var adStatus = false
    private var mLastTouchY: Float = 0.0F
    private var viewCount = 0
    private var mLocation: PlaybackLocation? = null
    private var mCastContextTask: Task<CastContext>? = null
    private var mCastContext: CastContext? = null
    private var mLastTouchBrightY: Float = 0.0F
    private val tAG = "PlayerScreenClass"
    private var counter = 0
    private var lockCounter = 0
    private var mPosBY: Float = 0.0F
    private var isLockMode: Boolean = false
    private var mPosY: Float = 0.0F
    private var mediaRouteMenuItem: MenuItem? = null
    private var finalProgress = 0
    private val viewModel by lazy {
        ViewModelProvider(this)[OneViewModel::class.java]
    }
    private var context: Context? = null
    private var mActivePointerId = MotionEvent.INVALID_POINTER_ID
    private var mLastTouchX: Float = 0.0F
    private var mActivePointerBrightId = MotionEvent.INVALID_POINTER_ID
    private val castExecutor: Executor = Executors.newSingleThreadExecutor()
    private var mPlaybackState: PlaybackState? = null
    private var count = 1
    private var orientationEventListener: OrientationEventListener? = null
    private var bindingExoPlayback: ExoPlaybackControlViewBinding? = null
    private var adManager: AdManager? = null

    ///Playback location
    enum class PlaybackLocation {
        LOCAL, REMOTE
    }

    //Checking player state...
    enum class PlaybackState {
        PLAYING, IDLE
    }
    private var booleanVpn: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exo_test_player)
        context = this
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        val exoView: ConstraintLayout? = binding?.playerView?.findViewById(R.id.exoControlView)
        bindingExoPlayback = exoView?.let { ExoPlaybackControlViewBinding.bind(it) }
        mLocation = PlaybackLocation.LOCAL
        setupActionBar()
//        getAppVolumeLevel()
//        getAppBrightnessLevel()
//        swipeVolumeFeature()
        changeOrientation()
//        swipeBrightnessFeature()
        initializeCastSdk()
        getNavValues()
        checkForAds()
        screenModeController()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                if (adStatus) {
                    if (!Constants.locationAfter.equals("none", true)) {
                        if (player != null) {
                            player?.stop()
                            player!!.release()
                            player = null
                        }
                        adManager?.showAds(locationAfter)
                        binding?.lottiePlayer2?.visibility = View.VISIBLE
                    }
                } else {
                    Constants.videoFinish = true
                    finish()
                }


            }
        })

    }

    private fun getNavValues() {
        try {
            val channelData: PlayerScreenArgs by navArgs()
            baseUrl = channelData.baseURL.toString()
            path = channelData.linkAppend.toString()
            channel_Type = channelData.channleType.toString()


            if (channel_Type.equals(Constants.userType1, true)) {
                lifecycleScope.launch(Dispatchers.Main) {
                    setUpPlayer(path)
                }
            } else if (channel_Type.equals(Constants.userType2, true)) {
                binding?.lottiePlayer?.visibility = View.VISIBLE

                viewModel.getDemoData()
                viewModel.userLinkStatus.observe(this) {
                    if (it == true) {
                        path = userLinkVal
                        lifecycleScope.launch(Dispatchers.Main) {
                            setUpPlayer(path)
                        }
                    }
                }
            } else if (channel_Type.equals(userType3, true)) {
                if (path.isNotEmpty()) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        setUpPlayerP2p(path)
                    }
                }
            } else {
                binding?.lottiePlayer?.visibility = View.VISIBLE
                viewModel.getDemoData()
                viewModel.userLinkStatus.observe(this) {
                    if (it == true) {
                        path = userLinkVal
                        if (path.isNotEmpty()) {
                            lifecycleScope.launch(Dispatchers.Main) {
                                setUpPlayerP2p(path)
                            }
                        }
                    }
                }

            }

        } catch (e: Exception) {
            Log.d("Exception", "message : ${e.cause}")
        }


    }
    private fun checkVpn() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    val booleanVpnCheck = hasTransport(NetworkCapabilities.TRANSPORT_VPN)
                    booleanVpn = booleanVpnCheck == true
                }
            } else {
                booleanVpn = false
            }
        }

        if (booleanVpn != null) {
            if (booleanVpn!!) {
                if (binding?.adblockLayout?.isVisible!!) {
                    /////////

                } else {
                    binding?.adblockLayout?.visibility = View.VISIBLE

                }
            } else {
                binding?.adblockLayout?.visibility = View.GONE

            }
        }

    }

    private fun checkForAds() {
        adManager = context?.let { AdManager(it, this, this) }

        //interstitial Ad loading
        adManager?.loadAdProvider(
            Constants.locationAfter, Constants.adAfter,
            null, null, null, null
        )

        loadLocation2TopPermanentProvider()
        loadLocation2BottomProvider()
    }

    private fun loadLocation2BottomProvider() {
        if (!location2BottomProvider.equals("none", true)) {
            binding?.adViewBottom?.let { it1 ->
                binding?.fbAdViewBottom?.let { it2 ->
                    adManager?.loadAdProvider(
                        location2BottomProvider, adLocation2bottom,
                        it1, it2, binding?.unityBannerViewBottom,binding?.startAppBannerBottom
                    )
                }
            }
        }
    }

    private fun loadLocation2TopPermanentProvider() {
        if (!location2TopPermanentProvider.equals("none", true)) {
            binding?.adViewTopPermanent?.let { it1 ->
                binding?.fbAdViewTop?.let { it2 ->
                    adManager?.loadAdProvider(
                        location2TopPermanentProvider, adLocation2topPermanent,
                        it1, it2, binding?.unityBannerView, binding?.startAppBannerTop
                    )
                }
            }
        }
    }
    private fun screenModeController() {
        bindingExoPlayback?.fullScreenIcon?.setOnClickListener {
            when (count) {
                1 -> {

                    setImageViewListener("Fit", R.drawable.fit_mode)

                }
                2 -> {
                    setImageViewListener("Fill", R.drawable.full_mode)

                }
                3 -> {
                    setImageViewListener("Stretch", R.drawable.stretch)

                }
                4 -> {
                    setImageViewListener("Original", R.drawable.ic_full_screen)

                }
            }
        }
        bindingExoPlayback?.lockMode?.setOnClickListener {
            if (lockCounter == 0) {
                isLockMode = true
                viewVisibility(View.GONE)
                bindingExoPlayback?.lockAffect?.visibility = View.VISIBLE
                lockCounter++
            } else {
                viewVisibility(View.VISIBLE)
                isLockMode = false
                bindingExoPlayback?.lockAffect?.visibility = View.GONE
                lockCounter = 0
            }

        }
    }


    private fun setUpPlayerP2p(link: String?) {
        logger.printLog(
            tAG, "setUpPlayer P2p" + link
                    + " " + mLocation
        )
      //  val parsedUrl = P2pEngine.instance?.parseStreamUrl(link!!)   //Remove By Haris Abbas (p2p)

        // Create LoadControl
        val loadControl: LoadControl = DefaultLoadControl.Builder()
            .setAllocator(DefaultAllocator(true, 16))
            .setBufferDurationsMs(
                VideoPlayerConfig.MIN_BUFFER_DURATION,
                VideoPlayerConfig.MAX_BUFFER_DURATION,
                VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER
            )
            .setTargetBufferBytes(-1)
            .setPrioritizeTimeOverSizeThresholds(true)
            .build()

        binding?.lottiePlayer?.visibility = View.GONE

        val meter: BandwidthMeter = DefaultBandwidthMeter.Builder(this).build()
        val trackSelector: TrackSelector = DefaultTrackSelector(this)
        // 2. Create a default LoadControl
        player = null
        player = context?.let {
            ExoPlayer.Builder(it)
                //.setBandwidthMeter(meter)
                //.setTrackSelector(trackSelector)
                .setLoadControl(loadControl)
                .build()
        }
        binding?.playerView?.player = player
        binding?.playerView?.keepScreenOn = true
        //Initialize data source factory
        val defaultDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaItem2 = MediaItem.Builder()
           // .setUri(parsedUrl)    Remove By Haris Abbas (p2p)
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            .build()

        //Initialize hlsMediaSource
        val hlsMediaSource: HlsMediaSource =
            HlsMediaSource.Factory(defaultDataSourceFactory).createMediaSource(mediaItem2)
        val concatenatedSource = ConcatenatingMediaSource(hlsMediaSource)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding?.playerView?.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            bindingExoPlayback?.fullScreenIcon?.setImageDrawable(
                context?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.ic_full_screen
                    )
                }
            )
            count = 1
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding?.playerView?.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        }

        when (mLocation) {
            PlaybackLocation.LOCAL -> {
                if (mCastSession != null && mCastSession?.remoteMediaClient != null) {
                    mCastSession?.remoteMediaClient?.stop()
                    mCastContext?.sessionManager?.endCurrentSession(true)
                }
                mPlaybackState =
                    PlaybackState.IDLE

                if (player != null) {

                    /*P2pEngine.instance?.setPlayerInteractor(object : PlayerInteractor() {
                        override fun onBufferedDuration(): Long {
                            return if (player != null) {
                                player!!.bufferedPosition - player!!.currentPosition
                            } else {
                                -1
                            }
                        }
                    })*/          //Remove By Haris Abbas (p2p)

                    /*P2pEngine.instance?.registerExceptionListener(object : EngineExceptionListener {
                        override fun onTrackerException(e: EngineException) {
                            // Tracker Exception
                            logger.printLog(tAG, "P2pEngine onTrackerException : ${e.cause}")
                            logger.printLog(
                                tAG,
                                "P2pEngine onTrackerException : ${e.localizedMessage}"
                            )
                        }

                        override fun onSignalException(e: EngineException) {
                            // Signal Server Exception
                            logger.printLog(tAG, "P2pEngine onSignalException : ${e.cause}")
                            logger.printLog(
                                tAG,
                                "P2pEngine onSignalException : ${e.localizedMessage}"
                            )
                        }

                        override fun onSchedulerException(e: EngineException) {
                            // Scheduler Exception
                            logger.printLog(tAG, "P2pEngine onSchedulerException : ${e.cause}")
                            logger.printLog(
                                tAG,
                                "P2pEngine onSchedulerException : ${e.localizedMessage}"
                            )
                        }

                        override fun onOtherException(e: EngineException) {
                            // Other Exception
                            logger.printLog(tAG, "P2pEngine onOtherException : ${e.cause}")
                            logger.printLog(
                                tAG,
                                "P2pEngine onOtherException : ${e.localizedMessage}"
                            )
                        }
                    })*/                  //Remove By Haris Abbas (p2p)


                    player?.addListener(this)
                    player?.setMediaSource(concatenatedSource)
                    player?.prepare()
                    binding?.playerView?.requestFocus()
                    player?.playWhenReady = true
                }

            }
            PlaybackLocation.REMOTE -> {
                mCastSession?.remoteMediaClient?.play()
                mPlaybackState =
                    PlaybackState.PLAYING
            }
            else -> {
            }
        }
    }


    private fun viewVisibility(value: Int) {

        resources.configuration.orientation
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            bindingExoPlayback?.fullScreenIcon?.visibility = View.GONE
        } else {
            bindingExoPlayback?.fullScreenIcon?.visibility = value

        }

        bindingExoPlayback?.seekProgress?.visibility = value
        bindingExoPlayback?.exoPlayPause?.visibility = value

    }


    private fun initializeCastSdk() {
        try {
            mCastContextTask = context?.let { it ->
                CastContext.getSharedInstance(it, castExecutor)
                    .addOnCompleteListener {
                        runOnUiThread(Runnable {
                            if (it.isComplete)
                            {
                                mCastContext = mCastContextTask?.result
                                setupCastListener()
                                mCastContext?.sessionManager?.addSessionManagerListener(
                                    this, CastSession::class.java
                                )
                            }
                        })
                    }
            }
        } catch (e: Exception) {

        }
    }


    ////Change orientation of screen programmatically......
    private fun changeOrientation() {
        Thread {
            orientationEventListener =
                object : OrientationEventListener(this) {
                    override fun onOrientationChanged(orientation: Int) {
                        val leftLandscape = 90
                        val rightLandscape = 270
                        runOnUiThread {
                            try {
                                if (epsilonCheck(orientation, leftLandscape) ||
                                    epsilonCheck(orientation, rightLandscape)
                                ) {
                                    if (!isLockMode)
                                        requestedOrientation =
                                            ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                                } else {
                                    if (!isLockMode) {
                                        if (orientation in 0..45 || orientation >= 315 || orientation in 135..225) {
                                            requestedOrientation =
                                                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                                        }

                                    }
                                }
                            }
                            catch (e:Exception){
                                Log.d("Exception","message")
                            }
                        }

                    }

                    private fun epsilonCheck(a: Int, b: Int): Boolean {
                        return a > b - 10 && a < b + 10
                    }
                }
            orientationEventListener?.enable()

        }.start()

    }


    override fun onDestroy() {
        super.onDestroy()
        orientationEventListener?.disable()
        if (player != null) {
            player!!.release()
        }
        /*if (P2pEngine.instance?.isConnected == true) {
            P2pEngine.instance?.stopP2p()
        }*/        //Remove By Haris Abbas (p2p)
    }

    ///
    private fun setImageViewListener(string: String, id: Int) {
        if (binding?.playerView != null) {

            bindingExoPlayback?.layoutRight?.visibility = View.VISIBLE
            val animFadeIn =
                AnimationUtils.loadAnimation(
                    applicationContext,
                    R.anim.fade_in
                )
            bindingExoPlayback?.fullScreenIcon?.startAnimation(animFadeIn)
            when (string) {
                "Fit" -> {
                    binding?.playerView?.resizeMode =
                        AspectRatioFrameLayout.RESIZE_MODE_FILL
                    count++

                }
                "Fill" -> {
                    binding?.playerView?.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                    player?.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
                    count++
                }
                "Stretch" -> {
                    binding?.playerView?.resizeMode =
                        AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    player?.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                    count++
                }
                else -> {
                    binding?.playerView?.resizeMode =
                        AspectRatioFrameLayout.RESIZE_MODE_FIT

                    count = 1
                }
            }

            Handler(Looper.getMainLooper()).postDelayed({
                bindingExoPlayback?.layoutRight?.visibility = View.GONE
                changeImageDrawable(id, bindingExoPlayback?.fullScreenIcon)
            }, 500)

        }
        bindingExoPlayback?.chnagedText?.visibility = View.VISIBLE
        bindingExoPlayback?.chnagedText?.text = string
        Handler(Looper.getMainLooper()).postDelayed(
            { bindingExoPlayback?.chnagedText?.visibility = View.GONE },
            2000
        )

    }

    override fun onPlayerError(error: PlaybackException) {
        if (player != null) {
            player?.playWhenReady = false
            player?.stop()
            player?.release()
        }

        againPlay()
    }

    private fun againPlay() {
        try {
            if (channel_Type.equals(Constants.userType1, true)) {
                val token = baseUrl.let { it1 -> Defamation.improveDeprecatedCode(it1) }
                path = baseUrl + token
                setUpPlayer(path)
            } else if (channel_Type.equals(Constants.userType2, true)) {
                path = userLinkVal
            } else if (channel_Type.equals(userType3, true)) {
                val token = baseUrl.let { it1 -> Defamation.improveDeprecatedCode(it1) }
                path = baseUrl + token
                setUpPlayerP2p(path)
            } else {
                path = userLinkVal
                setUpPlayerP2p(path)
            }

        } catch (e: Exception) {
            logger.printLog("Exception", "" + e.message)
        }
    }


    private fun setupActionBar() {
        setSupportActionBar(binding?.myToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(
            applicationContext,
            menu,
            R.id.media_route_menu_item
        )

        return true
    }


    ///Swipe Brightness Feature...
    private fun swipeBrightnessFeature() {
        binding?.leftView?.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                try {
                    binding?.leftView?.performClick()
                    binding?.leftVerticalSlider?.max = 100
                    when (p1?.action) {
                        MotionEvent.ACTION_DOWN -> {
                            counter = 0
                            binding?.leftVerticalSlider?.visibility = View.VISIBLE

                            changeImageDrawable(R.drawable.brightnesslow, binding?.volumeIcon)
                            binding?.volumeLay?.visibility = View.VISIBLE
                            mActivePointerBrightId = p1.getPointerId(0)

                            return true
                        }
                        MotionEvent.ACTION_MOVE -> {
                            counter++
                            if (counter < 15) {
                                /////

                            } else {
                                if (mLastTouchBrightY > 0.0) {
                                    val y = p1.y
                                    if (y == mLastTouchBrightY) {
                                        ///////means last touch and click position is same,,,

                                    } else {
                                        ////
                                        if (y > mLastTouchBrightY) {
                                            mLastTouchBrightY++

                                            val currentHeight =
                                                binding?.leftView?.measuredHeight?.minus(
                                                    mLastTouchBrightY
                                                )
                                            val percent =
                                                currentHeight?.div(binding?.leftView?.measuredHeight!!.toFloat())
                                            val per = (percent?.times(100))?.toInt()
                                            if (per != null) {
                                                if (per in 0..100) {
                                                    binding?.leftVerticalSlider?.progress = per
                                                    finalProgress = per
                                                    if (finalProgress in 1..100) {
                                                        if (finalProgress < (100 / 3) * 100 / 100) {
                                                            changeImageDrawable(
                                                                R.drawable.brightnesslow,
                                                                binding?.volumeIcon
                                                            )
                                                            ////Low volume icon
                                                        } else if (finalProgress < (100 * 2 / 3) * 100 / 100) {
                                                            changeImageDrawable(
                                                                R.drawable.brightnessmid,
                                                                binding?.volumeIcon
                                                            )
                                                            ///middle volume icon...

                                                        } else {
                                                            changeImageDrawable(
                                                                R.drawable.brightnessfull,
                                                                binding?.volumeIcon
                                                            )
                                                            ///high volume icon
                                                        }
                                                    }
                                                    screenBrightness(finalProgress.toDouble())

                                                }

                                            }
                                        } else if (y < mLastTouchBrightY) {
                                            if (mLastTouchBrightY > 0.0) {
                                                ////
                                                mLastTouchBrightY--

                                                val currentHeight =
                                                    binding?.leftView?.measuredHeight?.minus(
                                                        mLastTouchBrightY
                                                    )
                                                val percent =
                                                    currentHeight?.div(binding?.leftView?.measuredHeight!!.toFloat())
                                                val per = (percent?.times(100))?.toInt()
                                                if (per != null) {
                                                    if (per in 0..100) {
                                                        binding?.leftVerticalSlider?.progress = per
                                                        finalProgress = per

                                                        if (finalProgress in 1..100) {
                                                            if (finalProgress < (100 / 3) * 100 / 100) {

                                                                changeImageDrawable(
                                                                    R.drawable.brightnesslow,
                                                                    binding?.volumeIcon
                                                                )
                                                                ////Low volume icon

                                                            } else if (finalProgress < (100 * 2 / 3) * 100 / 100) {
                                                                changeImageDrawable(
                                                                    R.drawable.brightnessmid,
                                                                    binding?.volumeIcon
                                                                )
                                                                ///middle volume icon...
                                                            } else {
                                                                changeImageDrawable(
                                                                    R.drawable.brightnessfull,
                                                                    binding?.volumeIcon
                                                                )
                                                                ///high volume icon
                                                            }
                                                        }
                                                        screenBrightness(finalProgress.toDouble())

                                                    }

                                                }


                                            }


                                        }


                                    }

                                } else {
                                    mLastTouchY = 0.1F
                                }


                            }

                            try {
                                val (x: Float, k: Float) =
                                    p1.findPointerIndex(mActivePointerBrightId)
                                        .let { pointerIndex ->
                                            // Calculate the distance moved
                                            p1.getX(pointerIndex) to
                                                    p1.getY(pointerIndex)
                                        }
                                mPosBY += k - mLastTouchBrightY
//                             Remember this touch position for the next move event

                                mLastTouchX = x
                                mLastTouchBrightY = k
                            } catch (e: Exception) {
                                Log.d("Exception", "" + e.message)

                            }



                            return true
                        }
                        MotionEvent.ACTION_UP -> {
                            counter = 0
                            mActivePointerBrightId = MotionEvent.INVALID_POINTER_ID
                            binding?.leftVerticalSlider?.visibility = View.GONE
                            binding?.volumeLay?.visibility = View.GONE
                            return true
                        }
                    }
                } catch (e: Exception) {

                    Log.d("Exception", "" + e.message)

                }

                return false
            }
        })

    }

    ///Screen Brightness.....
    private fun screenBrightness(newBrightnessValue: Double) {

        val lp = window.attributes
        val newBrightness = newBrightnessValue.toFloat()
        lp.screenBrightness = newBrightness / 255f
        window.attributes = lp
    }

    ///Swipe volume feature...
    private fun swipeVolumeFeature() {
        binding?.rightView?.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                try {
                    binding?.rightView?.performClick()
                    binding?.verticalSlider?.max = 100
                    when (p1?.actionMasked) {

                        MotionEvent.ACTION_DOWN -> {
                            counter = 0
                            binding?.verticalSlider?.visibility = View.VISIBLE
                            binding?.verticalSlider?.progress = appSound

                            changeImageDrawable(
                                R.drawable.ic_volume_low_grey600_36dp,
                                binding?.volumeIcon
                            )
                            binding?.volumeLay?.visibility = View.VISIBLE
                            // Save the ID of this pointer (for dragging)
                            mActivePointerId = p1.getPointerId(0)
                            return true
                        }
                        MotionEvent.ACTION_MOVE -> {
                            counter++
                            if (counter < 15) {
                                //


                            } else {
                                val y = p1.y
                                if (mLastTouchY > 0.0) {

                                    if (y == mLastTouchY) {
                                        ///////means last touch and click position is same,,,

                                    } else {
                                        if (y > mLastTouchY) {
                                            mLastTouchY++

                                            val currentHeight =
                                                binding?.verticalSlider?.measuredHeight?.minus(
                                                    mLastTouchY
                                                )
                                            val percent =
                                                currentHeight?.div(binding?.verticalSlider?.measuredHeight!!.toFloat())
                                            val percentageHundred = (percent?.times(100))
                                            val per = percentageHundred?.toInt()
                                            if (per != null) {
                                                if (per in 0..100) {
                                                    binding?.verticalSlider?.progress = per
                                                    finalProgress = per

                                                    if (finalProgress in 1..100) {
                                                        if (finalProgress < (maxVolumeValue / 3) * 100 / maxVolumeValue) {
                                                            changeImageDrawable(
                                                                R.drawable.ic_volume_low_grey600_36dp,
                                                                binding?.volumeIcon
                                                            )
                                                            ////Low volume icon
                                                        } else if (finalProgress < (maxVolumeValue * 2 / 3) * 100 / maxVolumeValue) {
                                                            changeImageDrawable(
                                                                R.drawable.ic_volume_medium_grey600_36dp,
                                                                binding?.volumeIcon
                                                            )
                                                            ///middle volume icon...

                                                        } else {
                                                            changeImageDrawable(
                                                                R.drawable.ic_volume_high_grey600_36dp,
                                                                binding?.volumeIcon
                                                            )
                                                            ///high volume icon
                                                        }
                                                    }


                                                }

                                            }


                                        } else if (y < mLastTouchY) {
                                            if (mLastTouchY > 0) {
                                                ////
                                                mLastTouchY--
                                                ///less than last touch

                                                val currentHeight =
                                                    binding?.verticalSlider?.measuredHeight?.minus(
                                                        mLastTouchY
                                                    )
                                                val percent =
                                                    currentHeight?.div(binding?.verticalSlider?.measuredHeight!!.toFloat())
                                                val percentageHundred2 = (percent?.times(100))
                                                val per = percentageHundred2?.toInt()
                                                if (per != null) {
                                                    if (per in 0..100) {
                                                        binding?.verticalSlider?.progress = per
                                                        finalProgress = per

                                                        if (finalProgress in 1..100) {
                                                            if (finalProgress < (maxVolumeValue / 3) * 100 / maxVolumeValue) {

                                                                changeImageDrawable(
                                                                    R.drawable.ic_volume_low_grey600_36dp,
                                                                    binding?.volumeIcon
                                                                )
                                                                ////Low volume icon
                                                            } else if (finalProgress < (maxVolumeValue * 2 / 3) * 100 / maxVolumeValue) {

                                                                changeImageDrawable(
                                                                    R.drawable.ic_volume_medium_grey600_36dp,
                                                                    binding?.volumeIcon
                                                                )
                                                                ///middle volume icon...

                                                            } else {
                                                                changeImageDrawable(
                                                                    R.drawable.ic_volume_high_grey600_36dp,
                                                                    binding?.volumeIcon
                                                                )
                                                                ///high volume icon
                                                            }
                                                        }


                                                    }

                                                }

                                            }


                                        }

                                    }

                                } else {
                                    mLastTouchY = 0.1F
                                }
//                             Find the index of the active pointer and fetch its position

                                try {

                                    val (x: Float, k: Float) =

                                        p1.findPointerIndex(mActivePointerId)
                                            .let { pointerIndex ->
                                                // Calculate the distance moved
                                                p1.getX(pointerIndex) to
                                                        p1.getY(pointerIndex)
                                            }
                                    mPosY += k - mLastTouchY
                                    //                     Remember this touch position for the next move event

                                    mLastTouchX = x
                                    mLastTouchY = k

                                } catch (e: Exception) {

                                    logger.printLog(tAG, "message" + e.message)

                                }

                            }

                            return true

                        }
                        MotionEvent.ACTION_UP -> {
                            counter = 0
                            mActivePointerId = MotionEvent.INVALID_POINTER_ID
                            val getPercentageFinal = finalProgress.toFloat().div(100.toFloat())
                            val appSoundFinalVol = getPercentageFinal.times(maxVolumeValue).toInt()
                            appSound = finalProgress
                            audioManager?.setStreamVolume(
                                AudioManager.STREAM_MUSIC,
                                appSoundFinalVol,
                                AudioManager.FLAG_PLAY_SOUND
                            )

                            binding?.volumeLay?.visibility = View.GONE
                            binding?.verticalSlider?.visibility = View.GONE


                            return true
                        }
                    }
                } catch (e: Exception) {
                    logger.printLog(tAG, "message" + e.message)

                }

                return false
            }

        })

    }


    ///
    fun changeImageDrawable(drawable: Int, imageView: ImageView?) {
        imageView?.setImageDrawable(context?.let { ContextCompat.getDrawable(it, drawable) })

    }

    ///getting app Volume level...
    private fun getAppVolumeLevel() {

        try {
            audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager?
            if (audioManager != null) {
                val currentVolumeLevel = audioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
                maxVolumeValue = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                if (maxVolumeValue < 100) {

                    appSound =
                        (currentVolumeLevel.toFloat().div(maxVolumeValue.toFloat())).times(100)
                            .toInt()
                    binding?.rightView?.post {
                        binding?.rightView?.measure(
                            View.MeasureSpec.UNSPECIFIED,
                            View.MeasureSpec.UNSPECIFIED
                        )
                        val height: Int? = binding?.rightView?.measuredHeight
                        if (height != null) {
                            val pointerValue = (appSound.toDouble() / 100.0) * (height.toDouble())
                            val currentPointer = (height.toDouble() - pointerValue)
                            mLastTouchY = currentPointer.toFloat()
                        }


                    }


                }
            }

        } catch (e: Exception) {

            logger.printLog(tAG, "Exception" + "  " + e.message)


        }


    }


    ///getting app Brightness level,,,,
    private fun getAppBrightnessLevel() {
        try {
            val brightnessMode = Settings.System.getInt(
                contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE
            )
            val curBrightnessValue =
                Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, -1)

            if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                Settings.System.putInt(
                    contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                )
            }

            if (curBrightnessValue > 0) {
                val height: Int? = binding?.leftView?.measuredHeight

                if (height != null) {
                    val pointer = (curBrightnessValue.toDouble() / 100.0) * (height.toDouble())
                    val currentPosition = (height.toDouble() - pointer)
                    mLastTouchBrightY = currentPosition.toFloat()
                }


            }

        } catch (e: Exception) {
            // do something useful
        }
    }


    private fun setUpPlayer(link: String?) {
        binding?.lottiePlayer?.visibility = View.GONE

        val meter: BandwidthMeter = DefaultBandwidthMeter.Builder(this).build()
        val trackSelector: TrackSelector = DefaultTrackSelector(this)
        // 2. Create a default LoadControl
        player = null
        val loadControl: LoadControl = DefaultLoadControl()
        player = context?.let {
            ExoPlayer.Builder(it)
                .setBandwidthMeter(meter)
                .setTrackSelector(trackSelector)
                .setLoadControl(loadControl)
                .build()
        }
        binding?.playerView?.player = player
        binding?.playerView?.keepScreenOn = true
        //Initialize data source factory
        val defaultDataSourceFactory = DefaultDataSource.Factory(this)
        val mediaItem2 = MediaItem.Builder()
            .setUri(link)
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            .build()

        //Initialize hlsMediaSource
        val hlsMediaSource: HlsMediaSource =
            HlsMediaSource.Factory(defaultDataSourceFactory).createMediaSource(mediaItem2)
        val concatenatedSource = ConcatenatingMediaSource(hlsMediaSource)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding?.playerView?.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            bindingExoPlayback?.fullScreenIcon?.setImageDrawable(
                context?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.ic_full_screen
                    )
                }
            )
            count = 1
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding?.playerView?.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        }

        when (mLocation) {
            PlaybackLocation.LOCAL -> {
                if (mCastSession != null && mCastSession?.remoteMediaClient != null) {
                    mCastSession?.remoteMediaClient?.stop()
                    mCastContext?.sessionManager?.endCurrentSession(true)
                }
                mPlaybackState =
                    PlaybackState.IDLE

                if (player != null) {
                    player?.addListener(this)
                    player?.setMediaSource(concatenatedSource)
                    player?.prepare()
                    binding?.playerView?.requestFocus()
                    player?.playWhenReady = true
                }

            }
            PlaybackLocation.REMOTE -> {
                mCastSession?.remoteMediaClient?.play()
                mPlaybackState =
                    PlaybackState.PLAYING
            }
            else -> {
            }
        }
        setOnGestureListeners()
    }

    ////Load remote media when connected with casting device
    private fun loadRemoteMedia() {
        if (mCastSession == null) {
            return
        }
        val remoteMediaClient = mCastSession!!.remoteMediaClient ?: return
        remoteMediaClient.registerCallback(object : RemoteMediaClient.Callback() {
            override fun onStatusUpdated() {


                val intent =
                    Intent(context, ExpendedActivity::class.java)
                startActivity(intent)
                remoteMediaClient.unregisterCallback(this)
            }
        })


        val loadData = MediaLoadRequestData.Builder()
            .setMediaInfo(buildMediaInfo())
            .build()

        remoteMediaClient.load(loadData)
//        buildMediaInfo()?.let { remoteMediaClient.load(loadData) }
    }


    override fun onPause() {
        super.onPause()
        if (player != null) {
            player?.playWhenReady = false

        }
//        Chartboost.onPause(this)
    }

    override fun onResume() {
        super.onResume()
        if (InternetUtil.isPrivateDnsSetup(this)) {
            Toast.makeText(
                this,
                "Please turn off private dns,If not found then search dns in setting search",
                Toast.LENGTH_LONG
            ).show()
            try {
                startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
            } catch (e: Exception) {
                Log.d("Exception", "msg")
            }
        }

        hideSystemUI()
        if (mCastSession != null) {
            mCastContext?.sessionManager?.addSessionManagerListener(
                this, CastSession::class.java
            )
            if (mCastSession != null && mCastSession!!.isConnected) {

                if (player != null) {
                    player?.playWhenReady = false
                    player?.release()
                }

                try {
                    lifecycleScope.launch(Dispatchers.Main) {
                        if (channel_Type.equals(userType3, true)) {
                            lifecycleScope.launch(Dispatchers.Main) {
                                setUpPlayerP2p(path)
                            }
                        } else {
                            lifecycleScope.launch(Dispatchers.Main) {
                                setUpPlayer(path)
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {

                updatePlaybackLocation()
            }
        } else {
            if (player != null) {
                if (DebugChecker.checkDebugging(this)) {
                    player?.playWhenReady = false
                    player?.stop()

                } else {
                    player?.playWhenReady = true

                }
            }
        }

        checkVpn()
    }


    /////Media builder function
    private fun buildMediaInfo(): MediaInfo {


        return path.let {
            MediaInfo.Builder(it)
                .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setContentType("application/x-mpegURL")
                .build()
        }
    }

    ///Hide System bar.....
    private fun hideSystemUI() {
        // Set the content to appear under the system bars so that the content
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.decorView.let {
            WindowInsetsControllerCompat(window, it).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (binding?.playerView != null) {
            val orientation = newConfig.orientation

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                binding?.adViewTop?.removeAllViews()
                binding?.fbAdViewTop?.removeAllViews()
                binding?.unityBannerView?.removeAllViews()
                binding?.startAppBannerTop?.removeAllViews()

                loadLocation2TopPermanentProvider()
                loadLocation2BottomProvider()
                bindingExoPlayback?.fullScreenIcon?.visibility = View.GONE

                binding?.playerView?.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                binding?.adViewTopPermanent?.removeAllViews()
                binding?.adViewBottom?.removeAllViews()
                binding?.fbAdViewBottom?.removeAllViews()
                binding?.unityBannerViewBottom?.removeAllViews()
                binding?.startAppBannerBottom?.removeAllViews()

                if (!Constants.location2TopProvider.equals("none", true)) {
                    binding?.adViewTop?.let { it1 ->
                        binding?.fbAdViewTop?.let { it2 ->
                            adManager?.loadAdProvider(
                                Constants.location2TopProvider, Constants.adLocation2top,
                                it1, it2, binding?.unityBannerView,binding?.startAppBannerTop
                            )
                        }
                    }
                }
                if (bindingExoPlayback?.lockAffect?.visibility == View.VISIBLE) {
                    //
                    bindingExoPlayback?.fullScreenIcon?.visibility = View.GONE


                } else {

                    bindingExoPlayback?.fullScreenIcon?.visibility = View.VISIBLE
                    binding?.playerView?.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                    bindingExoPlayback?.fullScreenIcon?.setImageDrawable(
                        context?.let {
                            ContextCompat.getDrawable(
                                it,
                                R.drawable.ic_full_screen
                            )
                        }
                    )
                    count = 1

                }

            }
        }

    }


    ////Listener for castSession manager
    private fun setupCastListener() {
//        if (mSessionManagerListener == null) {
//            mSessionManagerListener = object : SessionManagerListener<CastSession> {
//                override fun onSessionStarting(castSession: CastSession) {
//
//                }
//
//                override fun onSessionStarted(castSession: CastSession, s: String) {
//                    onApplicationConnected(castSession)
//                }
//
//                override fun onSessionStartFailed(castSession: CastSession, i: Int) {
////                    Log.d("player_error", "" + "failed1")
//                    onApplicationDisconnected()
//                }
//
//                override fun onSessionEnding(castSession: CastSession) {
//
//                }
//
//                override fun onSessionEnded(castSession: CastSession, i: Int) {
//                    onApplicationDisconnected()
////                    Log.d("player_error", "" + "failed2")
//                }
//
//                override fun onSessionResuming(castSession: CastSession, s: String) {}
//                override fun onSessionResumed(castSession: CastSession, b: Boolean) {
//                    onApplicationConnected(castSession)
//                }
//
//                override fun onSessionResumeFailed(castSession: CastSession, i: Int) {
////                    Log.d("player_error", "" + "failed3")
//                    onApplicationDisconnected()
//                }
//
//                override fun onSessionSuspended(castSession: CastSession, i: Int) {
//
//                }
//
//
//            }
//        }


    }


    private fun updatePlaybackLocation() {

        mLocation = PlaybackLocation.LOCAL
    }

    private fun onApplicationConnected(castSession: CastSession) {
        mCastSession = castSession

        if (mPlaybackState == PlaybackState.IDLE) {
            if (mPlaybackState != PlaybackState.PLAYING) {
                loadRemoteMedia()
                mPlaybackState = PlaybackState.PLAYING
            }
            return
        }

        invalidateOptionsMenu()
    }

    private fun onApplicationDisconnected() {
        updatePlaybackLocation()
        mPlaybackState = PlaybackState.IDLE
        mLocation = PlaybackLocation.LOCAL
        invalidateOptionsMenu()
    }

    private fun setOnGestureListeners() {

        binding?.playerView?.setOnClickListener {
            if (viewCount == 0) {
                binding?.playerView?.hideController()
                if (mediaRouteMenuItem != null) mediaRouteMenuItem!!.isVisible = false

                viewCount++
            } else {
                binding?.playerView?.showController()
                if (mediaRouteMenuItem != null) mediaRouteMenuItem!!.isVisible = true
                viewCount = 0

            }

        }

    }

    override fun onAdLoad(value: String) {
        adStatus = value.equals("success", true)

    }

    override fun onAdFinish() {
        if (binding?.lottiePlayer2?.isVisible == true) {
            binding?.lottiePlayer2?.visibility = View.GONE
        }
        Constants.videoFinish = true
        finish()

    }

    override fun onSessionEnded(p0: CastSession, p1: Int) {
        onApplicationDisconnected()
    }

    override fun onSessionEnding(p0: CastSession) {
        onApplicationDisconnected()
    }

    override fun onSessionResumeFailed(p0: CastSession, p1: Int) {
        onApplicationDisconnected()
    }

    override fun onSessionResumed(p0: CastSession, p1: Boolean) {
//        onApplicationConnected(p0)

    }

    override fun onSessionResuming(p0: CastSession, p1: String) {
    }

    override fun onSessionStartFailed(p0: CastSession, p1: Int) {
        onApplicationDisconnected()

    }

    override fun onSessionStarted(p0: CastSession, p1: String) {
        onApplicationConnected(p0)
    }

    override fun onSessionStarting(p0: CastSession) {

    }

    override fun onSessionSuspended(p0: CastSession, p1: Int) {


    }

    object VideoPlayerConfig {
        //Minimum Video you want to buffer while Playing
        const val MIN_BUFFER_DURATION = 7000

        //Max Video you want to buffer during PlayBack
        const val MAX_BUFFER_DURATION = 15000

        //Min Video you want to buffer before start Playing it
        const val MIN_PLAYBACK_START_BUFFER = 7000

        //Min video You want to buffer when user resumes video
        const val MIN_PLAYBACK_RESUME_BUFFER = 7000
    }

    /*override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (P2pEngine.instance?.isConnected == true)
            P2pEngine.instance?.stopP2p()
    }*/                   //Remove By Haris Abbas (p2p)

}