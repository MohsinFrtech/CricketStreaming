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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.dream.live.cricket.score.hd.MainActivity
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.ActivitySplashBinding
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.DialogListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.CustomDialogue
import com.dream.live.cricket.score.hd.streaming.utils.objects.DebugChecker
import com.dream.live.cricket.score.hd.streaming.utils.objects.SharedPreference
import com.dream.live.cricket.score.hd.utils.InternetUtil
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

///Home Screen ....
class HomeScreen: AppCompatActivity(), DialogListener {

    private var bindingHome: ActivitySplashBinding?=null
    private var permissionCount= 0
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {

                bindingHome?.notificationLayout?.visibility= View.GONE
                // Permission is granted. Continue the action or workflow in your
                subscribeOrUnSubscribeTopic()

            } else {
                bindingHome?.notificationLayout?.visibility= View.VISIBLE

            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingHome= DataBindingUtil.setContentView(this, R.layout.activity_splash)
        //Initialize firebase instance...
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        window.navigationBarColor = ContextCompat.getColor(this, R.color.noChange)

        FirebaseApp.initializeApp(this)

        bindingHome?.retry?.setOnClickListener {
            if (!DebugChecker.checkDebugging(this))
            {
                emulatorCheck()
            }


        }

        bindingHome?.yesBtn?.setOnClickListener{
            bindingHome?.notificationLayout?.visibility= View.GONE
            makePermission()
        }
        bindingHome?.skipBtn?.setOnClickListener{

//            requestPermissionLauncher.unregister()
            bindingHome?.notificationLayout?.visibility= View.GONE
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
                    bindingHome?.notificationLayout?.visibility= View.VISIBLE

                }
                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    makePermission()

                }

            }
        }
        else
        {
            subscribeOrUnSubscribeTopic()
        }


    }

    private fun makePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (permissionCount>3)
            {
                bindingHome?.notificationLayout?.visibility= View.GONE

               navigationToNextScreen()

            }
            else if (permissionCount==2)
            {
                bindingHome?.notificationLayout?.visibility= View.GONE

                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts(
                    "package",
                    packageName, null
                )
                intent.data = uri
                startActivity(intent)
            }
            else
            {
                requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }

            permissionCount++

        }
    }



    private fun subscribeOrUnSubscribeTopic() {
        val preference= SharedPreference(this)
        val getStatus=preference.getBool(Constants.preferenceKey)

        val modeValue=preference.getString(Constants.preferenceMode)

        if (modeValue.equals("dark",true)) {

            Constants.modeCheckValue="dark"
        } else {
            Constants.modeCheckValue="light"

        }
        if (getStatus == true)
        {
            navigationToNextScreen()
            ///means already subscribe to topic...
        }
        else
        {


            FirebaseMessaging.getInstance().subscribeToTopic("event")
                .addOnCompleteListener { task ->
                    if (task.isComplete) {
                        //
                        preference.saveBool(Constants.preferenceKey,true)

                    }
                }

            navigationToNextScreen()
        }


    }

    override fun onResume() {
        super.onResume()


        if (!DebugChecker.checkDebugging(this))
        {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    emulatorCheck()

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
                this,"Alert!","Please use application on real device",
                "","Ok","baseValue"
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
        if (InternetUtil.isInternetOn(this))
        {
            bindingHome?.lottieAnimLayout?.visibility= View.GONE
            bindingHome?.noInternetText?.visibility= View.GONE
            bindingHome?.retry?.visibility= View.GONE
            moveToMainScreen()
        }
        else
        {
            bindingHome?.lottieAnimLayout?.visibility= View.VISIBLE
            bindingHome?.noInternetText?.visibility= View.VISIBLE
            bindingHome?.retry?.visibility= View.VISIBLE
            bindingHome?.homeAnimLayout?.visibility= View.GONE


        }
    }

    private fun moveToMainScreen() {
        bindingHome?.homeAnimLayout?.visibility= View.GONE
        if (isDeviceRooted())
        {
            CustomDialogue(this).showDialog(
                this,"Alert!","Please use application on real device",
                "","Ok","baseValue"
            )
        }
        else {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
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

    }

    override fun onNegativeDialogText(key: String) {

        when (key) {
            "baseValue" -> finishAffinity()
            "isInternet" -> finishAffinity()
            "eventValue" -> startActivity(Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS))

        }
    }







}