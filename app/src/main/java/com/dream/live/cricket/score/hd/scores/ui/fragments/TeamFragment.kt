package com.dream.live.cricket.score.hd.scores.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.dream.live.cricket.score.hd.databinding.FragmentTeamsBinding
import com.dream.live.cricket.score.hd.scores.adapter.TotalTeamsAdapter
import com.dream.live.cricket.score.hd.scores.model.AllTeamsModel
import com.dream.live.cricket.score.hd.scores.viewmodel.AllTeamViewModel
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.NavigateData
import java.util.*

class TeamFragment:Fragment() , NavigateData {


    var binding: FragmentTeamsBinding?=null
    private val teamsViewModel by lazy {
        ViewModelProvider(requireActivity())[AllTeamViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_teams,container,false)
        binding=DataBindingUtil.bind(view)
        binding?.lifecycleOwner=this
        binding?.viewModel=teamsViewModel
        setUpViewModel()

        binding?.ivBackArrow?.setOnClickListener {
            findNavController().popBackStack()

        }
        return view
    }

    private fun setUpViewModel() {
        teamsViewModel.isLoading.observe(viewLifecycleOwner)
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

        teamsViewModel.teamList.observe(viewLifecycleOwner)
        {
            if (!it.isNullOrEmpty())
            {
                setAdapter(it)
            }


        }

    }


    private fun setAdapter(liveTeams: List<AllTeamsModel?>) {
        try {
//            binding?.progressBar?.visibility=View.GONE
            val listAdapter = TotalTeamsAdapter(liveTeams,this)
            binding?.rvTeams?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding?.rvTeams?.adapter = listAdapter
            listAdapter.submitList(liveTeams)
            searchTeams(listAdapter,liveTeams)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun searchTeams(adapter: TotalTeamsAdapter, list: List<AllTeamsModel?>) {
        binding?.etSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                search(s.toString(), list as MutableList<AllTeamsModel>, adapter)
            }
        })
    }

    private fun search(
        text: String,
        liveAndNonemptyEvents: MutableList<AllTeamsModel>,
        adapterEvent: TotalTeamsAdapter?
    ) {
        //new array list that will hold the filtered data
        val localList: MutableList<AllTeamsModel> =
            ArrayList<AllTeamsModel>()
        //looping through existing elements
        for (s in liveAndNonemptyEvents) {
            //if the existing elements contains the search input
            if (s.team_name?.trim()?.lowercase(Locale.ROOT)?.contains(text.lowercase(Locale.ROOT))!!) {
                //adding the element to filtered list
                localList.add(s)
            }
        }
        val listAdapter = TotalTeamsAdapter(localList, this)
        binding?.rvTeams?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.rvTeams?.adapter = listAdapter
        listAdapter.submitList(localList)

    }

    override fun navigation(viewId: NavDirections) {

        if (viewId!=null)
        {
            findNavController().navigate(viewId)
        }
    }
}