package com.dream.live.cricket.score.hd.scores.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.FragmentRecentBinding
import com.dream.live.cricket.score.hd.scores.adapter.RecentlMatchesPagerAdapter
import com.dream.live.cricket.score.hd.scores.viewmodel.AllMatchesViewModel
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.odiFormat
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.t20Format
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.testFormat

class RecentFragment:Fragment() {


    var binding: FragmentRecentBinding?=null
    private val recentViewModel by lazy {
        ViewModelProvider(requireActivity())[AllMatchesViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_recent,container,false)
        binding=DataBindingUtil.bind(view)
        binding?.lifecycleOwner=this
        setUpViewPager()

        return view
    }

    private fun setUpViewPager() {
        val recentStatus = resources.getStringArray(R.array.matches_formats)
        val fragmentAdapter = RecentlMatchesPagerAdapter(this, "2")
        binding?.viewPagerRecent?.isUserInputEnabled = true
        binding?.tabsRecent?.tabGravity = TabLayout.GRAVITY_FILL
        binding?.viewPagerRecent?.currentItem=0
        binding?.viewPagerRecent?.adapter = fragmentAdapter
        binding?.tabsRecent?.let {
            binding?.viewPagerRecent?.let { it1 ->
                TabLayoutMediator(it, it1
                ) { tab: TabLayout.Tab, position: Int ->

                    tab.text = recentStatus[position]
                }.attach()
            }
        }


        binding?.tabsRecent?.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {
//                tab?.position?.let { RecentMatchesFragment.newInstance(it) }
                if (tab?.position==0)
                {
                    recentViewModel.isTabSelect.value=t20Format

                }
                else if (tab?.position==1)
                {
                    recentViewModel.isTabSelect.value= odiFormat

                }
                else if (tab?.position==2)
                {
                    recentViewModel.isTabSelect.value= testFormat

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                if (tab?.position==0)
                {
                    recentViewModel.isTabSelect.value= t20Format

                }
                else if (tab?.position==1)
                {
                    recentViewModel.isTabSelect.value= odiFormat

                }
                else if (tab?.position==2)
                {
                    recentViewModel.isTabSelect.value= testFormat

                }

            }
        })

        binding?.tabsRecent?.setTabTextColors(resources.getColor(R.color.textColor), resources.getColor(R.color.white));

    }
}