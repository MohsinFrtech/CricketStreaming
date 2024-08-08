package com.dream.live.cricket.score.hd.scores.ui.fragments.matches

import android.os.Bundle
import android.util.Log
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
import com.dream.live.cricket.score.hd.databinding.FragmentMatchesCategoryBinding
import com.dream.live.cricket.score.hd.scores.adapter.RecentMatchesAdapter
import com.dream.live.cricket.score.hd.scores.model.LiveScoresModel
import com.dream.live.cricket.score.hd.scores.viewmodel.MatchesCategoryViewModel
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.NavigateData
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.checkNativeAdProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.MyNativeAd
import java.util.ArrayList


class MatchesCategoryFragment : Fragment(), NavigateData ,AdManagerListener{

    private val recentViewModel by lazy {
        ViewModelProvider(requireActivity())[MatchesCategoryViewModel::class.java]
    }
    private var adManager: AdManager? = null
    private val liveScores: MutableList<LiveScoresModel> = ArrayList<LiveScoresModel>()
    private var tabSelect = ""
    var binding: FragmentMatchesCategoryBinding? = null


    companion object {
        fun newInstance(state: String): MatchesCategoryFragment {
            val matchesFragment = MatchesCategoryFragment()
            val bundle = Bundle()
            bundle.putString("Fragment_format", state)
            matchesFragment.arguments = bundle
            return matchesFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (arguments != null) {
            tabSelect = requireArguments().getString("Fragment_format", "abc")

        }

        val view = inflater.inflate(R.layout.fragment_matches_category, container, false)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
        binding?.viewModel = recentViewModel
        adManager =  AdManager(requireContext(), requireActivity(), this)

        setUpViewModel()

        return binding?.root
    }

    private fun setUpViewModel() {

        recentViewModel.isTabSelect.observe(this.viewLifecycleOwner)
        {


            showFilteredList(it)

        }
    }

    private fun showFilteredList(s: String) {


        recentViewModel.isLoading.observe(this.viewLifecycleOwner)
        {

            if (it) {
                binding?.progressBar?.visibility = View.VISIBLE
            } else {
                binding?.progressBar?.visibility = View.GONE
            }


        }

        recentViewModel.matchesList.observe(viewLifecycleOwner)
        {

            liveScores.clear()

            if (!it.isNullOrEmpty()) {
                for (match in it) {

                    if (match?.state.equals(s, true)) {
                        match?.let { it1 -> liveScores.add(it1) }
                    }
                }
                if (liveScores.isNotEmpty()) {
                    binding?.tvNoData?.visibility = View.GONE
                    binding?.rvMatches?.visibility = View.VISIBLE
                    setAdapter2(liveScores)
                } else {
                    binding?.tvNoData?.visibility = View.VISIBLE
                    binding?.rvMatches?.visibility = View.GONE
                }
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
                val listAdapter = adManager?.let {
                    RecentMatchesAdapter(requireContext(), this, "recent",listWithAd, checkNativeAdProvider,
                        it,null
                    )
                }
                binding?.rvMatches?.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding?.rvMatches?.adapter = listAdapter
                listAdapter?.submitList(listWithAd)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun navigation(viewId: NavDirections) {
        try {
            findNavController().navigate(viewId)
        } catch (e: Exception) {
            Log.d("Exception", "message")
        }
    }

    override fun onAdLoad(value: String) {

    }

    override fun onAdFinish() {

    }


}