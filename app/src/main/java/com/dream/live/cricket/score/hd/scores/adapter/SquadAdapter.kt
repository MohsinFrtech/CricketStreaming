package com.dream.live.cricket.score.hd.scores.adapter

import android.R.attr.text
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dream.live.cricket.score.hd.databinding.SquadLayoutBinding
import com.dream.live.cricket.score.hd.scores.model.PlayerDetails


class SquadAdapter() :
    ListAdapter<PlayerDetails, SquadAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: SquadLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(squadModel: PlayerDetails) {
            binding?.playerName?.text = squadModel.fullName
            binding?.playerRole?.text = squadModel?.role
                .toString()
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlayerDetails>() {
        override fun areItemsTheSame(oldItem: PlayerDetails, newItem: PlayerDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlayerDetails, newItem: PlayerDetails): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(SquadLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val league = currentList[position]
        holder.bind(league)
    }


}