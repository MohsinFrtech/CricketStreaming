package com.dream.live.cricket.score.hd.streaming.ui.activities

import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Rect
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.util.Rational
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.HttpDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.LoadControl
import androidx.media3.exoplayer.dash.DashChunkSource
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.dash.DefaultDashChunkSource
import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.DrmSessionManager
import androidx.media3.exoplayer.drm.FrameworkMediaDrm
import androidx.media3.exoplayer.drm.LocalMediaDrmCallback
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.trackselection.TrackSelector
import androidx.media3.exoplayer.upstream.BandwidthMeter
import androidx.media3.exoplayer.upstream.DefaultAllocator
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.navigation.navArgs
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
import com.dream.live.cricket.score.hd.streaming.adsData.NewAdManager
import com.dream.live.cricket.score.hd.streaming.models.DrmModel
import com.dream.live.cricket.score.hd.streaming.models.FormatData
import com.dream.live.cricket.score.hd.streaming.utils.Logger
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.OnHomePressedListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.adLocation2bottom
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.adLocation2topPermanent
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.location2BottomProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.location2TopPermanentProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.locationAfter
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.playerActivityInPip
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userLinkVal
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userType3
import com.dream.live.cricket.score.hd.streaming.utils.objects.DebugChecker
import com.dream.live.cricket.score.hd.streaming.utils.objects.Defamation
import com.dream.live.cricket.score.hd.streaming.utils.playerutils.PlayerScreenBottomSheet
import com.dream.live.cricket.score.hd.streaming.viewmodel.OneViewModel
import com.dream.live.cricket.score.hd.utils.InternetUtil
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.common.collect.ImmutableList
import com.google.firebase.analytics.FirebaseAnalytics
import com.p2pengine.core.p2p.EngineExceptionListener
import com.p2pengine.core.p2p.PlayerInteractor
import com.p2pengine.core.utils.EngineException
import com.p2pengine.sdk.P2pEngine
import com.dream.live.cricket.score.hd.streaming.utils.HomeWatcher
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.adBefore
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.clearKeyKey
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.dash
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.hlsSource
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.locationBeforeProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.timeValueAtPlayer
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.xForwardedKey
import com.facebook.ads.AdSettings
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.teamd2.live.football.tv.utils.AppContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


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

    private var isShowingTrackSelectionDialog = false
    private var isActivityResumed = false
    private var isChromcast = false
    private var isAdShowning = false
    val SUPPORTED_TRACK_TYPES = ImmutableList.of(
        C.TRACK_TYPE_VIDEO, C.TRACK_TYPE_AUDIO, C.TRACK_TYPE_TEXT, C.TRACK_TYPE_IMAGE
    )

    val mHomeWatcher = HomeWatcher(this)
    private var firebaseAnalytics: FirebaseAnalytics? = null

    var compareValue: Long = 0
    var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exo_test_player)
        context = this
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        AdSettings.addTestDevice("1fc9259a-88bf-4c7c-bcdc-0014d5b63674")
        if (isGooglePlayServicesAvailable(this)) {
            initializeCastSdk()
        }
        initializeFirebaseAnalytics()
        val exoView: ConstraintLayout? = binding?.playerView?.findViewById(R.id.exoControlView)
        bindingExoPlayback = exoView?.let { ExoPlaybackControlViewBinding.bind(it) }
        mLocation = PlaybackLocation.LOCAL
        setupActionBar()
//        getAppVolumeLevel()
//        getAppBrightnessLevel()
//        swipeVolumeFeature()
        changeOrientation()
