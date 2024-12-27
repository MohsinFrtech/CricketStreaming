package com.dream.live.cricket.score.hd.scores.adapter

import android.content.Context
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dream.live.cricket.score.hd.databinding.ScoreboardItemBinding
import com.dream.live.cricket.score.hd.scores.model.ScoreCardMatch


class ScoreBoardAdapter(private val context: Context) :
    ListAdapter<ScoreCardMatch, ScoreBoardAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: ScoreboardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(matchScoreCard: ScoreCardMatch, contextScore: Context) {
            if (matchScoreCard.batTeamDetails != null) {
                var inning_id=""
                if (matchScoreCard.inningsId==1){
                    inning_id ="1st"
                }
                else if (matchScoreCard.inningsId==2)
                {
                    inning_id ="1st"
                }
                else if (matchScoreCard.inningsId==3)
                {
                    inning_id ="2nd"
                } else if (matchScoreCard.inningsId==4)
                {
                    inning_id ="2nd"
                }


                binding?.teamInning?.text = matchScoreCard.batTeamDetails!!.batTeamName?.toString()+" "+inning_id.toString()+" Inning"
                if (matchScoreCard.scoreDetails != null) {
                    binding?.teamScore?.text = matchScoreCard.scoreDetails!!.runs.toString() + "-" +
                            matchScoreCard.scoreDetails!!.wickets.toString() + " " + "(" + matchScoreCard.scoreDetails!!
                        .overs.toString() + " Ov" + ")"
                }
                //if batting team details are available....
                if (!matchScoreCard.batTeamDetails!!.batsmenData.isNullOrEmpty()) {
                    // if batting team  array is not empty...
                    val newList = matchScoreCard.batTeamDetails!!.batsmenData.filter {
                        it.balls != null && it.balls!! > 0
                    }
                    val listAdapter =
                        BatStatsAdapter(contextScore)
                    binding?.battingDataRecycler?.layoutManager =
                        LinearLayoutManager(contextScore)
                    binding?.battingDataRecycler?.adapter = listAdapter
                    listAdapter.submitList(newList)
                }

                //for setting extra data...
                if (matchScoreCard.extrasData != null) {
                    if (matchScoreCard.extrasData!!.total != null) {
                        if (matchScoreCard.extrasData!!.total!! > 0) {
                            binding?.extraRecord?.visibility = View.VISIBLE
                            ///Extra record is available....
                            val extraString =
                                "(" + "b " + matchScoreCard?.extrasData?.byes.toString() +
                                        ", lb " + matchScoreCard.extrasData!!.legByes + ", w " +
                                        matchScoreCard.extrasData!!.wides.toString() + ", nb " +
                                        matchScoreCard.extrasData!!.noBalls.toString() + ", p " + matchScoreCard.extrasData!!.penalty.toString() + ")"
                            val s = SpannableStringBuilder()
                                .bold { append(matchScoreCard?.extrasData?.total.toString()) }
                                .append(extraString)
                            binding?.extraRecord?.visibility = View.VISIBLE
                            binding?.extraValue?.text = s

                        } else {
                            binding?.extraRecord?.visibility = View.GONE
                        }
                    }
                }

                //for setting total
                if (matchScoreCard.scoreDetails != null) {

                    if (matchScoreCard.scoreDetails!!.overs != null) {
                        if (matchScoreCard.scoreDetails!!.overs!! > 0.0) {
                            binding?.Total?.visibility = View.VISIBLE
                            val setTotal =
                                "(" + " " + matchScoreCard.scoreDetails!!.wickets?.toString() + " wkts" +
                                        "," + matchScoreCard.scoreDetails!!.overs.toString() +
                                        " Ov" + ")"
                            val s = SpannableStringBuilder()
                                .bold { append(matchScoreCard?.scoreDetails?.runs.toString()) }
                                .append(setTotal)
                            binding?.Total?.visibility = View.VISIBLE
                            binding?.TotalValue?.text = s
                        } else {
                            binding?.Total?.visibility = View.GONE
                        }
                    }

                }

                if (!matchScoreCard.bowlTeamDetails!!.bowlersData.isNullOrEmpty()) {
                    // if batting team  array is not empty...
                    val newList = matchScoreCard.bowlTeamDetails!!.bowlersData.filter {
                        it.overs != null && it.overs!! > 0.0
                    }
                    val listAdapter =
                        BowlerStatsAdapter(contextScore)
                    binding?.bowlingDataRecycler?.layoutManager =
                        LinearLayoutManager(contextScore)
                    binding?.bowlingDataRecycler?.adapter = listAdapter
                    listAdapter.submitList(newList)
                }

                if (!matchScoreCard.wicketsData.isNullOrEmpty()) {

                    val stringBuilder=StringBuilder()
                    matchScoreCard.wicketsData.forEach {
                        wicketsData ->
                        val string = ""+wicketsData.wktRuns.toString()+"-"+
                                wicketsData.wktNbr.toString()+"("+
                                wicketsData.batName.toString()+", "+wicketsData.wktOver.toString()+" Ov)"+", "
                        stringBuilder.append(string)
                    }

                    binding?.fallOfWckValue?.text = stringBuilder.toString()
                    binding?.fallWickets?.visibility=View.VISIBLE
                    binding?.fallOfWckValue?.visibility=View.VISIBLE
                }
                else
                {
                    binding?.fallWickets?.visibility=View.GONE
                    binding?.fallOfWckValue?.visibility=View.GONE

                }

            }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<ScoreCardMatch>() {
        override fun areItemsTheSame(oldItem: ScoreCardMatch, newItem: ScoreCardMatch): Boolean {
            return oldItem.inningsId == newItem.inningsId
        }

        override fun areContentsTheSame(oldItem: ScoreCardMatch, newItem: ScoreCardMatch): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ScoreboardItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val league = currentList[position]
        holder.bind(league, context)
    }


}