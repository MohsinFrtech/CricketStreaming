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
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.FragmentTeamsRankBinding
import com.dream.live.cricket.score.hd.scores.adapter.PlayersRankAdapterNew
import com.dream.live.cricket.score.hd.scores.model.PlayersRankingModel
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.scores.viewmodel.PlayersRankingViewModel

class AllRounderRankingFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[PlayersRankingViewModel::class.java]
    }

    private var listPlayers: MutableList<PlayersRankingModel> = ArrayList<PlayersRankingModel>()
    private val listPlayerSpecific: MutableList<PlayersRankingModel> =
        ArrayList<PlayersRankingModel>()

    var binding: FragmentTeamsRankBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_teams_rank, container, false)
        binding = DataBindingUtil.bind(view)

        setUpViewModel()

        binding?.leagueRounds?.onItemSelectedListener = this

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

        return binding?.root
    }

    private fun setUpViewModel() {

        viewModel.teamsOdiList.observe(viewLifecycleOwner)
        {

            listPlayers = it?.toMutableList()!!

            formatTeamsSelected(0)
        }
    }


    private fun setAdapter2(list: List<PlayersRankingModel?>) {
        try {
            val listAdapter = PlayersRankAdapterNew(PlayersRankAdapterNew.OnClickListener {
                if (!it.name.isNullOrEmpty()) {
                    /* this.findNavController().navigate(
                         RankingFragmentDirections.actionRankingFragmentToTeamsMatchesFragment(
                             it.player_id!!,
                             it.name
                         )
                     )*/
                }
            })
            binding?.recyclerViewTeams?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding?.recyclerViewTeams?.adapter = listAdapter
            listAdapter.submitList(list)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.labelPonits?.visibility=View.GONE
        binding?.labelName?.text=resources.getString(R.string.player)
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //binding.leagueRounds.selectedItem=parent?.getItemAtPosition(position)
        formatTeamsSelected(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private fun formatTeamsSelected(name: Any) {
        when (name) {
            0 -> {
                viewModel.teamsOdiList.observe(viewLifecycleOwner)
                {

                    if (it != null) {
                        listPlayers = it.toMutableList()
                    }
                    getSpecificList(Cons.match_format_odi)

                }

            }
            1 -> {
                viewModel.teamsT20List?.observe(viewLifecycleOwner)
                {

                    listPlayers = it.toMutableList()
                    getSpecificList(Cons.match_format_t20)

                }


            }
            2 -> {
                viewModel.teamsTestList?.observe(viewLifecycleOwner)
                {
                    listPlayers = it.toMutableList()
                    getSpecificList(Cons.match_format_test)

                }



            }
        }

    }

    private fun getSpecificList(matchFormats: String) {
        listPlayerSpecific.clear()
        listPlayers.forEach {
            if (it.format.equals(matchFormats, true)) {
                if (it.category?.equals(Cons.player_allRounders,true) == true)
                {
                    listPlayerSpecific.add(it)
                }
            }
        }

        listPlayerSpecific.sortBy {
            it.rank
        }

        setAdapter2(listPlayerSpecific)
    }
}