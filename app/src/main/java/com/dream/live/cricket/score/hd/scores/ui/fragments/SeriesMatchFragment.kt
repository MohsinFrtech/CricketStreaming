package com.dream.live.cricket.score.hd.scores.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.FragmentMatchesBinding
import com.dream.live.cricket.score.hd.scores.adapter.RecentMatchesAdapter
import com.dream.live.cricket.score.hd.scores.model.LiveScoresModel
import com.dream.live.cricket.score.hd.scores.viewmodel.SeriesMatchesViewModel
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.NavigateData
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.checkNativeAdProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.MyNativeAd

class SeriesMatchFragment : Fragment(), NavigateData,AdManagerListener {


    var bindingMatches: FragmentMatchesBinding? = null
    private val seriesViewModel by lazy {
        ViewModelProvider(requireActivity())[SeriesMatchesViewModel::class.java]
    }

    private var adManager: AdManager? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_matches, container, false)
        bindingMatches = DataBindingUtil.bind(view)
        bindingMatches?.lifecycleOwner = this
        bindingMatches?.viewmodel = seriesViewModel
        adManager =  AdManager(requireContext(), requireActivity(), this)

        setUpViewModel()
        return view
    }

    private fun setUpViewModel() {

        seriesViewModel.againLoad()
        seriesViewModel.sliderList.observe(viewLifecycleOwner)
        {
            if (!it.isNullOrEmpty()) {

                bindingMatches?.tvNoData?.visibility = View.GONE
                setAdapter2(it)
            } else {
                bindingMatches?.tvNoData?.visibility = View.VISIBLE
            }


        }
    }

    private fun setAdapter2(liveScores: List<LiveScoresModel?>) {
        try {
            val listWithAd: List<LiveScoresModel?> =
                if (checkNativeAdProvider != "none") {
                    MyNativeAd.checkNativeAd(liveScores)
                } else {
                    liveScores
                }
            val listAdapter = adManager?.let {
                RecentMatchesAdapter(requireContext(), this, "seriesMatch",listWithAd, checkNativeAdProvider,
                    it,null
                )
            }
            bindingMatches?.rvSeries?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            bindingMatches?.rvSeries?.adapter = listAdapter
            listAdapter?.submitList(liveScores)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun navigation(viewId: NavDirections) {

        findNavController().navigate(viewId)
    }

    override fun onAdLoad(value: String) {

    }

    override fun onAdFinish() {

    }


}