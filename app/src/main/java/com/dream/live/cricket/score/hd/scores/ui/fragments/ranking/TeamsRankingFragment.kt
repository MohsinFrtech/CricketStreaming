package com.dream.live.cricket.score.hd.scores.ui.fragments.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.FragmentTeamsRankBinding
import com.dream.live.cricket.score.hd.scores.adapter.TeamRankAdapterNew
import com.dream.live.cricket.score.hd.scores.model.RankingTeams
import com.dream.live.cricket.score.hd.scores.viewmodel.RankingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TeamsRankingFragment : Fragment(), AdapterView.OnItemSelectedListener {

    var binding: FragmentTeamsRankBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[RankingViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_teams_rank, container, false)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this

        showLoading()

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            viewModel.getSpinnerData()
        )
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        binding?.leagueRounds?.adapter = aa

        binding?.leagueRounds?.onItemSelectedListener = this

        viewModel.teamsOdiList?.observe(viewLifecycleOwner) { it ->

            if (it != null) {
                hideLoading()
            }
        }



        binding?.ivBackArrow?.setOnClickListener {
            findNavController().popBackStack()
        }

        return view
    }


    private fun setAdapter(rankingTeams: List<RankingTeams>?) {
        val sortedList = rankingTeams?.sortedWith(compareBy { it.rank })

        val listAdapter = TeamRankAdapterNew {
            if (!it.name.isNullOrEmpty()) {
                this.findNavController().navigate(

                    TeamsRankingFragmentDirections.actionRankingFragmentToTeamsMatchesFragment(
                        it.team_id!!,
                        it.name
                    )
                )
            }
        }
        this.lifecycleScope.launch(Dispatchers.Main) {
            binding?.recyclerViewTeams?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding?.recyclerViewTeams?.adapter = listAdapter
            listAdapter.submitList(sortedList)
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


//        (parent?.getChildAt(0) as TextView).setTextColor(requireContext().getColor(R.color.white))
        formatTeamsSelected(position)

    }


    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private fun formatTeamsSelected(name: Any) {
        when (name) {
            0 -> {
                viewModel.teamsOdiList?.observe(viewLifecycleOwner) { it ->
                    if (it != null) {

                        setAdapter(it)
                    }
                }
            }
            1 -> {
                viewModel.teamsT20List?.observe(viewLifecycleOwner) { it ->
                    if (it != null) {
                        setAdapter(it)
                    }
                }

            }
            2 -> {
                viewModel.teamsTestList?.observe(viewLifecycleOwner) { it ->
                    if (it != null) {
                        setAdapter(it)
                    }
                }

            }
        }

    }

    private fun showLoading() {
        binding?.homeAnimLayout?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding?.homeAnimLayout?.visibility = View.GONE
    }

}