package com.dream.live.cricket.score.hd.tv.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.ActivityTvHomeBinding
import com.dream.live.cricket.score.hd.streaming.utils.objects.DebugChecker
import com.dream.live.cricket.score.hd.utils.InternetUtil

class TvHomeActivity : FragmentActivity() {

    var binding: ActivityTvHomeBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_tv_home)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        binding?.retry?.setOnClickListener {
            binding?.homeAnimLayout?.visibility = View.VISIBLE
            if(InternetUtil.isInternetOn(this)){
                binding?.internetLay?.visibility = View.GONE
                Handler(Looper.getMainLooper()).postDelayed({
                    startIntent()
                },2000)
            }else {
                Handler(Looper.getMainLooper()).postDelayed({
                    binding?.homeAnimLayout?.visibility = View.GONE
                }, 2000)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (!DebugChecker.checkDebugging(this))
        {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    if(InternetUtil.isPrivateDnsSetup(this))
                    {
                        Toast.makeText(
                            this,
                            "Please turn off private dns,If not found then search dns in setting search",
                            Toast.LENGTH_LONG
                        ).show()
                        try {
                            startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
                        }
                        catch (e:Exception){
                            Log.d("Exception","msg")
                        }
                    }
                    else
                    {
                        checkEitherEmulatorOrNot()
                    }
                },
                2000
            )

        }
    }

    private fun checkEitherEmulatorOrNot() {
        try {
            val isProbablyRunningOnEmulator: Boolean by lazy {
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

            if (isProbablyRunningOnEmulator)
            {
                Toast.makeText(
                    this,
                    "Please use application on real device",
                    Toast.LENGTH_LONG
                ).show()
            }
            else
            {
                startIntent()
            }

        } catch (e: Exception) {
            Log.d("Exception", "" + e.message)

        }

    }

    private fun startIntent() {
        if (InternetUtil.isInternetOn(this)) {
            binding?.internetLay?.visibility=View.GONE
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, TvMainActivity::class.java)
                startActivity(intent)
                finish()
            }, 1000)
        }
        else
        {
            binding?.internetLay?.visibility=View.VISIBLE
        }

    }

}