//        swipeBrightnessFeature()

        getNavValues()
        checkForAds()
        screenModeController()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                if (!locationAfter.equals("none", true)) {
                    if (!locationAfter.equals(Constants.startApp, true)) {
                        if (player != null) {
                            player?.stop()
                            player!!.release()
                            player = null
                        }
                        isAdShowning = true
                        val local = AppContextProvider.getContext()
                        local?.let {
                            NewAdManager.showAds(
                                locationAfter,
                                this@PlayerScreen,
                                it
                            )
                        }
                        binding?.lottiePlayer?.visibility = View.VISIBLE
                    } else {
                        Constants.videoFinish = true
                        finish()
                    }

                } else {
                    Constants.videoFinish = true
                    finish()
                }
            }
        })
        bindingExoPlayback?.moreOption?.setOnClickListener {
            if (player != null) {
                if (!isShowingTrackSelectionDialog
                    && willHaveContent(player!!)
                ) {
                    getTracksPresentInsideUrl(player)
                }
            }
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            checkHomeButton()
        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkHomeButton() {
        try {
            mHomeWatcher.setOnHomePressedListener(object : OnHomePressedListener {
                override fun onHomePressed() {
                    // do something here...
                    if (!isInPictureInPictureMode) {
                        if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    val params = updatePictureInPictureParams()
                                    if (!isFinishing) {
                                        enterPictureInPictureMode(params)
                                    }
                                }
                            } catch (e: java.lang.Exception) {
                                val bundle = Bundle()
                                bundle.putString("pipFinish", "not Resumed")
                                firebaseAnalytics?.logEvent("pipFinish", bundle)
                                Log.d("Exception", "msg")
                            }
                        } else {
                            val bundle = Bundle()
                            bundle.putString("resume", "not Resumed")
                            firebaseAnalytics?.logEvent("resumeStatus", bundle)
                            Log.d("Exception", "msg")
                        }
                    } else {
                        val bundle = Bundle()
                        bundle.putString("pipMode", "Already")
                        firebaseAnalytics?.logEvent("pipMode", bundle)
                    }
                }

                override fun onHomeLongPressed() {
                    if (!isInPictureInPictureMode) {
                        if (isActivityResumed) {
                            if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                                try {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        val params = updatePictureInPictureParams()
                                        if (!isFinishing) {
                                            enterPictureInPictureMode(params)
                                        }
                                    }
                                } catch (e: java.lang.Exception) {
                                    val bundle = Bundle()
                                    bundle.putString("pipFinish", "not Resumed")
                                    firebaseAnalytics?.logEvent("pipFinish", bundle)
                                    Log.d("Exception", "msg")
                                }
                            } else {
                                val bundle = Bundle()
                                bundle.putString("resume", "not Resumed")
                                firebaseAnalytics?.logEvent("resumeStatus", bundle)
                                Log.d("Exception", "msg")
                            }
                        }
                    }
                }
            })
            mHomeWatcher.startWatch()
        } catch (e: java.lang.Exception) {
            val bundle = Bundle()
            bundle.putString("exception", "error" + e?.message)
            firebaseAnalytics?.logEvent("resumeStatus", bundle)
            Log.d("Exception", "msg")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updatePictureInPictureParams(): PictureInPictureParams {
        // Calculate the aspect ratio of the PiP screen.
        val bundle = Bundle()
        bundle.putString("PipMode", "okay")
        firebaseAnalytics?.logEvent("pipMode", bundle)
        // Calculate the aspect ratio of the PiP screen.
        var aspectRatio: Rational? = null
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//            aspectRatio = binding?.minView?.width?.let {
//                binding?.minView?.height?.let { it1 ->
//                    Rational(
//                        it,
//                        it1
//                    )
//                }
//            }
            aspectRatio = binding?.minView?.width?.let {
                binding?.minView?.height?.let { it1 ->
                    clampAspectRatio(
                        it,
                        it1
                    )
                }
            }
        } else {
//            aspectRatio =
//                binding?.playerView?.width?.let {
//                binding?.playerView?.height?.let { it1 ->
//                    Rational(
//                        it,
//                        it1
//                    )
//                }
//            }
            aspectRatio = binding?.playerView?.width?.let {
                binding?.playerView?.height?.let { it1 ->
                    clampAspectRatio(
                        it,
                        it1
                    )
                }
            }
        }

        binding?.myToolbar?.visibility = View.GONE
        bindingExoPlayback?.exoControlView?.visibility = View.GONE
        playerActivityInPip = true
        removeAllAdsViews()
        binding?.topAdLay?.visibility = View.GONE
        binding?.bottomAdLay?.visibility = View.GONE
        // The  view turns into the picture-in-picture mode.
        val visibleRect = Rect()
        binding?.playerView?.getGlobalVisibleRect(visibleRect)
        val params = PictureInPictureParams.Builder()
            .setAspectRatio(aspectRatio)
            // Specify the portion of the screen that turns into the picture-in-picture mode.
            // This makes the transition animation smoother.
            .setSourceRectHint(visibleRect)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // The screen automatically turns into the picture-in-picture mode when it is hidden
            // by the "Home" button.
            params.setAutoEnterEnabled(true)
        }
        return params.build().also {
            if (!isFinishing) {
                setPictureInPictureParams(it)
            }
        }
    }

    fun removeAllAdsViews() {
        binding?.adViewTop?.invalidate()
        binding?.fbAdViewTop?.invalidate()
        binding?.unityBannerView?.invalidate()
        binding?.adViewTopPermanent?.invalidate()
        binding?.adViewBottom?.invalidate()
        binding?.fbAdViewBottom?.invalidate()
        binding?.unityBannerViewBottom?.invalidate()
        if (binding?.mainTimerLay?.isVisible == true) {
            binding?.countDownTimerAd?.removeAllViews()
        }
    }


    fun clampAspectRatio(width: Int, height: Int): Rational {
        val aspectRatio = width.toFloat() / height
        return when {
            aspectRatio < 0.418410 -> Rational(41841, 100000) // Minimum allowed aspect ratio
            aspectRatio > 2.390000 -> Rational(239000, 100000) // Maximum allowed aspect ratio
            else -> Rational(width, height)
        }
    }


    private fun initializeFirebaseAnalytics() {
        try {
            firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        } catch (e: java.lang.Exception) {
            Log.d("Exception", "msg")
        }
    }

    @OptIn(UnstableApi::class)
    private fun getTracksPresentInsideUrl(player: ExoPlayer?) {

        if (player?.currentTracks != null) {
            if (!player.currentTracks.isEmpty) {
                Constants.dataFormats.clear()
                val tracks = player.currentTracks
                val videoTracks = tracks.groups.filter { it.type == C.TRACK_TYPE_VIDEO }
                Constants.dataFormats.add(FormatData(null, null))
                videoTracks.forEach { trackGroup ->
                    (0 until trackGroup.length).forEach { i ->
                        val track = trackGroup.getTrackFormat(i)
                        val format = track.height
                        val width = track.width
                        Constants.dataFormats.add(FormatData(track, trackGroup))
                    }
                }


                if (!Constants.dataFormats.isNullOrEmpty()) {
                    val fullscreenModal =
                        PlayerScreenBottomSheet(player, Constants.dataFormats.size)
                    supportFragmentManager.let {
                        fullscreenModal.show(
                            it,
                            "FullscreenModalBottomSheetDialog"
                        )
                    }
                }

            }
        }

        /////

    }


    fun willHaveContent(player: Player): Boolean {
        return willHaveContent(player.currentTracks)
    }

    fun willHaveContent(tracks: Tracks): Boolean {
        for (trackGroup in tracks.groups) {
            if (SUPPORTED_TRACK_TYPES.contains(trackGroup.type)) {
                return true
            }
        }
        return false
    }

    private fun isGooglePlayServicesAvailable(context: Context): Boolean {
        try {
            val googleApiAvailability = GoogleApiAvailability.getInstance()
            val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
            return resultCode == ConnectionResult.SUCCESS
        } catch (e: Exception) {
            return false
        }

    }

    private fun getNavValues() {
        try {
            val channelData: PlayerScreenArgs by navArgs()
            baseUrl = channelData.baseURL.toString()
            path = channelData.linkAppend.toString()
            channel_Type = channelData.channleType.toString()

            val channelTime = channelData.channelTime
            if (channelTime != null) {
                if (channelTime != compareValue) {
                    checkTimeValueAndStartTimer(channelTime)
                } else {
                    hideTimerLayout()
                    getChannelTyppeAndPlay()
                }
            } else {
                hideTimerLayout()
                getChannelTyppeAndPlay()
            }
            /////////



        } catch (e: Exception) {
            Log.d("Exception", "message : ${e.cause}")
        }
    }

    private fun checkTimeValueAndStartTimer(selectedChannelTimeInMillis: Long) {
//        dialogFragment = CountDownDialogFragment(selectedChannelTimeInMillis)
//        dialogFragment?.show(supportFragmentManager, "Count Down Fragment")
        lifecycleScope.launch(Dispatchers.Main) {
            val timerValue = Defamation.calculateDifferenceBetweenDates(selectedChannelTimeInMillis)
            val hoursMat = timerValue / (60 * 60 * 1000)
            val minutesMat = (timerValue % (60 * 60 * 1000)) / (60 * 1000)
            Log.d("Difference", "$hoursMat $minutesMat  second(s) remaining")

            if (hoursMat == compareValue && minutesMat <= timeValueAtPlayer) {
                hideTimerLayout()
                timerFinishAndPlayAgain()
            } else {
                if (timerValue > 0) {
                    showTimerLay()
                    timer = object : CountDownTimer(Math.abs(timerValue), 1000) {
                        // Callback fired on regular interval.
                        override fun onTick(millisUntilFinished: Long) {
                            val hours = millisUntilFinished / (60 * 60 * 1000)
                            val minutes = (millisUntilFinished % (60 * 60 * 1000)) / (60 * 1000)
                            val seconds = (millisUntilFinished % (60 * 1000)) / 1000
                            if (hours == compareValue && minutes <= timeValueAtPlayer) {
                                hideTimerLayout()
                                timerFinishAndPlayAgain()
                            } else {
                                binding?.timeValue?.text = "$hours : $minutes : $seconds"
                            }

                            Log.d("Difference", "$hours $minutes $seconds second(s) remaining")
                        }

                        // Callback fired when the time is up.
                        override fun onFinish() {
                            hideTimerLayout()
                            timerFinishAndPlayAgain()
                            Log.d("Difference", "Done!!!")
                        }
                    }.start() // Start the countdown timer
                } else {
                    hideTimerLayout()
                    timerFinishAndPlayAgain()
                }
            }
        }
    }

    private fun hideTimerLayout() {
        binding?.playerView?.visibility = View.VISIBLE
        timer?.cancel()
        binding?.mainTimerLay?.visibility = View.GONE
    }


    private fun timerFinishAndPlayAgain() {
        getChannelTyppeAndPlay()
    }

    private fun showTimerLay() {
        binding?.mainTimerLay?.visibility = View.VISIBLE
        if (binding?.mainTimerLay?.isVisible == true) {
            loadLocation2TopAtTimerScreen()
        }
    }

    private fun loadLocation2TopAtTimerScreen() {
        if (!location2TopPermanentProvider.equals("none", true)) {
            adManager?.loadAdProvider(
                location2TopPermanentProvider, adLocation2topPermanent,
                binding?.countDownTimerAd, null, null,null
            )
        }

    }

    private fun getChannelTyppeAndPlay() {

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
        }
        else if (channel_Type.equals(dash, true)) {
            if (path.isNotEmpty()) {
                setUpPlayerDashWithAgent(path)
            }
        }
        else if (channel_Type.equals(hlsSource, true)){
            if (path.isNotEmpty()){
                setUpPlayerWithM3u8(path)
            }
        }
        else if (channel_Type.equals(userType3, true)) {
            if (path.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.Main) {
                    setUpPlayerP2p(path)
                }
            }
        } else {
            setUpPlayer(path)
        }
    }

    @OptIn(UnstableApi::class)
    private fun setUpPlayerWithM3u8(path: String) {

        binding?.lottiePlayer?.visibility = View.GONE
        val listDrmModel: MutableList<DrmModel> = mutableListOf()


        // Setup DefaultDrmSessionManager for ClearKey
//        val keyId = "9002ec8c3dbc55c5bccdcd6871d80fd0".toByteArray(Charsets.UTF_8)
//        val key = "7099325123bae7810db508727bb0bc7d".toByteArray(Charsets.UTF_8)

        val drmKey = clearKeyKey

//        val drmKeyId =
//            "8ab47741930c476780515f9a00decb0a"

        var drmBody = ""

        if (drmKey.contains(":")) {
            val elementsKey = drmKey.split(":") // Split the string by commas
//            val elementsKeyId = drmKeyId.split(",") // Split the string by commas

            var length = elementsKey.size + 1
            var finalLen = length / 2

            var count = 0
            for (element in 0 until finalLen) {

                val drmKeyIdBytes =
                    elementsKey[count].chunked(2).map { it.toInt(16).toByte() }.toByteArray()
                val encodedDrmKeyId = Base64.encodeToString(
                    drmKeyIdBytes, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP
                )

                count += 1

                val drmKeyBytes =
                    elementsKey[count].chunked(2).map { it.toInt(16).toByte() }.toByteArray()
                val encodedDrmKey = Base64.encodeToString(
                    drmKeyBytes, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP
                )

                count += 1



                listDrmModel.add(DrmModel("oct", encodedDrmKey, encodedDrmKeyId))
            }

            val jsonObject = JsonObject()
            val jsonArray = JsonArray()

            for (keyData in listDrmModel) {
                val keyJsonObject = JsonObject()
                keyJsonObject.addProperty("kty", keyData.ktyString)
                keyJsonObject.addProperty("k", keyData.drmKey)
                keyJsonObject.addProperty("kid", keyData.drmKeyId)
                jsonArray.add(keyJsonObject)
            }

            jsonObject.add("keys", jsonArray)
            jsonObject.addProperty("type", "temporary")

            val gson = Gson()
            val jsonString = gson.toJson(jsonObject)

            drmBody = jsonString

//            Log.d(
//                "DrmBoday", "body" +drmBody
//            )
        } else {

        }



        val dashMediaItem = MediaItem.Builder().setUri(path).setMimeType(MimeTypes.APPLICATION_M3U8)
            .setMediaMetadata(MediaMetadata.Builder().setTitle("Live Football Tv Teamd2").build())
            .build()

        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory()
        val trackSelector = DefaultTrackSelector(this, videoTrackSelectionFactory)
        trackSelector.setParameters(
            trackSelector.buildUponParameters().setPreferredVideoMimeType(MimeTypes.VIDEO_H264)
        )
        /////////
//        val trackSelector = DefaultTrackSelector(this)
        val loadControl = DefaultLoadControl()

        val drmCallback = LocalMediaDrmCallback(drmBody.toByteArray())
        val drmSessionManager =
            DefaultDrmSessionManager.Builder().setPlayClearSamplesWithoutKeys(true)
                .setMultiSession(false).setKeyRequestParameters(HashMap())
                .setUuidAndExoMediaDrmProvider(C.CLEARKEY_UUID, FrameworkMediaDrm.DEFAULT_PROVIDER)
                .build(drmCallback)

        val customDrmSessionManager: DrmSessionManager = drmSessionManager
        ///////////////
        val defaultHttpDataSourceFactory =
            DefaultHttpDataSource.Factory().setUserAgent(Constants.USER_AGENT).setTransferListener(
                DefaultBandwidthMeter.Builder(this).setResetOnNetworkTypeChange(false).build()
            )


        val dashChunkSourceFactory: DashChunkSource.Factory = DefaultDashChunkSource.Factory(
            defaultHttpDataSourceFactory
        )
        val manifestDataSourceFactory =
            DefaultHttpDataSource.Factory().setUserAgent(Constants.USER_AGENT)

        var dataSourceFactory: DataSource.Factory? = null


        if (xForwardedKey != null) {
            if (xForwardedKey.isNotEmpty()) {
                dataSourceFactory = DefaultDataSource.Factory(
                    this, CustomHttpDataSourceFactoryWithHeader()
                )

            } else {
                dataSourceFactory = DefaultDataSource.Factory(
                    this, CustomHttpDataSourceFactory(Constants.USER_AGENT)
                )
            }
        } else {
            dataSourceFactory = DefaultDataSource.Factory(
                this, CustomHttpDataSourceFactory(Constants.USER_AGENT)
            )
        }

        var mediaSource: MediaSource?=null


        if (clearKeyKey.isNullOrEmpty()){
            Log.d("ComingInSection","yes")
            mediaSource =
                dataSourceFactory?.let {
                    HlsMediaSource.Factory(dataSourceFactory)
//                    .setDrmSessionManagerProvider { customDrmSessionManager }
                        .createMediaSource(dashMediaItem)
                }
        }
        else{
            Log.d("ComingInSection","no")

            mediaSource =
                dataSourceFactory?.let {
                    HlsMediaSource.Factory(dataSourceFactory)
                        .setDrmSessionManagerProvider { customDrmSessionManager }
                        .createMediaSource(dashMediaItem)
                }
        }


        val renderersFactory =
            DefaultRenderersFactory(this).setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF)

        ////////
        val renderersFactory2 =
            DefaultRenderersFactory(this).setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
                .setEnableDecoderFallback(true)

        player = ExoPlayer.Builder(this, renderersFactory2).setTrackSelector(trackSelector)
            .setLoadControl(loadControl)
