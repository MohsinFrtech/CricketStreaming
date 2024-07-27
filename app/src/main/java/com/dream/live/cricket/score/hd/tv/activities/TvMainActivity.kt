package com.dream.live.cricket.score.hd.tv.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.ActivityTvMainBinding
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.date.ScreenRotation
import com.dream.live.cricket.score.hd.streaming.date.ScreenUtil
import com.dream.live.cricket.score.hd.streaming.models.ApplicationConfiguration
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.viewmodel.OneViewModel
import com.dream.live.cricket.score.hd.utils.Logger
import com.getkeepsafe.relinker.ReLinker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TvMainActivity : FragmentActivity(), AdManagerListener {

    private val viewModel by lazy {
        ViewModelProvider(this)[OneViewModel::class.java]
    }
    var binding: ActivityTvMainBinding?=null
    private var adManager: AdManager? = null
    private var replaceChar = "mint"
    private val tags = "TvMainActivity"
    private val screenUtil = ScreenUtil()

    private var logger: Logger = Logger()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this, R.layout.activity_tv_main)

        adManager = AdManager(this, this, this)
        logger.printLog(tags,"onCreate")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        binding?.lottiePlayer2?.visibility=View.VISIBLE
        ReLinker.loadLibrary(this, "cricket", object : ReLinker.LoadListener {
            override fun success() {

                lifecycleScope.launch(Dispatchers.Main) {
                    val screenUtil = ScreenUtil()
                    val numberFile = getProjectConcat(screenUtil.reMem())
                    Constants.authToken = numberFile?.get(screenUtil.reMem2()).toString()
                    Constants.passVal = numberFile?.get(screenUtil.reMem4()).toString()
                    Constants.baseUrlChannel = numberFile?.get(screenUtil.reMem3()).toString()
                    Constants.emptyCheck = numberFile?.get(screenUtil.reMem5()).toString()
                    Cons.base_url_scores = numberFile?.get(screenUtil.reMem7()).toString()
                    Cons.s_token = numberFile?.get(screenUtil.reMem17()).toString()
                    Cons.socketUrl = numberFile?.get(screenUtil.reMem16()).toString()
                    Cons.socketAuth = numberFile?.get(screenUtil.reMem18()).toString()
                    getIndexValue("chint")
                }
            }

            override fun failure(t: Throwable) {
                Handler(Looper.getMainLooper()).post {
                    hideStreaming()
                    showFailedCppDialog()
                }
            }
        })

    }


    private fun showFailedCppDialog() {
        Toast.makeText(this,"Something went wrong , Please restart app.",Toast.LENGTH_SHORT).show()
    }


    private fun getStoneValues() {
        setUpModelData()

        viewModel.dataModelList.observe(this) {

            if(it.live==true){

                if(!it.events.isNullOrEmpty())
                {
                    binding?.noStreaming?.visibility=View.GONE
                    hideStreaming()
                }

                if (!it.extra_2.isNullOrEmpty()) {
                    replaceChar = "goi"

                    getIndexValue(it.extra_2!!)
                }

                if (!it.application_configurations.isNullOrEmpty()) {
                    showSplashMethod(it.application_configurations)
                }


            }
            else
            {
                binding?.noStreaming?.visibility=View.VISIBLE
                hideStreaming()
            }

        }


    }

    private fun showSplashMethod(applicationConfigurations: List<ApplicationConfiguration>?) {
        if (!applicationConfigurations.isNullOrEmpty()) {
            applicationConfigurations.forEach { configValue ->

                if (configValue.key.equals("baseURL", true)) {
                    if (configValue.value != null) {
                        Constants.baseUrlDemo = configValue.value.toString()
                    }
                }

            }

        }

    }


    private fun hideStreaming() {
        binding?.lottiePlayer2?.visibility= View.GONE
    }

    private fun setUpModelData() {
        Constants.cementData = Constants.authToken
        Constants.authToken = "bfhwebfefbhbefjk"
        Constants.cementType = Constants.cementData
        Constants.cementData = "hb87y87y7"

        Constants.cementMainData = Constants.baseUrlChannel
        Constants.baseUrlChannel = "https://play.google.com/store/apps"
        Constants.cementMainType = Constants.cementMainData
        Constants.cementMainData = "https://play.google.com/store/apps/details"

        viewModel.onRefreshFixtures()

    }



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


    private fun getIndexValue(fitX: String) {
        try {
            var ml1 = ""
            var xLimit = 40
            var sendValue = "tpcidfg&%45"
            if (replaceChar.equals("mint", true)) {
                val tripleVal = sendValue
                sendValue = Constants.emptyCheck
            } else {
                sendValue = fitX
            }


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
                Constants.passVal = ml1
                getStoneValues()
            } else {

                val getFileNumberAt2nd = getProjectConcat(sizeMain)
                val rotation = ScreenRotation()
                rotation.templateFile(ml1, sizeMain, getFileNumberAt2nd)
            }


        } catch (e: Exception) {
            logger.printLog("tAG", "message" + e.message)
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

    override fun onAdLoad(value: String) {

    }

    override fun onAdFinish() {

    }


}