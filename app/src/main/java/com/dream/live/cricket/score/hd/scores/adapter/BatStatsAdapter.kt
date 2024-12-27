package com.dream.live.cricket.score.hd.scores.adapter

import android.R.attr.content
import android.R.attr.text
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dream.live.cricket.score.hd.databinding.ScoreCardItemBinding
import com.dream.live.cricket.score.hd.scores.model.BatsmenData


class BatStatsAdapter(val context: Context) :
    ListAdapter<BatsmenData, BatStatsAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: ScoreCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(batData: BatsmenData) {
            if (batData.balls!=null)
            {
                if (batData.balls!! >0){
                    binding?.battingTeamScores?.visibility=View.VISIBLE
                    binding?.teamTxt2?.text = batData.batName.toString()
                    binding?.team12?.text = batData.runs?.toString()
                    binding?.team22?.text = batData.balls?.toString()
                    binding?.team32?.text = batData?.fours?.toString()
                    binding?.team42?.text = batData?.sixes?.toString()
                    binding?.teamTotal2?.text = batData?.strikeRate?.toString()

                    if (!batData.outDesc.isNullOrEmpty()){
                        binding?.outStatusValue?.text = batData.outDesc.toString()
                        binding?.outLay?.visibility=View.VISIBLE
                    }
                    else
                    {
                        binding?.outLay?.visibility=View.GONE
                    }
                }
                else
                {
                    binding?.battingTeamScores?.visibility=View.GONE
                }
            }
            else
            {
                binding?.battingTeamScores?.visibility=View.GONE
            }


        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<BatsmenData>() {
        override fun areItemsTheSame(oldItem: BatsmenData, newItem: BatsmenData): Boolean {
            return oldItem.batId == newItem.batId
        }

        override fun areContentsTheSame(oldItem: BatsmenData, newItem: BatsmenData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ScoreCardItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val batData = currentList[position]
        holder.bind(batData)
    }


}