//            .setSeekForwardIncrementMs(10000L)
//            .setSeekBackIncrementMs(10000L)
            .build()

        binding?.playerView?.player = player
        binding?.playerView?.keepScreenOn = true

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding?.playerView?.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            bindingExoPlayback?.fullScreenIcon?.setImageDrawable(context?.let {
                ContextCompat.getDrawable(
                    it, R.drawable.ic_full_screen
                )
            })
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
                mPlaybackState = PlaybackState.IDLE

                if (player != null) {
                    player?.addListener(this)
                    mediaSource?.let { player?.setMediaSource(it, true) }
                    player?.prepare()
                    binding?.playerView?.requestFocus()
                    player?.playWhenReady = true
                }

            }

            PlaybackLocation.REMOTE -> {
                mCastSession?.remoteMediaClient?.play()
                mPlaybackState = PlaybackState.PLAYING
            }

            else -> {
            }
        }
        ////////////
//        setOnGestureListeners()
    }

    @OptIn(UnstableApi::class)
    private fun setUpPlayerDashWithAgent(path: String) {

        binding?.lottiePlayer?.visibility = View.GONE
        val listDrmModel: MutableList<DrmModel> = mutableListOf()


        // Setup DefaultDrmSessionManager for ClearKey
//        val keyId = "9002ec8c3dbc55c5bccdcd6871d80fd0".toByteArray(Charsets.UTF_8)
//        val key = "7099325123bae7810db508727bb0bc7d".toByteArray(Charsets.UTF_8)

        val drmKey = clearKeyKey

//        val drmKeyId =
//            "8ab47741930c476780515f9a00decb0a"

        var drmBody = ""

        if (drmKey.contains(":")) {
            val elementsKey = drmKey.split(":") // Split the string by commas
//            val elementsKeyId = drmKeyId.split(",") // Split the string by commas

            var length = elementsKey.size + 1
            var finalLen = length / 2

            var count = 0
            for (element in 0 until finalLen) {

                val drmKeyIdBytes =
                    elementsKey[count].chunked(2).map { it.toInt(16).toByte() }.toByteArray()
                val encodedDrmKeyId = Base64.encodeToString(
                    drmKeyIdBytes, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP
                )

                count += 1

                val drmKeyBytes =
                    elementsKey[count].chunked(2).map { it.toInt(16).toByte() }.toByteArray()
                val encodedDrmKey = Base64.encodeToString(
                    drmKeyBytes, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP
                )

                count += 1



                listDrmModel.add(DrmModel("oct", encodedDrmKey, encodedDrmKeyId))
            }

            val jsonObject = JsonObject()
            val jsonArray = JsonArray()

            for (keyData in listDrmModel) {
                val keyJsonObject = JsonObject()
                keyJsonObject.addProperty("kty", keyData.ktyString)
                keyJsonObject.addProperty("k", keyData.drmKey)
                keyJsonObject.addProperty("kid", keyData.drmKeyId)
                jsonArray.add(keyJsonObject)
            }

            jsonObject.add("keys", jsonArray)
            jsonObject.addProperty("type", "temporary")

            val gson = Gson()
            val jsonString = gson.toJson(jsonObject)

            drmBody = jsonString

//            Log.d(
//                "DrmBoday", "body" +drmBody
//            )
        } else {
//            val drmKeyBytes =
//                drmKey.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
//            val encodedDrmKey = Base64.encodeToString(
//                drmKeyBytes,
//                Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP
//            )
//
//            val drmKeyIdBytes = drmKeyId.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
//            val encodedDrmKeyId = Base64.encodeToString(
//                drmKeyIdBytes,
//                Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP
//            )
//            drmBody =
//                "{\"keys\":[{\"kty\":\"oct\",\"k\":\"${encodedDrmKey}\",\"kid\":\"${encodedDrmKeyId}\"}],\"type\":\"temporary\"}"
        }


//        val drmKeyIdBytes = drmKeyId.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
//        val encodedDrmKeyId = Base64.encodeToString(
//            drmKeyIdBytes,
//            Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP
//        )


        val dashMediaItem = MediaItem.Builder().setUri(path).setMimeType(MimeTypes.APPLICATION_MPD)
            .setMediaMetadata(MediaMetadata.Builder().setTitle("Live Football TV").build())
            .build()

        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory()
        val trackSelector = DefaultTrackSelector(this, videoTrackSelectionFactory)
        trackSelector.setParameters(
            trackSelector.buildUponParameters().setPreferredVideoMimeType(MimeTypes.VIDEO_H264)
        )
        /////////
//        val trackSelector = DefaultTrackSelector(this)
        val loadControl = DefaultLoadControl()

        val drmCallback = LocalMediaDrmCallback(drmBody.toByteArray())
        val drmSessionManager =
            DefaultDrmSessionManager.Builder().setPlayClearSamplesWithoutKeys(true)
                .setMultiSession(false).setKeyRequestParameters(HashMap())
                .setUuidAndExoMediaDrmProvider(C.CLEARKEY_UUID, FrameworkMediaDrm.DEFAULT_PROVIDER)
                .build(drmCallback)

        val customDrmSessionManager: DrmSessionManager = drmSessionManager
        ///////////////
        val defaultHttpDataSourceFactory =
            DefaultHttpDataSource.Factory().setUserAgent(Constants.USER_AGENT).setTransferListener(
                DefaultBandwidthMeter.Builder(this).setResetOnNetworkTypeChange(false).build()
            )


        val dashChunkSourceFactory: DashChunkSource.Factory = DefaultDashChunkSource.Factory(
            defaultHttpDataSourceFactory
        )
        val manifestDataSourceFactory =
            DefaultHttpDataSource.Factory().setUserAgent(Constants.USER_AGENT)


        // Create a custom SSL context with the provided certificate
//        val trustManager = object : X509TrustManager {
//            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
//            }
//
//            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
//            }
//
//            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
//        }
        // Set up the SSL context to trust all certificates
//        val sslContext = SSLContext.getInstance("TLS")
//        sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
        ///////////////////////////////////////////////////////////////////////////////
        //Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate>? {
                return null
            }

            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {
                //
            }

            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {
                //
            }
        })
        //Install the all-trusting trust manager
