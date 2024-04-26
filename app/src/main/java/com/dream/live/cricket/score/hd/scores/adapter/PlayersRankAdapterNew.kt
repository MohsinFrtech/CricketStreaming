package com.dream.live.cricket.score.hd.scores.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.ItemPlayerRankBinding
import com.dream.live.cricket.score.hd.scores.model.PlayersRankingModel

class PlayersRankAdapterNew(private val onClickListener: OnClickListener) :
    ListAdapter<PlayersRankingModel, PlayersRankAdapterNew.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemPlayerRankBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlayersRankingModel) {
            binding.apply {
                model = item
                tvRankId.text = item.rank.toString()
                if (item.avg != null) {
                    tvPoints.text = item.avg.toString()
                } else {
                    tvPoints.text = ""
                }
                tvRatings.text = item.rating.toString()
                ivTeamLogo.setImageResource(R.drawable.avatar_icon)

                executePendingBindings()
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<PlayersRankingModel>() {
        override fun areItemsTheSame(
            oldItem: PlayersRankingModel,
            newItem: PlayersRankingModel
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: PlayersRankingModel,
            newItem: PlayersRankingModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlayerRankBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
    }

    class OnClickListener(val clickListener: (item: PlayersRankingModel) -> Unit) {
        fun onClick(item: PlayersRankingModel) = clickListener(item)
    }
}
