package com.dream.live.cricket.score.hd.scores.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.ItemTeamRankBinding
import com.dream.live.cricket.score.hd.scores.model.RankingTeams
import com.dream.live.cricket.score.hd.streaming.utils.objects.CodeUtils.setSafeOnClickListener

class TeamRankAdapterNew(private val onClickListener: (RankingTeams) -> Unit) :
    ListAdapter<RankingTeams, TeamRankAdapterNew.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTeamRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)

        holder.binding.apply {
            model = data
            tvRankId.text = data.rank.toString()
            tvPoints.text = data.points.toString()
            if (data.rating != null) {
                tvRatings.text = data.rating.toString()
            } else {
                tvRatings.text = "0"
            }

            when (data.team_id) {
                4 -> ivTeamLogo.setImageResource(R.drawable.australia)
                2 -> ivTeamLogo.setImageResource(R.drawable.india)
                6 -> ivTeamLogo.setImageResource(R.drawable.bangladesh)
                5 -> ivTeamLogo.setImageResource(R.drawable.srilanka)
                13 -> ivTeamLogo.setImageResource(R.drawable.newzealand)
                9 -> ivTeamLogo.setImageResource(R.drawable.england)
                3 -> ivTeamLogo.setImageResource(R.drawable.pakistan)
                10 -> ivTeamLogo.setImageResource(R.drawable.westindies)
                96 -> ivTeamLogo.setImageResource(R.drawable.afghanistan)
                11 -> ivTeamLogo.setImageResource(R.drawable.southafrica)
                27 -> ivTeamLogo.setImageResource(R.drawable.ireland)
                23 -> ivTeamLogo.setImageResource(R.drawable.scotland)
                12 -> ivTeamLogo.setImageResource(R.drawable.zimbabwe)
                24 -> ivTeamLogo.setImageResource(R.drawable.netherland)
                72 -> ivTeamLogo.setImageResource(R.drawable.nepal)
                304 -> ivTeamLogo.setImageResource(R.drawable.oman)
                161 -> ivTeamLogo.setImageResource(R.drawable.namibia)
                15 -> ivTeamLogo.setImageResource(R.drawable.us)
                7 -> ivTeamLogo.setImageResource(R.drawable.uae)
                287 -> ivTeamLogo.setImageResource(R.drawable.papuanew)
                else -> ivTeamLogo.setImageResource(R.drawable.placehold)
            }

            root.setSafeOnClickListener {
                onClickListener(data)
            }
        }
    }

    inner class ViewHolder(val binding: ItemTeamRankBinding) : RecyclerView.ViewHolder(binding.root)

    companion object DiffCallback : DiffUtil.ItemCallback<RankingTeams>() {
        override fun areItemsTheSame(oldItem: RankingTeams, newItem: RankingTeams): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: RankingTeams, newItem: RankingTeams): Boolean {
            return oldItem == newItem
        }
    }
}