//        // Create a DataSource factory using OkHttp
//        val dataSourceFactory = OkHttpDataSourceFactory(client)
//
//        // 5. Create a custom DataSource.Factory
//        val dataSourceFactoryManifest = DataSource.Factory {
//            val defaultHttpDataSource = DefaultHttpDataSource.Factory()
//                .setSslSocketFactory(sslContext.socketFactory)
//                .createDataSource()
//            defaultHttpDataSource
//        }

        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, trustAllCerts, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
        } catch (e: KeyManagementException) {
            Log.d("Error", "KeyManagementException")
        } catch (e: NoSuchAlgorithmException) {
            Log.d("Error", "KeyManagementException")

        }
        var dataSourceFactory: DataSource.Factory? = null

        //Below lines must be commentted on Live ID
        // xForwardedKey ="13.106.174.0"
        //  xForwardedKey = "49.50.223.255"

        if (xForwardedKey != null) {
            if (xForwardedKey.isNotEmpty()) {
                dataSourceFactory = DefaultDataSource.Factory(
                    this, CustomHttpDataSourceFactoryWithHeader()
                )

            } else {
                dataSourceFactory = DefaultDataSource.Factory(
                    this, CustomHttpDataSourceFactory(Constants.USER_AGENT)
                )
            }
        } else {
            dataSourceFactory = DefaultDataSource.Factory(
                this, CustomHttpDataSourceFactory(Constants.USER_AGENT)
            )
        }

