package com.dream.live.cricket.score.hd.scores.ui.fragments

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dream.live.cricket.score.hd.BuildConfig
import com.google.firebase.messaging.FirebaseMessaging
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.MoreLayoutBinding
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.SharedPreference
import com.dream.live.cricket.score.hd.utils.Logger


class MoreFragment:Fragment() {

    private val logger= Logger()
    private val tags = "MoreFragment"
    private var binding: MoreLayoutBinding? = null
    private var preference: SharedPreference?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.more_layout,container,false)

        binding = DataBindingUtil.bind(view)
        preference= SharedPreference(requireContext())

        val getStatus=preference?.getBool(Constants.preferenceKey)

        val getAppMode=preference?.getString(Constants.preferenceMode)

        binding?.notificationOnOff2?.isOn = getAppMode.equals("dark",true)

        binding?.notificationOnOff?.isOn = getStatus==true


        binding?.notificationOnOff?.setOnToggledListener { toggleableView, isOn ->

            if (isOn)
            {
                preference?.saveBool(Constants.preferenceKey, true)
                FirebaseMessaging.getInstance().subscribeToTopic("event")
            }
            else
            {
                preference?.saveBool(Constants.preferenceKey, false)
                FirebaseMessaging.getInstance().unsubscribeFromTopic("event")
            }

        }


        binding?.notificationOnOff2?.setOnToggledListener { toggleableView, isOn ->

            if (isOn)
            {
                Constants.modeCheckValue="dark"
                binding?.notificationOnOff2?.isOn=true
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate
                            .MODE_NIGHT_YES)
                preference?.saveString(Constants.preferenceMode,"dark")
            }
            else
            {

                Constants.modeCheckValue="light"
                binding?.notificationOnOff2?.isOn=false
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate
                            .MODE_NIGHT_NO
                    )
                preference?.saveString(Constants.preferenceMode,"light")

            }

        }


        //Rateus Click
        binding?.layoutRateUS?.setOnClickListener {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + requireContext().packageName)
                    )
                )
            }catch (e: ActivityNotFoundException){
                try{
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + requireContext().packageName)
                        )
                    )
                }catch (e:ActivityNotFoundException){
                    logger.printLog(tags, "exception : ${e.localizedMessage}")
                }
            }
        }
        ////Shareus layout....
        binding?.layoutShare?.setOnClickListener {
            try {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(
                    Intent.EXTRA_TEXT, "Please download this app for live  streaming.\n" +
                            "https://play.google.com/store/apps/details?id=" + requireContext().packageName
                )
                intent.type = "text/plain"
                val componentName: ComponentName =
                    intent.resolveActivity(requireContext().packageManager)
                if (componentName != null) {
                    requireContext().startActivity(intent)
                } else {
                    Toast.makeText(context, "No Activity to handle Intent action", Toast.LENGTH_SHORT).show()

                }

            } catch (e: Exception) {
                logger.printLog(tags, "exception : ${e.localizedMessage}")
            }
        }

        binding?.layoutRanking?.setOnClickListener {

            val directions=MoreFragmentDirections.actionMoreFragmentToRankingFragment()
            findNavController().navigate(directions)

        }
        binding?.layoutBrowseTeam?.setOnClickListener {

            val directions=MoreFragmentDirections.actionMoreFragmentToTeamFragment()
            findNavController().navigate(directions)
        }

        binding?.layoutBrowseSeries?.setOnClickListener {

            val directions=MoreFragmentDirections.actionMoreFragmentToSeriesFragment()
            findNavController().navigate(directions)

        }


        binding?.layoutTerms ?.setOnClickListener {
            try {
                val url = ""
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            } catch (e: ActivityNotFoundException) {
                logger.printLog(tags, "exception : ${e.localizedMessage}")
            }
        }

        binding?.layoutFeedBack?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, Array(1) { Constants.mailId})
            intent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name))
            startActivity(Intent.createChooser(intent, "Send Email..."))
        }

        binding?.versiontext?.text= BuildConfig.VERSION_NAME

        ///Privacy policy layout...
        binding?.layoutPP ?.setOnClickListener {
            try {
                val url = ""
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            } catch (e: ActivityNotFoundException) {
                logger.printLog(tags, "exception : ${e.localizedMessage}")
            }
        }
        return view
    }

}