package com.dream.live.cricket.score.hd.scores.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.UpcomingFragmentMatchesBinding
import com.dream.live.cricket.score.hd.scores.adapter.RecentMatchesAdapter
import com.dream.live.cricket.score.hd.scores.model.LiveScoresModel
import com.dream.live.cricket.score.hd.scores.viewmodel.UpcomingMatchesViewModel
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.NavigateData
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.checkNativeAdProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.MyNativeAd
import java.util.ArrayList

class UpcomingMatchesFragment:Fragment() , NavigateData,AdManagerListener {

    private val upcomingViewModel by lazy {
        ViewModelProvider(requireActivity())[UpcomingMatchesViewModel::class.java]
    }

    private val liveScores: MutableList<LiveScoresModel> =
        ArrayList<LiveScoresModel>()
    private var adManager: AdManager? = null

    companion object {
        fun newInstance(state: String): UpcomingMatchesFragment {
            val matchesFragment = UpcomingMatchesFragment()
            val bundle = Bundle()
            bundle.putString("Fragment_format", state)
            matchesFragment.arguments = bundle
            return matchesFragment
        }
    }

    var bindingUpcomingMatches: UpcomingFragmentMatchesBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.upcoming_fragment_matches,container,false)
        bindingUpcomingMatches=DataBindingUtil.bind(view)
        bindingUpcomingMatches?.lifecycleOwner=this
        bindingUpcomingMatches?.viewModel=upcomingViewModel
        adManager =  AdManager(requireContext(), requireActivity(), this)

        setUpViewModel()
        return view
    }


    private fun setUpViewModel() {
        upcomingViewModel.isTabSelect.observe(viewLifecycleOwner, Observer {
            showFilteredList(it)
        })
    }

    private fun showFilteredList(s: String) {


        upcomingViewModel.isLoading.observe(viewLifecycleOwner)
        {
            if (it) {
                bindingUpcomingMatches?.progressBar?.visibility = View.VISIBLE
            } else {
                bindingUpcomingMatches?.progressBar?.visibility = View.GONE
            }


        }

        upcomingViewModel.sliderList.observe(viewLifecycleOwner)
        {

            liveScores.clear()

            if (!it.isNullOrEmpty()) {
                bindingUpcomingMatches?.tvNoData?.visibility = View.GONE

                for (match in it) {

                    if (match?.match_format.equals(s, true)) {
                        match?.let { it1 -> liveScores.add(it1) }
                    }
                }
                if(!liveScores.isNullOrEmpty()){

                }
                else{
                    bindingUpcomingMatches?.tvNoData?.visibility = View.VISIBLE
                }
                setAdapter2(liveScores)
            } else {
                bindingUpcomingMatches?.tvNoData?.visibility = View.VISIBLE

            }


        }
    }





    private fun setAdapter2(liveScores: List<LiveScoresModel?>) {
        try {
            var tFormatList: MutableList<LiveScoresModel?> = liveScores.toMutableList()
            if(!tFormatList.isNullOrEmpty()) {
                tFormatList.sortBy {
                    it?.id
                }

                val listWithAd: List<LiveScoresModel?> =
                    if (checkNativeAdProvider != "none") {
                        MyNativeAd.checkNativeAd(tFormatList)
                    } else {
                        tFormatList
                    }
//            binding?.progressBar?.visibility=View.GONE
                val listAdapter = adManager?.let {
                    RecentMatchesAdapter(requireContext(),this,"recent",listWithAd, checkNativeAdProvider,
                        it,null
                    )
                }
                bindingUpcomingMatches?.rvMatches?.visibility=View.VISIBLE

                bindingUpcomingMatches?.rvMatches?.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                bindingUpcomingMatches?.rvMatches?.adapter = listAdapter
                listAdapter?.submitList(listWithAd)

            }else
            {
                bindingUpcomingMatches?.rvMatches?.visibility=View.GONE
                bindingUpcomingMatches?.tvNoData?.visibility = View.VISIBLE

            }
        } catch (e: Exception) {
            Log.d("Exception","msg")
        }
    }

    override fun navigation(viewId: NavDirections) {

    }

    override fun onAdLoad(value: String) {

    }

    override fun onAdFinish() {

    }
}