//        val headersMap = HashMap<String, String>()
//        headersMap.put("User-Agent", Constants.USER_AGENT)
//        headersMap.put("x-forwarded-for", "49.50.223.255")


        val dashMediaSource = dataSourceFactory?.let {
            DashMediaSource.Factory(it).setDrmSessionManagerProvider { customDrmSessionManager }
                .createMediaSource(dashMediaItem)
        }
        //        DataSource.Factory dataSourceFactory = DemoUtil.getDataSourceFactory(PlayerActivity.this,headersMap);

        ///////////////


        //////////////
//        val mediaSourceFactory = DefaultMediaSourceFactory(this)
//            .setDrmSessionManagerProvider { customDrmSessionManager }
//            .createMediaSource(dashMediaItem)

        val renderersFactory =
            DefaultRenderersFactory(this).setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF)

        ////////
        val renderersFactory2 =
            DefaultRenderersFactory(this).setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
                .setEnableDecoderFallback(true)

        player = ExoPlayer.Builder(this, renderersFactory2).setTrackSelector(trackSelector)
            .setLoadControl(loadControl)
//            .setSeekForwardIncrementMs(10000L)
//            .setSeekBackIncrementMs(10000L)
            .build()

        binding?.playerView?.player = player
        binding?.playerView?.keepScreenOn = true

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding?.playerView?.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            bindingExoPlayback?.fullScreenIcon?.setImageDrawable(context?.let {
                ContextCompat.getDrawable(
                    it, R.drawable.ic_full_screen
                )
            })
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
                mPlaybackState = PlaybackState.IDLE

                if (player != null) {
                    player?.addListener(this)
                    dashMediaSource?.let { player?.setMediaSource(it, true) }
                    player?.prepare()
                    binding?.playerView?.requestFocus()
                    player?.playWhenReady = true
                }

            }

            PlaybackLocation.REMOTE -> {
                mCastSession?.remoteMediaClient?.play()
                mPlaybackState = PlaybackState.PLAYING
            }

            else -> {
            }
        }
        ////////////
