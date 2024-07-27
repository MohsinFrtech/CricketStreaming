package com.dream.live.cricket.score.hd.tv.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.streaming.viewmodel.OneViewModel

class TvChannelActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_channel)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )


        val viewModel by lazy {
            ViewModelProvider(this)[OneViewModel::class.java]
        }

        viewModel.getApiData()
    }
}