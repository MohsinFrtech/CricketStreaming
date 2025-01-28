package com.dream.live.cricket.score.hd.scores.ui.fragments

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
import com.dream.live.cricket.score.hd.databinding.FragmentRecentMatchesBinding
import com.dream.live.cricket.score.hd.scores.adapter.RecentMatchesAdapter
import com.dream.live.cricket.score.hd.scores.model.LiveScoresModel
import com.dream.live.cricket.score.hd.scores.viewmodel.AllMatchesViewModel
import com.dream.live.cricket.score.hd.streaming.adsData.AdManager
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.AdManagerListener
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.NavigateData
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.checkNativeAdProvider
import com.dream.live.cricket.score.hd.streaming.utils.objects.MyNativeAd.checkNativeAd
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.NativeAdOptions
import java.util.ArrayList


class RecentMatchesFragment : Fragment(), NavigateData,AdManagerListener{

    private val recentViewModel by lazy {
        ViewModelProvider(requireActivity())[AllMatchesViewModel::class.java]
    }
    private var currentNativeAd: com.google.android.gms.ads.nativead.NativeAd? = null

    private val liveScores: MutableList<LiveScoresModel> =
        ArrayList<LiveScoresModel>()
    private var tabSelect=""
    private var adManager: AdManager? = null

    var binding: FragmentRecentMatchesBinding?=null
    companion object {
        fun newInstance(state: String): RecentMatchesFragment {
            val matchesFragment = RecentMatchesFragment()
            val bundle = Bundle()
            bundle.putString("Fragment_format", state)
            matchesFragment.arguments = bundle
            return matchesFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (arguments!=null) {
            tabSelect = requireArguments().getString("Fragment_format","abc")

        }

        val view=inflater.inflate(R.layout.fragment_recent_matches,container,false)
        binding=DataBindingUtil.bind(view)
        binding?.lifecycleOwner=this
        binding?.viewModel=recentViewModel
        adManager =  AdManager(requireContext(), requireActivity(), this)
        setUpViewModel()

        return binding?.root
    }

    private fun setUpViewModel() {

        recentViewModel.isTabSelect.observe(viewLifecycleOwner)
        {


            showFilteredList(it)

        }
    }

    private fun showFilteredList(s: String) {


        recentViewModel.isLoading.observe(viewLifecycleOwner)
        {

            if (it)
            {
                binding?.progressBar?.visibility=View.VISIBLE
            }
            else
            {
                binding?.progressBar?.visibility=View.GONE
            }


        }

        recentViewModel.sliderList.observe(viewLifecycleOwner)
        {

            liveScores.clear()

            if (!it.isNullOrEmpty())
            {
                binding?.tvNoData?.visibility=View.GONE

                for (match in it)
                {

                    if (match?.match_format.equals(s,true))
                    {
                        match?.let { it1 -> liveScores.add(it1) }
                    }

                }

                if(!liveScores.isNullOrEmpty()){

                }
                else{
                    binding?.tvNoData?.visibility = View.VISIBLE
                }
                setAdapter2(liveScores)
            }
            else
            {
                binding?.tvNoData?.visibility=View.VISIBLE
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
                        checkNativeAd(tFormatList)
                    } else {
                        tFormatList
                    }

                val listAdapter = adManager?.let {
                    RecentMatchesAdapter(requireContext(),this,"recent",listWithAd, checkNativeAdProvider,
                        it,currentNativeAd
                    )
                }
                binding?.rvMatches?.visibility=View.VISIBLE

                binding?.rvMatches?.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding?.rvMatches?.adapter = listAdapter
                listAdapter?.submitList(listWithAd)

            } else
            {
                binding?.rvMatches?.visibility=View.GONE
                binding?.tvNoData?.visibility = View.VISIBLE

            }

        } catch (e: Exception) {
            Log.d("Exception","msg")
        }
    }



    override fun navigation(viewId: NavDirections) {
        try {
            findNavController().navigate(viewId)
        }
        catch (e:Exception)
        {
            Log.d("Exception","message")
        }
    }

    override fun onAdLoad(value: String) {

    }

    override fun onAdFinish() {

    }


}