package com.dream.live.cricket.score.hd.streaming.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dream.live.cricket.score.hd.BuildConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.dream.live.cricket.score.hd.MainActivity
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.ActivitySplashBinding
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.scores.utility.Cons.base_url_scores
import com.dream.live.cricket.score.hd.scores.utility.listeners.ApiResponseListener
import com.dream.live.cricket.score.hd.streaming.adsData.GoogleMobileAdsConsentManager
import com.dream.live.cricket.score.hd.streaming.adsData.NewAdManager
import com.dream.live.cricket.score.hd.streaming.date.ScreenRotation
import com.dream.live.cricket.score.hd.streaming.date.ScreenUtil
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.DialogListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.authToken
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.baseUrlChannel
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.cementData
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.cementMainData
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.cementMainType
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.cementType
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.emptyCheck
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.locationAfter
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.locationBeforeProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.middleAdProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.passVal
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.rateShown
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.splash_status
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.tapPositionProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.CustomDialogue
import com.dream.live.cricket.score.hd.streaming.utils.objects.DebugChecker
import com.dream.live.cricket.score.hd.streaming.utils.objects.SharedPreference
import com.dream.live.cricket.score.hd.streaming.viewmodel.OneViewModel
import com.dream.live.cricket.score.hd.utils.InternetUtil
import com.dream.live.cricket.score.hd.utils.InternetUtil.isPrivateDnsSetup
import com.getkeepsafe.relinker.ReLinker
import com.teamd2.live.football.tv.utils.AppContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

///Home Screen ....
class HomeScreen : AppCompatActivity(), DialogListener ,ApiResponseListener{
    private external fun getStringArray1(): Array<String?>?
    private external fun getStringArray2(): Array<String?>?
    private external fun getStringArray3(): Array<String?>?
    private external fun getStringArray4(): Array<String?>?
    private external fun getStringArray5(): Array<String?>?
    private external fun getStringArray6(): Array<String?>?
    private external fun getStringArray7(): Array<String?>?
    private external fun getStringArray8(): Array<String?>?
    private external fun getStringArray9(): Array<String?>?
    private external fun getStringArray10(): Array<String?>?
    private external fun getStringArray11(): Array<String?>?
    private external fun getStringArray12(): Array<String?>?
    private external fun getStringArray13(): Array<String?>?
    private external fun getStringArray14(): Array<String?>?
    private external fun getStringArray15(): Array<String?>?
    private external fun getStringArray16(): Array<String?>?
    private external fun getStringArray17(): Array<String?>?
    private external fun getStringArray18(): Array<String?>?
    private external fun getStringArray19(): Array<String?>?
    private external fun getStringArray20(): Array<String?>?
    private external fun getStringArray21(): Array<String?>?
    private external fun getStringArray22(): Array<String?>?
    private external fun getStringArray23(): Array<String?>?
    private external fun getStringArray24(): Array<String?>?
    private external fun getStringArray25(): Array<String?>?
    private external fun getStringArray26(): Array<String?>?
    private external fun getStringArray27(): Array<String?>?
    private external fun getStringArray28(): Array<String?>?
    private external fun getStringArray29(): Array<String?>?
    private external fun getStringArray30(): Array<String?>?
    private external fun getStringArray31(): Array<String?>?
    private external fun getStringArray32(): Array<String?>?
    private external fun getStringArray33(): Array<String?>?
    private external fun getStringArray34(): Array<String?>?
    private external fun getStringArray35(): Array<String?>?
    private external fun getStringArray36(): Array<String?>?
    private external fun getStringArray37(): Array<String?>?
    private external fun getStringArray38(): Array<String?>?
    private external fun getStringArray39(): Array<String?>?
    private external fun getStringArray40(): Array<String?>?
    private var bindingHome: ActivitySplashBinding? = null
    private var preference: SharedPreference? = null
    private var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager? = null
    private var replaceChar = "mint"
    private val screenUtil = ScreenUtil()
    private val viewModel by lazy {
        ViewModelProvider(this)[OneViewModel::class.java]
    }
    private var ifPermissionGrantedThenNotResume=false

