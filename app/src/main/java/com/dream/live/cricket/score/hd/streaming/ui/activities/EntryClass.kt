package com.dream.live.cricket.score.hd.streaming.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class EntryClass:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }

        val Intent = Intent(this,HomeScreen::class.java)
        startActivity(Intent)
        finish()
    }
}