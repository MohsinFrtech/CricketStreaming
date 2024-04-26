package com.dream.live.cricket.score.hd.scores.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dream.live.cricket.score.hd.databinding.ItemTeamNameBinding
import com.dream.live.cricket.score.hd.scores.model.AllTeamsModel
import com.dream.live.cricket.score.hd.scores.ui.fragments.TeamFragmentDirections
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.NavigateData

class TotalTeamsAdapter(private val listSeries: List<AllTeamsModel?>, private val teamFragment: NavigateData): ListAdapter<AllTeamsModel, TotalTeamsAdapter.ViewHolder>(
    DiffCallback
) {

    class ViewHolder(private var binding: ItemTeamNameBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(model : AllTeamsModel){
            binding.model = model
                        binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<AllTeamsModel>(){
        override fun areItemsTheSame(oldItem: AllTeamsModel, newItem: AllTeamsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AllTeamsModel, newItem: AllTeamsModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemTeamNameBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.setIsRecyclable(false)
        val league = currentList[position]
        holder.bind(league)

        holder.itemView.setOnClickListener {

            val direction= league.team_id?.let { it1 ->
                TeamFragmentDirections.actionTeamFragmentToTeamsMatchesFragment(
                    it1,league.team_name)
            }

            direction?.let { it1 -> teamFragment.navigation(it1) }

        }
    }



}