//        setOnGestureListeners()
    }

    @UnstableApi
    class CustomHttpDataSourceFactoryWithHeader(
    ) : HttpDataSource.Factory {
        override fun createDataSource(): HttpDataSource {
            val dataSource = DefaultHttpDataSource.Factory().createDataSource()
            dataSource.setRequestProperty("X-Forwarded-For", xForwardedKey)
            return dataSource
        }

        override fun setDefaultRequestProperties(defaultRequestProperties: MutableMap<String, String>): HttpDataSource.Factory {
            TODO("Not yet implemented")
        }
    }


    @UnstableApi
    class CustomHttpDataSourceFactory(
        private val userAgent: String,
    ) : HttpDataSource.Factory {
        override fun createDataSource(): HttpDataSource {
            val dataSource = DefaultHttpDataSource.Factory().createDataSource()
            dataSource.setRequestProperty("User-Agent", userAgent)
            return dataSource
        }

        override fun setDefaultRequestProperties(defaultRequestProperties: MutableMap<String, String>): HttpDataSource.Factory {
            TODO("Not yet implemented")
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

        if (locationBeforeProvider.equals(Constants.startApp, true)) {
            adManager?.loadAdProvider(
                locationBeforeProvider, adBefore,
                null, null, null, null
            )
        }
        if (binding?.mainTimerLay?.isVisible != true) {
            loadLocation2TopPermanentProvider()
            loadLocation2BottomProvider()
        }
    }

    private fun loadLocation2BottomProvider() {
        if (!location2BottomProvider.equals("none", true)) {
            binding?.adViewBottom?.let { it1 ->
                binding?.fbAdViewBottom?.let { it2 ->
                    adManager?.loadAdProvider(
                        location2BottomProvider, adLocation2bottom,
                        it1, it2, binding?.unityBannerViewBottom, binding?.startAppBannerBottom
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


    @OptIn(UnstableApi::class)
    private fun setUpPlayerP2p(link: String?) {

        try {
            logger.printLog(tAG, "setUpPlayer P2p")
            val parsedUrl = P2pEngine.getInstance()?.parseStreamUrl(link!!)

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
                .setUri(parsedUrl)
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

                        P2pEngine.getInstance()?.setPlayerInteractor(object : PlayerInteractor() {
                            override fun onBufferedDuration(): Long {
                                return if (player != null) {
                                    player!!.bufferedPosition - player!!.currentPosition
                                } else {
                                    -1
                                }
                            }
                        })

                        P2pEngine.getInstance()?.registerExceptionListener(object :
                            EngineExceptionListener {
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
                        })


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
        } catch (e: Exception) {
            Log.d("Exception", "msg")
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
        bindingExoPlayback?.liveTxt?.visibility = value
        bindingExoPlayback?.liveShape?.visibility = value
    }


    private fun initializeCastSdk() {
        try {
            mCastContext = CastContext.getSharedInstance(this)
            mCastSession = mCastContext!!.sessionManager.currentCastSession
            setupCastListener()
            mCastContext?.sessionManager?.addSessionManagerListener(
                mSessionManagerListener!!, CastSession::class.java
            )
        } catch (e: Exception) {
            Log.d("CastSdk", "msg")
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
                            } catch (e: Exception) {
                                Log.d("Exception", "message")
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
        playerActivityInPip = false
        try {
            mCastContext!!.sessionManager.removeSessionManagerListener(
                mSessionManagerListener!!, CastSession::class.java
            )
            orientationEventListener?.disable()
            if (player != null) {
                player!!.release()
                player = null
                if (P2pEngine.getInstance()?.isConnected == true) {
                    P2pEngine.getInstance()?.stopP2p()
                }
            }
            val bundle = Bundle()
            bundle.putString("Destroy", "yes")
            firebaseAnalytics?.logEvent("onDestroy", bundle)
            mHomeWatcher?.stopWatch()
            timer?.cancel()
            finish()
        } catch (e: java.lang.Exception) {
            val bundle = Bundle()
            bundle.putString("Destroy", "Exception")
            firebaseAnalytics?.logEvent("onDestroy", bundle)
            Log.d("Exception", "msg")
        }
    }

    ///
    @OptIn(UnstableApi::class)
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
            }
            else if (channel_Type.equals(hlsSource, true)){
                if (path.isNotEmpty()){
                    setUpPlayerWithM3u8(path)
                }
            }
            else if (channel_Type.equals(dash, true)) {
                if (path.isNotEmpty()) {
                    setUpPlayerDashWithAgent(path)
                }
            }
            else {
                val token = baseUrl.let { it1 -> Defamation.improveDeprecatedCode(it1) }
                path = baseUrl + token
                setUpPlayer(path)
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


    @OptIn(UnstableApi::class)
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
                isChromcast = true
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
        try {
            isActivityResumed = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (!isInPictureInPictureMode) {
                        if (player != null) {
                            player!!.playWhenReady = false
                        }
                    } else {
//                removeAllAdsViews()
                        binding?.myToolbar?.visibility = View.GONE
                        bindingExoPlayback?.exoControlView?.visibility = View.GONE
                    }
                } else {
                    if (player != null) {
                        player!!.playWhenReady = false
                    }
                }
            } else {
                if (player != null) {
                    player!!.playWhenReady = false
                }
            }
        } catch (e: java.lang.Exception) {
            Log.d("Exception", "msg")
        }
    }

    override fun onResume() {
        super.onResume()
        isActivityResumed = true
        playerActivityInPip = false
        isAdShowning = false
        NewAdManager.setAdManager(this)
        isChromcast = false
        binding?.myToolbar?.visibility = View.VISIBLE
        bindingExoPlayback?.exoControlView?.visibility = View.VISIBLE
        binding?.topAdLay?.visibility = View.VISIBLE
        binding?.bottomAdLay?.visibility = View.VISIBLE
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
                mSessionManagerListener!!, CastSession::class.java
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

                updatePlaybackLocation(PlaybackLocation.LOCAL)
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

    @OptIn(UnstableApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        try {
            if (isInPictureInPictureMode) {
                // Hide the controls in picture-in-picture mode.
//            binding?.playerView?.hideController()
                binding?.myToolbar?.visibility = View.GONE
//            removeAllAdsViews()
            } else {
                // Show the video controls if the video is not playing
//            binding?.playerView?.showController()
                binding?.topAdLay?.visibility = View.VISIBLE
                binding?.bottomAdLay?.visibility = View.VISIBLE
                val orientation = resources.configuration.orientation
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    againFillAdViewsIfPortrait()
                }
                if (binding?.mainTimerLay?.isVisible == true) {
                    loadLocation2TopAtTimerScreen()
                }
                binding?.myToolbar?.visibility = View.VISIBLE
                bindingExoPlayback?.exoControlView?.visibility = View.VISIBLE
            }
        } catch (e: java.lang.Exception) {
            Log.d("Exception", "msg")
        }
    }

    private fun againFillAdViewsIfPortrait() {
        loadLocation2TopPermanentProvider()
        loadLocation2BottomProvider()
    }

    @OptIn(UnstableApi::class)
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (binding?.playerView != null) {
            val orientation = newConfig.orientation

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                if (binding?.mainTimerLay?.isVisible == true) {
                    binding?.mainTimerLay?.background =
                        ContextCompat.getDrawable(this, R.drawable.count_down)
                    val params =
                        binding?.timeValue?.layoutParams as ViewGroup.MarginLayoutParams // Cast to MarginLayoutParams

                    params.setMargins(
                        0,
                        0,
                        0,
                        700
                    ) // Set margins (left, top, right, bottom) in pixels
                    binding?.timeValue?.layoutParams = params
                }

                if (binding?.mainTimerLay?.isVisible != true) {
                    binding?.adViewTop?.removeAllViews()
                    binding?.fbAdViewTop?.removeAllViews()
                    binding?.unityBannerView?.removeAllViews()
                    binding?.startAppBannerTop?.removeAllViews()

                    loadLocation2TopPermanentProvider()
                    loadLocation2BottomProvider()
                }

                bindingExoPlayback?.fullScreenIcon?.visibility = View.GONE

                binding?.playerView?.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (binding?.mainTimerLay?.isVisible == true) {
                    binding?.mainTimerLay?.background =
                        ContextCompat.getDrawable(this, R.drawable.count_down_land)
                    val params =
                        binding?.timeValue?.layoutParams as ViewGroup.MarginLayoutParams // Cast to MarginLayoutParams

                    params.setMargins(
                        0,
                        0,
                        0,
                        230
                    ) // Set margins (left, top, right, bottom) in pixels
                    binding?.timeValue?.layoutParams = params
                }
                //
                if (binding?.mainTimerLay?.isVisible != true) {
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
                                    it1, it2, binding?.unityBannerView, binding?.startAppBannerTop
                                )
                            }
                        }
                    }
                }

                if (bindingExoPlayback?.lockAffect?.visibility == View.VISIBLE) {
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

    override fun onStop() {
        super.onStop()
        try {
            playerActivityInPip = false
//            Log.d("ReleaseVersion","val"+android.os.Build.VERSION.SDK_INT)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
                val bundle = Bundle()
                bundle.putString("onStop", "Api level above 12")
                firebaseAnalytics?.logEvent("onStop", bundle)

                mCastContext?.sessionManager?.removeSessionManagerListener(
                    mSessionManagerListener!!, CastSession::class.java
                )
                if (player != null) {
                    P2pEngine.getInstance()?.stopP2p()
                    player?.stop()
                    player?.release()
                    player = null
                }
                if (isChromcast) {
                    val bundle = Bundle()
                    bundle.putString("Chromcast", "yes")
                    firebaseAnalytics?.logEvent("onStop", bundle)
                } else {
                    if (!isAdShowning) {
                        mHomeWatcher?.stopWatch()
                        val bundle = Bundle()
                        bundle.putString("Chromcast", "false")
                        firebaseAnalytics?.logEvent("onStop", bundle)
                        if (binding?.mainTimerLay?.isVisible == true) {
                            timer?.cancel()
                        }
                        orientationEventListener?.disable()
                        finish()
                    } else {
                        val bundle = Bundle()
                        bundle.putString("AdShowing", "yes")
                        firebaseAnalytics?.logEvent("onStop", bundle)
                    }
                }
                ///if api level is
            } else {
                val bundle = Bundle()
                bundle.putString("onStop", "Api level below 12")
                firebaseAnalytics?.logEvent("onStop", bundle)

                mCastContext?.sessionManager?.removeSessionManagerListener(
                    mSessionManagerListener!!, CastSession::class.java
                )
                if (player != null) {
                    P2pEngine.getInstance()?.stopP2p()
//                    player?.stop()
                }
            }
            ///////////////////
        } catch (e: java.lang.Exception) {
            val bundle = Bundle()
            bundle.putString("onStop", "Exception")
            firebaseAnalytics?.logEvent("onStop", bundle)
            Log.d("Exception", "msg")
        }
    }

    ////Listener for castSession manager
    private fun setupCastListener() {
        if (mSessionManagerListener == null) {

            mSessionManagerListener = object : SessionManagerListener<CastSession> {

                override fun onSessionStarting(castSession: CastSession) {
                }

                override fun onSessionStarted(castSession: CastSession, s: String) {
                    onApplicationConnected(castSession)
                }

                override fun onSessionStartFailed(castSession: CastSession, i: Int) {
                    logger.printLog(tAG, "playback")

                    onApplicationDisconnected()
                }

                override fun onSessionEnding(castSession: CastSession) {

                }

                override fun onSessionEnded(castSession: CastSession, i: Int) {
                    onApplicationDisconnected()
                }

                override fun onSessionResuming(castSession: CastSession, s: String) {}
                override fun onSessionResumed(castSession: CastSession, b: Boolean) {
//                    invalidateOptionsMenu()
//                    onApplicationConnected(castSession)
                }

                override fun onSessionResumeFailed(castSession: CastSession, i: Int) {

                    onApplicationDisconnected()
                    Log.d("castDisconnectionIssue", castSession.toString())
                }

                override fun onSessionSuspended(castSession: CastSession, i: Int) {

                }

                private fun onApplicationConnected(castSession: CastSession) {
                    mCastSession = castSession
                    if (mPlaybackState != PlaybackState.PLAYING) {
                        player?.pause()
                        loadRemoteMedia()
                        return
                    } else {
                        mPlaybackState = PlaybackState.IDLE
                        updatePlaybackLocation(PlaybackLocation.REMOTE)
                    }
                    invalidateOptionsMenu()
                }

                private fun onApplicationDisconnected() {
                    updatePlaybackLocation(PlaybackLocation.LOCAL)
                    mPlaybackState = PlaybackState.IDLE
                    mLocation = PlaybackLocation.LOCAL
                    invalidateOptionsMenu()
                }

            }
        }
    }


    private fun updatePlaybackLocation(location: PlaybackLocation) {
        mLocation = location
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
        updatePlaybackLocation(PlaybackLocation.LOCAL)
        mPlaybackState = PlaybackState.IDLE
        mLocation = PlaybackLocation.LOCAL
        invalidateOptionsMenu()
    }

    @OptIn(UnstableApi::class)
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

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (P2pEngine.getInstance()?.isConnected == true)
            P2pEngine.getInstance()?.stopP2p()
    }

}