    private var permissionCount = 0
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                ifPermissionGrantedThenNotResume=true
                bindingHome?.notificationLayout?.visibility = View.GONE
                // Permission is granted. Continue the action or workflow in your
                subscribeOrUnSubscribeTopic()

            } else {
                ifPermissionGrantedThenNotResume=true
                bindingHome?.notificationLayout?.visibility = View.VISIBLE
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingHome = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        //Initialize firebase instance...
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        window.navigationBarColor = ContextCompat.getColor(this, R.color.noChange)
        preference = SharedPreference(this)
        FirebaseApp.initializeApp(this)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (bindingHome?.notificationLayout?.isVisible == true || bindingHome?.noInternetMainLayout?.isVisible == true) {
                    finishAffinity()
                } else {

                }
            }
        })
        Constants.app_update_dialog=false
        splash_status =false
        rateShown =false
        ifPermissionGrantedThenNotResume=false
        viewModel?.apiResponseListener=this
        bindingHome?.retry?.setOnClickListener {
            bindingHome?.homeAnimLayout?.visibility = View.VISIBLE
            if (InternetUtil.isInternetOn(this)) {
                bindingHome?.noInternetMainLayout?.visibility = View.GONE
                Handler(Looper.getMainLooper()).postDelayed({
                    if (!DebugChecker.checkDebugging(this)) {
                        emulatorCheck()
                    }
                }, 2000)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    bindingHome?.homeAnimLayout?.visibility = View.GONE
                }, 2000)
            }
        }

        bindingHome?.yesBtn?.setOnClickListener {
            bindingHome?.notificationLayout?.visibility = View.GONE
            makePermission()
        }
        bindingHome?.skipBtn?.setOnClickListener {

//            requestPermissionLauncher.unregister()
            bindingHome?.notificationLayout?.visibility = View.GONE
            navigationToNextScreen()
        }
    }

    private fun checkNotificationPermission() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // You can use the API that requires the permission.
                    subscribeOrUnSubscribeTopic()

                }

                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
                -> {
                    bindingHome?.notificationLayout?.visibility = View.VISIBLE

                }

                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    makePermission()

                }

            }
        } else {
            subscribeOrUnSubscribeTopic()
        }


    }

    private fun makePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (permissionCount > 3) {
                bindingHome?.notificationLayout?.visibility = View.GONE
                navigationToNextScreen()

            } else if (permissionCount == 2) {
                bindingHome?.notificationLayout?.visibility = View.GONE

                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts(
                    "package",
                    packageName, null
                )
                intent.data = uri
                startActivity(intent)
            } else {
                requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }

            permissionCount++

        }
    }


    private fun subscribeOrUnSubscribeTopic() {

        val getStatus = preference?.getBool(Constants.preferenceKey)

        val modeValue = preference?.getString(Constants.preferenceMode)

        if (modeValue.equals("dark", true)) {

            Constants.modeCheckValue = "dark"
        } else {
            Constants.modeCheckValue = "light"

        }
        if (getStatus == true) {
            navigationToNextScreen()
            ///means already subscribe to topic...
        } else {


            FirebaseMessaging.getInstance().subscribeToTopic("event")
                .addOnCompleteListener { task ->
                    if (task.isComplete) {
                        //
                        preference?.saveBool(Constants.preferenceKey, true)

                    }
                }

            navigationToNextScreen()
        }


    }

    override fun onResume() {
        super.onResume()
        if (!ifPermissionGrantedThenNotResume) {
            showConsentDialog()
        }
    }

    private fun showConsentDialog() {
        val getConsentStatus = preference?.getConsentStatus(Constants.consentKey)
        if (getConsentStatus == true) {
            checkNecessities()
        } else {
            googleMobileAdsConsentManager = GoogleMobileAdsConsentManager.getInstance(this)
            googleMobileAdsConsentManager?.gatherConsent(this) { consentError ->
                if (consentError != null) {
                    preference?.saveConsent(Constants.consentKey, true)
                    checkNecessities()
                    Log.d("UmpDataCollected", "ump" + consentError.message)
                }

                if (googleMobileAdsConsentManager?.canRequestAds == true) {
                    preference?.saveConsent(Constants.consentKey, true)
                    Log.d("UmpDataCollected", "yes")
                    checkNecessities()
//                initializeMobileAdsSdk()
                } else {
                    //
                    checkNecessities()
                    Log.d("UmpDataCollected", "yes")
                }
//            if (googleMobileAdsConsentManager?.isPrivacyOptionsRequired == true) {
//                // Regenerate the options menu to include a privacy setting.
//                invalidateOptionsMenu()
//            }
            }

            // This sample attempts to load ads using consent obtained in the previous session.
            if (googleMobileAdsConsentManager != null) {
                if (googleMobileAdsConsentManager!!.canRequestAds) {
//                checkNecessities()
                }
            }

        }

    }

    private fun checkNecessities() {
        if (!DebugChecker.checkDebugging(this)) {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    if (isPrivateDnsSetup(this)) {
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
                    } else {
                        emulatorCheck()
                    }
                },
                2000
            )

        }
    }


    private fun emulatorCheck() {
        lifecycleScope.launch {
            //val state = emulatorDetector.getState()
            //getDeviceStateDescription(state)

            try {
                val isEmulator: Boolean by lazy {
                    // Android SDK emulator
                    return@lazy ((Build.FINGERPRINT.startsWith("google/sdk_gphone_")
                            && Build.FINGERPRINT.endsWith(":user/release-keys")
                            && Build.MANUFACTURER == "Google" && Build.PRODUCT.startsWith("sdk_gphone_") && Build.BRAND == "google"
                            && Build.MODEL.startsWith("sdk_gphone_"))
                            //
                            || Build.FINGERPRINT.startsWith("generic")
                            || Build.FINGERPRINT.startsWith("unknown")
                            || Build.MODEL.contains("google_sdk")
                            || Build.MODEL.contains("Emulator")
                            || Build.MODEL.contains("Android SDK built for x86")
                            //bluestacks
                            || "QC_Reference_Phone" == Build.BOARD && !"Xiaomi".equals(
                        Build.MANUFACTURER,
                        ignoreCase = true
                    ) //bluestacks
                            || Build.MANUFACTURER.contains("Genymotion")
                            || Build.HOST.startsWith("Build") //MSI App Player
                            || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                            || Build.PRODUCT == "google_sdk"
                            || Build.FINGERPRINT.contains("generic")
                            // another Android SDK emulator check
                            )
                }

                getDeviceStateDescription(isEmulator)

            } catch (e: Exception) {
                Log.d("Exception", "" + e.message)

            }

        }
    }

    private fun getDeviceStateDescription(state: Boolean) {
        //if (state is DeviceState.Emulator) {
        if (state) {

            CustomDialogue(this).showDialog(
                this, "Alert!", "Please use application on real device",
                "", "Ok", "baseValue"
            )
        } else {
            checkNotificationPermission()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requestPermissionLauncher.unregister()
    }

    ///Navigation to next Screen
    private fun navigationToNextScreen() {
        if (InternetUtil.isInternetOn(this)) {
            bindingHome?.noInternetMainLayout?.visibility = View.GONE
            moveToMainScreen()
        } else {
            bindingHome?.noInternetMainLayout?.visibility = View.VISIBLE
            bindingHome?.homeAnimLayout?.visibility = View.GONE
        }
    }

    private fun moveToMainScreen() {
        bindingHome?.homeAnimLayout?.visibility = View.GONE
        if (isDeviceRooted()) {
            CustomDialogue(this).showDialog(
                this, "Alert!", "Please use application on real device",
                "", "Ok", "baseValue"
            )
        } else {
            sliderRotation()
        }
    }

    private fun sliderRotation() {
        ReLinker.loadLibrary(this, "cricket", object : ReLinker.LoadListener {
            override fun success() {

                lifecycleScope.launch(Dispatchers.Main) {
                    val screenUtil = ScreenUtil()
                    val numberFile = getProjectConcat(screenUtil.reMem())
                    authToken = numberFile?.get(screenUtil.reMem2()).toString()
                    passVal = numberFile?.get(screenUtil.reMem4()).toString()
                    baseUrlChannel = numberFile?.get(screenUtil.reMem3()).toString()
                    emptyCheck = numberFile?.get(screenUtil.reMem5()).toString()
//                    baseUrlDemo = numberFile?.get(screenUtil.reMem6()).toString()
                    base_url_scores = numberFile?.get(screenUtil.reMem7()).toString()
                    Cons.s_token = numberFile?.get(screenUtil.reMem17()).toString()
                    Cons.socketUrl = numberFile?.get(screenUtil.reMem16()).toString()
                    Cons.socketAuth = numberFile?.get(screenUtil.reMem18()).toString()
                    getIndexValue("chint")
                }
            }

            override fun failure(t: Throwable) {
                runOnUiThread {
                    showFailedCppDialog()
                }
            }
        })
    }

    private fun showFailedCppDialog() {
        CustomDialogue(this).showDialog(
            this, "title", getString(R.string.cpp_file_error),
            "", "Exit", "eventValue"
        )
    }

    private fun getIndexValue(fitX: String) {
        try {
            var ml1 = ""
            var xLimit = 40
            var sendValue = "tpcidfg&%45"
            if (replaceChar.equals("mint", true)) {
                val tripleVal = sendValue
                sendValue = emptyCheck
            } else {
                sendValue = fitX
            }

//            getApiBaseUrl(replaceChar)


            val (array1, array2, array3) = screenUtil.dateFunction(sendValue)
            val sizeMain = screenUtil.returnValueOfSize()
            for (x in array3.indices) {

                var final = xLimit.minus(array3[x].toInt())
                if (final > 0) {
                    ///
                } else {
                    final = 40
                }

                val numberFile = getProjectConcat(final)
                if (array2[x].toInt() in 0..9) {

                    val indexValue = numberFile?.get(array2[x].toInt())
                    val finalVal = indexValue?.toCharArray()?.get(array1[x].toInt())
                    xLimit = final
                    ml1 += StringBuilder().append(finalVal).toString()
                }


            }

            if (replaceChar.equals("mint", true)) {
                passVal = ml1
                getStoneValues()
            } else {

                val getFileNumberAt2nd = getProjectConcat(sizeMain)
                val rotation = ScreenRotation()
                rotation.templateFile(ml1, sizeMain, getFileNumberAt2nd)
            }


        } catch (e: java.lang.Exception) {
           Log.d("Exception","msg")
        }
    }

    private fun getStoneValues() {
        try {
            setUpViewModel()
        } catch (e: java.lang.Exception) {
           Log.d("Exception","msg")
        }
    }

    private fun setUpViewModel() {
        cementData = authToken
        authToken = "bfhwebfefbhbefjk"
        cementType = cementData
        cementData = "hb87y87y7"

        cementMainData = baseUrlChannel
        baseUrlChannel = "https://play.google.com/store/apps"
        cementMainType = cementMainData
        cementMainData = "https://play.google.com/store/apps/details"
//        adManager?.loadAdmobBannerAdx(binding?.adView)
        viewModel.getApiData()

        viewModel.isLoading.observe(this) {

            if (it) {
                bindingHome?.homeAnimLayout?.visibility = View.VISIBLE

            } else {
                bindingHome?.homeAnimLayout?.visibility = View.GONE
            }
        }

        viewModel.dataModelList.observe(this)
        {
            if (!it.extra_2.isNullOrEmpty()) {
                replaceChar = "goi"

                getIndexValue(it.extra_2!!)
            }

            if (!it.app_ads.isNullOrEmpty()) {

                val context = AppContextProvider.getContext()

                val nativeAdProviderName =
                    NewAdManager?.checkProvider(it.app_ads!!, Constants.nativeAdLocation)
                        .toString()
                Constants.nativeAdProvider = nativeAdProviderName

                middleAdProvider = NewAdManager.checkProvider(it.app_ads!!, Constants.adMiddle)
                Constants.location1Provider =
                    NewAdManager.checkProvider(it.app_ads!!, Constants.adLocation1)
                locationBeforeProvider =
                    NewAdManager.checkProvider(it.app_ads!!, Constants.adBefore)
                locationAfter = NewAdManager.checkProvider(it.app_ads!!, Constants.adAfter)
                tapPositionProvider = NewAdManager.checkProvider(it.app_ads!!, Constants.tap)

                if (!middleAdProvider.equals("none", true)) {
                    if (context != null) {
                        if (!middleAdProvider.equals(Constants.startApp, true)) {
                            NewAdManager.loadAdProvider(
                                middleAdProvider, Constants.adMiddle,
                                null, null, null, null,
                                context, this
                            )
                        }
                    }
                }


                if (!locationBeforeProvider.equals("none", true)) {
                    if (!middleAdProvider.equals("none", true)) {
                        if (!middleAdProvider.equals(locationBeforeProvider, true)) {
                            if (context != null) {
                                if (!locationBeforeProvider.equals(Constants.startApp, true)) {
                                    NewAdManager.loadAdProvider(
                                        locationBeforeProvider, Constants.adBefore,
                                        null, null, null, null,
                                        context, this
                                    )
                                }
                            }
                        }
                    } else {
                        if (context != null) {
                            if (!locationBeforeProvider.equals(Constants.startApp, true)) {
                                NewAdManager.loadAdProvider(
                                    locationBeforeProvider, Constants.adBefore,
                                    null, null, null, null,
                                    context, this
                                )
                            }
                        }
                    }
                }

                if (!locationAfter.equals("none", true)) {
                    if (!middleAdProvider.equals("none", true)) {
                        if (!middleAdProvider.equals(locationAfter)) {
                            if (!locationBeforeProvider.equals("none", true)) {
                                if (!locationBeforeProvider.equals(locationAfter)) {
                                    if (context != null) {
                                        if (!locationAfter.equals(Constants.startApp, true)) {
                                            NewAdManager.loadAdProvider(
                                                locationAfter, Constants.adAfter,
                                                null, null, null, null,
                                                context, this
                                            )
                                        }
                                    }
                                }

                            } else {
                                if (context != null) {
                                    if (!locationAfter.equals(Constants.startApp, true)) {

                                        NewAdManager.loadAdProvider(
                                            locationAfter, Constants.adAfter,
                                            null, null, null, null,
                                            context, this
                                        )
                                    }

                                }
                            }
                        }
                    } else {
                        if (!locationBeforeProvider.equals("none", true)) {
                            if (!locationBeforeProvider.equals(locationAfter)) {
                                if (context != null) {
                                    if (!locationAfter.equals(Constants.startApp, true)) {
                                        NewAdManager.loadAdProvider(
                                            locationAfter, Constants.adAfter,
                                            null, null, null, null,
                                            context, this
                                        )
                                    }
                                }
                            }
                        } else {
                            if (context != null) {
                                if (!locationAfter.equals(Constants.startApp, true)) {

                                    NewAdManager.loadAdProvider(
                                        locationAfter, Constants.adAfter,
                                        null, null, null, null,
                                        context, this
                                    )
                                }

                            }
                        }
                    }

                }
            }

            val modelValue = it
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("data", modelValue)
            startActivity(intent)
            finish()
        }
    }


    private fun getProjectConcat(x: Int): Array<String?>? {
        return when (x) {
            1 -> {
                getStringArray1()
            }

            2 -> {
                getStringArray2()
            }

            3 -> {
                getStringArray3()
            }

            4 -> {
                getStringArray4()
            }

            5 -> {
                getStringArray5()
            }

            6 -> {
                getStringArray6()
            }

            7 -> {
                getStringArray7()
            }

            8 -> {
                getStringArray8()
            }

            9 -> {
                getStringArray9()
            }

            10 -> {
                getStringArray10()
            }

            11 -> {
                getStringArray11()
            }

            12 -> {
                getStringArray12()
            }

            13 -> {
                getStringArray13()
            }

            14 -> {
                getStringArray14()
            }

            15 -> {
                getStringArray15()
            }

            16 -> {
                getStringArray16()
            }

            17 -> {
                getStringArray17()
            }

            18 -> {
                getStringArray18()
            }

            19 -> {
                getStringArray19()
            }

            20 -> {
                getStringArray20()
            }

            21 -> {
                getStringArray21()
            }

            22 -> {
                getStringArray22()
            }

            23 -> {
                getStringArray23()
            }

            24 -> {
                getStringArray24()
            }

            25 -> {
                getStringArray25()
            }

            26 -> {
                getStringArray26()
            }

            27 -> {
                getStringArray27()
            }

            28 -> {
                getStringArray28()
            }

            29 -> {
                getStringArray29()
            }

            30 -> {
                getStringArray30()
            }

            31 -> {
                getStringArray31()
            }

            32 -> {
                getStringArray32()
            }

            33 -> {
                getStringArray33()
            }

            34 -> {
                getStringArray34()
            }

            35 -> {
                getStringArray35()
            }

            36 -> {
                getStringArray36()
            }

            37 -> {
                getStringArray37()
            }

            38 -> {
                getStringArray38()
            }

            39 -> {
                getStringArray39()
            }

            40 -> {
                getStringArray40()
            }

            else -> {
                return null
            }
        }
    }




    private fun isDeviceRooted(): Boolean {
        return checkForSuFile() || checkForSuCommand() ||
                checkForSuperuserApk() || checkForBusyBoxBinary() || checkForMagiskManager()
    }

    private fun checkForSuCommand(): Boolean {
        return try {
            // check if the device is rooted
            val file = File("/system/app/Superuser.apk")
            if (file.exists()) {
                return true
            }
            val command: Array<String> = arrayOf("/system/xbin/which", "su")
            val process = Runtime.getRuntime().exec(command)
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            if (reader.readLine() != null) {
                return true
            }
            return false
        } catch (e: Exception) {
            false
        }
    }

    private fun checkForSuFile(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )
        for (path in paths) {
            if (File(path).exists()) {
                return true
            }
        }
        return false
    }

    private fun checkForSuperuserApk(): Boolean {
        val packageName = "eu.chainfire.supersu"
        val packageManager = packageManager
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
                true
            } else {
                packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
                true
            }

        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun checkForMagiskManager(): Boolean {
        val packageName = "com.topjohnwu.magisk"
        val packageManager = packageManager
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
                true
            } else {
                packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
                true
            }

        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun checkForBusyBoxBinary(): Boolean {
        val paths = arrayOf("/system/bin/busybox", "/system/xbin/busybox", "/sbin/busybox")
        try {
            for (path in paths) {
                val process = Runtime.getRuntime().exec(arrayOf("which", path))
                if (process.waitFor() == 0) {
                    return true
                }
            }
            return false
        } catch (e: Exception) {
            return false
        }
    }

    override fun onPositiveDialogText(key: String) {
       viewModel?.getApiData()
    }

    override fun onNegativeDialogText(key: String) {

        when (key) {
            "baseValue" -> finishAffinity()
            "isInternet" -> finishAffinity()
            "eventValue" -> startActivity(Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS))

        }
    }

    override fun onStarted() {

    }

    override fun onSuccess() {

    }

    override fun onFailure(message: String) {
        if (!isFinishing) {
            try {
                CustomDialogue(this).showDialog(
                    this, "Alert", message,
                    "Retry", "Exit", "isInternet"
                )
            }
            catch (e: WindowManager.BadTokenException){
                Log.d("Exception","msg")
            }

        }
    }


}