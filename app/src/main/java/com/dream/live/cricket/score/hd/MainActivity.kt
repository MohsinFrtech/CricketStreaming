package com.dream.live.cricket.score.hd


import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.getkeepsafe.relinker.ReLinker
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.dream.live.cricket.score.hd.streaming.date.ScreenRotation
import com.dream.live.cricket.score.hd.streaming.utils.Logger
import com.dream.live.cricket.score.hd.databinding.ActivityMainBinding
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.scores.utility.Cons.base_url_scores
import com.dream.live.cricket.score.hd.scores.utility.Cons.currentNativeAd
import com.dream.live.cricket.score.hd.scores.utility.listeners.ApiResponseListener
import com.dream.live.cricket.score.hd.scores.viewmodel.LiveViewModel
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.date.ScreenUtil
import com.dream.live.cricket.score.hd.streaming.models.ApplicationConfiguration
import com.dream.live.cricket.score.hd.streaming.models.DataModel
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.DialogListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.authToken
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.baseUrlChannel
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.baseUrlDemo
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.cementData
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.cementMainData
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.cementMainType
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.cementType
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.emptyCheck
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.passVal
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.rateShown
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.rateUsDialogValue
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.rateUsKey
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.rateUsText
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.splash_status
import com.dream.live.cricket.score.hd.streaming.utils.objects.CustomDialogue
import com.dream.live.cricket.score.hd.streaming.utils.objects.Defamation
import com.dream.live.cricket.score.hd.streaming.utils.objects.SharedPreference
import com.dream.live.cricket.score.hd.streaming.viewmodel.OneViewModel
import com.dream.live.cricket.score.hd.utils.InternetUtil
import com.facebook.ads.AdSettings
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), DialogListener,
    NavController.OnDestinationChangedListener, AdManagerListener, ApiResponseListener {

    private lateinit var binding: ActivityMainBinding
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

    var context: Context? = null
    private val tAG = "MainActivityClass"

    private val viewModel by lazy {
        ViewModelProvider(this)[OneViewModel::class.java]
    }
    private var replaceChar = "mint"
    private val logger = Logger()
    private var navController: NavController? = null
    private var intentLink: String = ""
    private var backBoolean = false
    private var time = "0"
    private var navigationTap = 0
    private var showNavigationAd = 2
    private var booleanVpn: Boolean? = false

    private var itemView: BottomNavigationItemView? = null
    private var itemView2: BottomNavigationItemView? = null
    private var itemView3: BottomNavigationItemView? = null
    private var itemView4: BottomNavigationItemView? = null
    private var itemView5: BottomNavigationItemView? = null

    private val liveViewModel by lazy {
        ViewModelProvider(this)[LiveViewModel::class.java]
    }
    private var adManager: AdManager? = null
    private var adStatus = false
    private var preference: SharedPreference? = null
    private val screenUtil = ScreenUtil()
    private var adProviderName = "none"
    private var rateUsStatus: Boolean? = false
    private var ratingGiven: Boolean? = false
    private var dialog: Dialog? = null
    private var dialog2: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Constants.modeCheckValue.equals("dark", true)) {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
        } else {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )

        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        AdSettings.addTestDevice("538c573f-f49a-4c57-8844-f23ddcd20d46")
        window.navigationBarColor = ContextCompat.getColor(this, R.color.noChange)
        adManager = AdManager(this, this, this)

        context = this
        setUpActionBar()
        preference = SharedPreference(context)
        binding.lifecycleOwner = this
        binding.modelData = viewModel
        viewModel?.apiResponseListener = this
        sliderRotation()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backBoolean) {
                    if (ratingGiven != true) {
                        if (!isFinishing) {
                            showDialogue()
                        }
                    } else {
                        finishAffinity()
                    }
                } else {
                    binding?.navHostFragment?.findNavController()?.popBackStack()
                }
            }
        })
        setUpNavigationGraph()
    }


    private fun sliderRotation() {
        ReLinker.loadLibrary(context, "cricket", object : ReLinker.LoadListener {
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

                Handler(Looper.getMainLooper()).post {
                    showFailedCppDialog()
                }
            }
        })


    }


    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbar1)
    }

    private fun setUpNavigationGraph() {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home,
                R.id.streaming,
                R.id.recentFragment,
                R.id.upcomingFragment,
                R.id.moreFragment
            )
        )
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController!!, appBarConfiguration)
        binding.bottomNav.setupWithNavController(navController!!)

        val bottomMenuView = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
        val view = bottomMenuView.getChildAt(0)
        itemView = view as BottomNavigationItemView
        val viewCustom =
            LayoutInflater.from(this).inflate(R.layout.homeactive, bottomMenuView, false)
        itemView?.addView(viewCustom)

        val bottomMenuView2 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
        val view2 = bottomMenuView2.getChildAt(1)
        itemView2 = view2 as BottomNavigationItemView
        val viewCustom2 =
            LayoutInflater.from(this).inflate(R.layout.streaming_inactive, bottomMenuView2, false)
        itemView2?.addView(viewCustom2)


        val bottomMenuView4 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
        val view3 = bottomMenuView4.getChildAt(2)
        itemView3 = view3 as BottomNavigationItemView
        val viewCustom4 =
            LayoutInflater.from(this).inflate(R.layout.recentinactive, bottomMenuView4, false)
        itemView3?.addView(viewCustom4)


        val bottomMenuView5 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
        val view5 = bottomMenuView5.getChildAt(3)
        itemView4 = view5 as BottomNavigationItemView
        val viewCustom5 =
            LayoutInflater.from(this).inflate(R.layout.upcominginactive, bottomMenuView4, false)
        itemView4?.addView(viewCustom5)


        val bottomMenuView6 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
        val view6 = bottomMenuView6.getChildAt(4)
        itemView5 = view6 as BottomNavigationItemView
        val viewCustom6 =
            LayoutInflater.from(this).inflate(R.layout.moreinactive, bottomMenuView4, false)
        itemView5?.addView(viewCustom6)

        //Add custom tab menu

        navController!!.addOnDestinationChangedListener(this)


        binding.bottomNav.setOnItemSelectedListener { item ->

            try {
                navigationTap += 1

                if (navigationTap == showNavigationAd) {
                    //showInterAd

                    if (adProviderName.equals(Constants.startApp, true)) {
                        adManager?.loadAdProvider(
                            adProviderName, Constants.adTap,
                            null, null, null, null
                        )
                    }

                    if (adStatus) {
                        if (!adProviderName.equals("none", true)) {
                            adManager?.showAds(adProviderName)
                            navigationTap = 0
                            showNavigationAd += 1
                        }

                    } else {
                        navigationTap = 0
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


            if (item.itemId == navController!!.currentDestination?.id) {
                // If it is, do nothing or handle the reselection as desired
                // In this case, we are not performing any action
            } else {
                // Navigate to the selected item using NavigationUI
                NavigationUI.onNavDestinationSelected(item, navController!!)
            }
            false
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

            getApiBaseUrl(replaceChar)


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


        } catch (e: Exception) {
            logger.printLog(tAG, "message" + e.message)
        }


    }

    private fun getStoneValues() {
        try {
            setUpViewModel()
        } catch (e: Exception) {
            logger.printLog("Exception", "" + e.message)
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
        viewModel.getApiData()

        if (Cons.s_token.isNotEmpty() && base_url_scores.isNotEmpty()) {
            liveViewModel.getsliderData()
        }

        viewModel.isLoading.observe(this) {

            if (it) {
                binding.lottieHome.visibility = View.VISIBLE

            } else {
                binding.lottieHome.visibility = View.GONE

            }


        }

        viewModel.dataModelList.observe(this)
        {

            if (!it.app_ads.isNullOrEmpty()) {
                adProviderName =
                    adManager?.checkProvider(it.app_ads!!, Constants.adTap).toString()

                val nativeAdProvider =
                    adManager?.checkProvider(it.app_ads!!, Constants.nativeAdLocation)
                if (nativeAdProvider != null) {
                    adManager?.loadAdProvider(
                        nativeAdProvider, Constants.nativeAdLocation, null,
                        null, null, null
                    )
                }

                Log.d("tapAdName", "provider" + adProviderName)
                if (adProviderName != "none") {
                    //interstitial Ad loading
                    loadTapAd()
                }
                val bannerProvider =
                    adManager?.checkProvider(it.app_ads!!, Constants.adLocation1)

                if (bannerProvider != null) {
                    Constants.adLocation1Provider = bannerProvider
                }

                if (bannerProvider != null) {

                    adManager?.loadAdProvider(
                        bannerProvider, Constants.adLocation1, binding?.adView,
                        binding?.fbAdView, binding?.unityBannerView, binding?.startAppBanner
                    )
                }
            }



            if (!it.extra_2.isNullOrEmpty()) {
                replaceChar = "goi"

                getIndexValue(it.extra_2!!)
            }

            if (!it.app_version.isNullOrEmpty()) {
                try {
                    val version = BuildConfig.VERSION_CODE
                    try {
                        val parsedInt = it.app_version!!.toInt()
                        if (parsedInt > version) {
                            if (!Constants.app_update_dialog) {
                                showAppUpdateDialog(it.app_update_text, it.is_permanent_dialog)
                                Constants.app_update_dialog = true
                            }
                        }
                        else
                        {
                            checkRatingDialog(it)
                            //check rating dialog....
                        }
                    } catch (nfe: java.lang.NumberFormatException) {

                        logger.printLog(tAG, "Exception" + nfe.message)
                    }
                } catch (e: PackageManager.NameNotFoundException) {
                    logger.printLog(tAG, "Exception" + e.message)
                }
            }
            else
            {
                checkRatingDialog(it)
            }

            if (!it.application_configurations.isNullOrEmpty()) {
                showSplashMethod(it.application_configurations)
            }

        }
    }


    private fun showSplashMethod(applicationConfigurations: List<ApplicationConfiguration>?) {
        var showingSplash = false
        if (!applicationConfigurations.isNullOrEmpty()) {
            applicationConfigurations.forEach { configValue ->

                if (configValue.key.equals("Time", true)) {
                    if (configValue.value != null) {
                        time = configValue.value!!
                    }
                }
                if (configValue.key.equals("baseURL", true)) {
                    if (configValue.value != null) {
                        baseUrlDemo = configValue.value.toString()
                    }
                }
                ///For setting button text
                if (configValue.key.equals("ButtonText", true)) {
                    if (configValue.value != null) {
                        binding?.splashButton?.text = configValue.value
                    }

                }

                ///For setting heading
                if (configValue.key.equals("Heading", true)) {
                    if (configValue.value != null) {
                        binding?.splashHeading?.text = configValue.value
                    }

                }

                ///For setting link
                if (configValue.key.equals("ButtonLink", true)) {
                    if (configValue.value != null) {
                        intentLink = configValue.value!!
                    }

                }

                ///For setting body
                if (configValue.key.equals("DetailText", true)) {
                    if (configValue.value != null) {
                        binding?.splashBody?.text = configValue.value
                    }
                }


                ///For setting show button
                if (configValue.key.equals("ShowButton", true)) {
                    if (configValue.value != null) {
                        if (configValue.value.equals("True", true)) {
                            binding?.splashButton?.visibility = View.VISIBLE
                        } else {
                            binding?.splashButton?.visibility = View.GONE
                        }

                    }

                }

                if (configValue.key.equals("ShowSplash", true)) {
                    if (configValue.value.equals("true", true)) {
                        if (!splash_status) {
                            showingSplash = true
                        }
                    }
                }

            }

            if (showingSplash) {
                try {
                    var timer: Int = time.toInt()
                    timer *= 1000
                    binding?.splashLayout?.visibility = View.VISIBLE
                    binding?.splashButton?.setOnClickListener {

                        val uri =
                            Uri.parse(intentLink)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)

                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        splash_status = true
                        binding?.splashLayout?.visibility = View.GONE
                    }, timer.toLong())
                } catch (e: NumberFormatException) {

                }

            }
        }

    }

    private fun checkRatingDialog(dataModel: DataModel) {
        if (!dataModel.application_configurations.isNullOrEmpty()) {
            val status= preference?.getRateUsBool(rateUsKey)
            if (status!=true) {
                dataModel.application_configurations?.forEach { config ->
                    if (config.key.equals("rateShow", true)) {
                        if (config.value != null) {
                            if (config.value.equals("True", true)) {
                                rateUsDialogValue = true
                            } else {
                                rateUsDialogValue = false
                            }
                        }
                    }

                    if (config.key.equals("rateText", true)) {
                        if (config.value != null) {
                            rateUsText = config.value.toString()
                        }
                    }
                }
                if (rateUsDialogValue) {
                    if (!rateShown) {
                        rateShown = true
                        rateUsDialog(rateUsText)
                    }
                }
            }
        }
    }

    private fun rateUsDialog(
        rateText: String?
    ) {
        dialog2 = context?.let { Dialog(it) }
        dialog2?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog2?.setContentView(R.layout.rating_layout)

        val rateClicked = dialog2?.findViewById(R.id.rateUs) as Button
        val cancelCliked = dialog2?.findViewById(R.id.cancelUs) as Button
        val rateTxt = dialog2?.findViewById(R.id.rateUsTitle) as TextView

        rateTxt.text = rateText

        rateClicked.setOnClickListener {
            rateClicked()
        }

        cancelCliked.setOnClickListener {
            dialog2?.dismiss()
        }

        if (!isFinishing) {
            dialog2?.show()
        }

    }
    private fun showAppUpdateDialog(appUpdateText: String?, permanent: Boolean?) {
        val dialog = context?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.app_update_layout)
        val textExit = dialog?.findViewById(R.id.no_thanks) as Button
        val textRate2 = dialog.findViewById(R.id.update) as Button
        val textUpdate = dialog.findViewById(R.id.app_update_txt) as TextView

        if (permanent == true) {
            dialog.setCancelable(false)
            textExit.text = resources.getString(R.string.Exit)
        } else {
            textExit.text = resources.getString(R.string.noThanks)
            dialog.setCancelable(true)
        }

        if (appUpdateText != null) {
            textUpdate.text = appUpdateText
        }

        textExit.setOnClickListener {
            if (permanent == true) {
                Constants.app_update_dialog = false
                dialog.dismiss()
                finishAffinity()
            } else {

                dialog.dismiss()

            }
        }

        textRate2.setOnClickListener {
            rateClicked()
        }


        dialog.show()
    }

    private fun showDialogue() {
        val dialog = context?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(true)
        dialog?.setContentView(R.layout.custom_layout2)

        val textExit = dialog?.findViewById(R.id.rateus) as Button
        val textRate2 = dialog.findViewById(R.id.cancel) as Button
        textExit.setOnClickListener {
            rateClicked()
        }

        textRate2.setOnClickListener {
            Constants.app_update_dialog = false
            dialog.dismiss()
            finishAffinity()

        }
        if (!isFinishing) {
            dialog.show()
        }

    }

    private fun rateClicked() {
        try {
            val viewIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse("https://play.google.com/store/apps/details?id=${this.packageName}")
            )
            if (viewIntent.resolveActivity(packageManager) != null) {
                startActivity(viewIntent)
            } else {
                Log.d("CricketStreaming", "Intent not resolved")
            }
            preference?.saveRateUsBool(rateUsKey, true)

        } catch (e: ActivityNotFoundException) {
            Log.d("CricketStreaming", "Intent not resolved" + e?.message)
            preference?.saveRateUsBool(rateUsKey, true)
        }
    }


    private fun getApiBaseUrl(replaceChar: String) {

        try {
            val baseValue = Defamation.encryptBase64(replaceChar)
            val getSecretValue = Defamation.decryptPRNG(baseValue)
            Defamation.convertCementData(getSecretValue)
        } catch (e: Exception) {

            Log.d("Exception", "message" + e.message)
        }
    }

    override fun onPositiveDialogText(key: String) {
        when (key) {
            "baseValue" -> viewModel.getApiData()
            "isInternet" -> viewModel.getApiData()
            "eventValue" -> viewModel.getApiData()
        }
    }


    override fun onPause() {
        super.onPause()
        liveViewModel.stopWebSocket()
    }

    override fun onDestroy() {
        super.onDestroy()
        liveViewModel.stopWebSocket()
        Constants.app_update_dialog=false
        splash_status=false
        rateShown=false
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

        liveViewModel.isSocketUrl.observe(this) {
            if (it == true) {
                liveViewModel.runSocketCode()
            }
        }
        rateUsStatus = preference?.getRateUsBool(rateUsKey)
        if (rateUsStatus == true) {
            ratingGiven = true
            if (dialog != null) {
                dialog?.dismiss()
            }

            if (dialog2 != null) {
                dialog2?.dismiss()
            }
        } else {
            ratingGiven = false
        }

        checkVpn()
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



    override fun onNegativeDialogText(key: String) {
        when (key) {
            "baseValue" -> finishAffinity()
            "isInternet" -> finishAffinity()
            "eventValue" -> finishAffinity()

        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

        if (destination.id == R.id.channel) {
            binding.bottomNav.visibility = View.GONE
        } else {
            binding.bottomNav.visibility = View.VISIBLE

        }

        if (destination.id == R.id.home) {
            itemView?.removeAllViews()
            itemView2?.removeAllViews()
            itemView3?.removeAllViews()
            itemView4?.removeAllViews()
            itemView5?.removeAllViews()


            val bottomMenuView22 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom22 = LayoutInflater.from(this)
                .inflate(R.layout.streaming_inactive, bottomMenuView22, false)
            itemView2?.addView(viewCustom22)
            val bottomMenuView3 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom3 =
                LayoutInflater.from(this).inflate(R.layout.homeactive, bottomMenuView3, false)
            itemView?.addView(viewCustom3)

            val bottomMenuView6 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom6 =
                LayoutInflater.from(this).inflate(R.layout.recentinactive, bottomMenuView6, false)
            itemView3?.addView(viewCustom6)


            val bottomMenuView111 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom16 = LayoutInflater.from(this)
                .inflate(R.layout.upcominginactive, bottomMenuView111, false)
            itemView4?.addView(viewCustom16)


            val bottomMenuView21 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom21 =
                LayoutInflater.from(this).inflate(R.layout.moreinactive, bottomMenuView21, false)
            itemView5?.addView(viewCustom21)
        } else if (destination.id == R.id.streaming) {
            itemView?.removeAllViews()
            itemView2?.removeAllViews()
            itemView3?.removeAllViews()
            itemView4?.removeAllViews()
            itemView5?.removeAllViews()

            val bottomMenuView22 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom22 =
                LayoutInflater.from(this).inflate(R.layout.streamingactive, bottomMenuView22, false)
            itemView2?.addView(viewCustom22)
            val bottomMenuView3 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom3 =
                LayoutInflater.from(this).inflate(R.layout.homeinactive, bottomMenuView3, false)
            itemView?.addView(viewCustom3)

            val bottomMenuView6 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom6 =
                LayoutInflater.from(this).inflate(R.layout.recentinactive, bottomMenuView6, false)
            itemView3?.addView(viewCustom6)


            val bottomMenuView31 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom31 = LayoutInflater.from(this)
                .inflate(R.layout.upcominginactive, bottomMenuView31, false)
            itemView4?.addView(viewCustom31)


            val bottomMenuView32 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom32 =
                LayoutInflater.from(this).inflate(R.layout.moreinactive, bottomMenuView32, false)
            itemView5?.addView(viewCustom32)

        } else if (destination.id == R.id.recentFragment) {

            itemView?.removeAllViews()
            itemView2?.removeAllViews()
            itemView3?.removeAllViews()
            itemView4?.removeAllViews()
            itemView5?.removeAllViews()

            val bottomMenuView22 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom22 = LayoutInflater.from(this)
                .inflate(R.layout.streaming_inactive, bottomMenuView22, false)
            itemView2?.addView(viewCustom22)

            val bottomMenuView3 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom3 =
                LayoutInflater.from(this).inflate(R.layout.homeinactive, bottomMenuView3, false)
            itemView?.addView(viewCustom3)


            val bottomMenuView6 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom6 =
                LayoutInflater.from(this).inflate(R.layout.recentactive, bottomMenuView6, false)
            itemView3?.addView(viewCustom6)

            val bottomMenuView33 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom33 = LayoutInflater.from(this)
                .inflate(R.layout.upcominginactive, bottomMenuView33, false)
            itemView4?.addView(viewCustom33)


            val bottomMenuView34 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom34 =
                LayoutInflater.from(this).inflate(R.layout.moreinactive, bottomMenuView34, false)
            itemView5?.addView(viewCustom34)

        } else if (destination.id == R.id.upcomingFragment) {
            itemView?.removeAllViews()
            itemView2?.removeAllViews()
            itemView3?.removeAllViews()
            itemView4?.removeAllViews()
            itemView5?.removeAllViews()

            val bottomMenuView22 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom22 = LayoutInflater.from(this)
                .inflate(R.layout.streaming_inactive, bottomMenuView22, false)
            itemView2?.addView(viewCustom22)

            val bottomMenuView3 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom3 =
                LayoutInflater.from(this).inflate(R.layout.homeinactive, bottomMenuView3, false)
            itemView?.addView(viewCustom3)


            val bottomMenuView6 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom6 =
                LayoutInflater.from(this).inflate(R.layout.recentinactive, bottomMenuView6, false)
            itemView3?.addView(viewCustom6)

            val bottomMenuView33 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom33 =
                LayoutInflater.from(this).inflate(R.layout.upcomingactive, bottomMenuView33, false)
            itemView4?.addView(viewCustom33)


            val bottomMenuView34 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom34 =
                LayoutInflater.from(this).inflate(R.layout.moreinactive, bottomMenuView34, false)
            itemView5?.addView(viewCustom34)

        } else {


            itemView?.removeAllViews()
            itemView2?.removeAllViews()
            itemView3?.removeAllViews()
            itemView4?.removeAllViews()
            itemView5?.removeAllViews()

            val bottomMenuView22 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom22 = LayoutInflater.from(this)
                .inflate(R.layout.streaming_inactive, bottomMenuView22, false)
            itemView2?.addView(viewCustom22)

            val bottomMenuView3 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom3 =
                LayoutInflater.from(this).inflate(R.layout.homeinactive, bottomMenuView3, false)
            itemView?.addView(viewCustom3)


            val bottomMenuView6 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom6 =
                LayoutInflater.from(this).inflate(R.layout.recentinactive, bottomMenuView6, false)
            itemView3?.addView(viewCustom6)

            val bottomMenuView33 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom33 = LayoutInflater.from(this)
                .inflate(R.layout.upcominginactive, bottomMenuView33, false)
            itemView4?.addView(viewCustom33)


            val bottomMenuView34 = binding.bottomNav.getChildAt(0) as BottomNavigationMenuView
            val viewCustom34 =
                LayoutInflater.from(this).inflate(R.layout.moreactive, bottomMenuView34, false)
            itemView5?.addView(viewCustom34)
        }


        //////////////////////
        backBoolean = destination.id == R.id.home


    }

    private fun loadTapAd() {
        //interstitial Ad loading
        if (!adProviderName.equals(Constants.startApp, true)) {
            adManager?.loadAdProvider(
                adProviderName, Constants.adTap,
                null, null, null, null
            )
        }

    }

    override fun onAdLoad(value: String) {
        adStatus = value.equals("success", true)

    }

    override fun onAdFinish() {
        adStatus = false
        if (adProviderName != "none") {
            loadTapAd()
        }
    }

    override fun onStarted() {

    }

    override fun onSuccess() {

    }

    override fun onFailure(message: String) {
        CustomDialogue(this).showDialog(
            this, "Alert", message,
            "Retry", "Exit", "isInternet"
        )
    }


}