package com.dream.live.cricket.score.hd.scores.ui.fragments.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.FragmentRankingBinding
import com.dream.live.cricket.score.hd.scores.adapter.RankingsPagerAdapter
import com.dream.live.cricket.score.hd.scores.utility.listeners.ApiResponseListener
import com.dream.live.cricket.score.hd.scores.viewmodel.PlayersRankingViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class RankingFragment : Fragment(), ApiResponseListener {

    private var binding: FragmentRankingBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[PlayersRankingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_ranking, container, false)
        binding = DataBindingUtil.bind(view)
        binding?.viewModel = viewModel
        viewModel.apiResponseListener = this
        binding?.ivBackArrow?.setOnClickListener {
            findNavController().popBackStack()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          setUpViewPager()
    }


    private fun setUpViewPager() {
        val rankingsArray = resources.getStringArray(R.array.browse_rankings)
        val fragmentAdapter = RankingsPagerAdapter(childFragmentManager, this.lifecycle)
        binding?.viewPagerRanking?.isUserInputEnabled = true
        binding?.tabsRanking?.tabGravity = TabLayout.GRAVITY_FILL
        binding?.viewPagerRanking?.adapter = fragmentAdapter
        binding?.tabsRanking.let {
            binding?.viewPagerRanking.let { it1 ->
                if (it != null) {
                    if (it1 != null) {
                        TabLayoutMediator(
                            it, it1
                        ) { tab: TabLayout.Tab, position: Int ->
                            tab.text = rankingsArray[position]
                        }.attach()
                    }
                }
            }
        }

        hideLoading()

        binding?.tabsRanking?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding?.tabsRanking?.setTabTextColors(resources.getColor(R.color.textColor), resources.getColor(R.color.white));

    }


    override fun onStarted() {
    }

    override fun onSuccess() {
        //observeData()
    }

    override fun onFailure(message: String) {
    }


    private fun hideLoading() {
        binding?.rotateloadingRanking?.visibility = View.GONE
    }

}