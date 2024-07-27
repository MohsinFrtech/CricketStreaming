package com.dream.live.cricket.score.hd.scores.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.ItemSeriesLayoutBinding
import com.dream.live.cricket.score.hd.scores.model.SeriesScoresModel
import com.dream.live.cricket.score.hd.scores.ui.fragments.BrowseFragmentDirections
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.NavigateData
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.streaming.utils.objects.CodeUtils.setSafeOnClickListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.selectedSeriesId
import java.text.SimpleDateFormat
import java.util.*


class SeriesItemAdapter(val context: Context, private val navigateData: NavigateData,
                        val source:String) :
    ListAdapter<SeriesScoresModel, SeriesItemAdapter.LiveSliderAdapterViewHolder>(
        LiveSliderAdapterDiffUtilCallback
    ) {


    class LiveSliderAdapterViewHolder(private var binding: ItemSeriesLayoutBinding,
        private var context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bindSeriesData(seriesScoresModel: SeriesScoresModel) {
            binding?.model = seriesScoresModel
            if (seriesScoresModel.start_date != null && seriesScoresModel.end_date != null) {

                val valueShow= seriesScoresModel.start_date?.let { Cons.convertDateAndTime(it) }
                dateAndTime(valueShow, "start")

                val valueShow2= seriesScoresModel.end_date?.let { Cons.convertDateAndTime(it) }
                dateAndTime(valueShow2, "end")

            }
            binding?.executePendingBindings()

        }
        private fun dateAndTime(startDate: String?,point: String?) {
            val df = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH)
            val date = startDate.let { it?.let { it1 -> df.parse(it1) } }
            val dayOfTheWeek =
                DateFormat.format("EEEE", date) as String
            val day = DateFormat.format("dd", date) as String
            val monthString =
                DateFormat.format("MMM", date) as String
            val year = DateFormat.format("yyyy", date) as String


            if (point.equals("start",true)) {
                binding?.tvFirstTeam?.text =
                    context.getString(R.string.dateAndTime, dayOfTheWeek, day, monthString, year)

                binding?.tvFirstTeam?.visibility = View.VISIBLE
            }
            else
            {
                binding?.tvSecondTeam?.text =
                    context.getString(R.string.dateAndTime, dayOfTheWeek, day, monthString, year)

                binding?.tvSecondTeam?.visibility = View.VISIBLE
            }



        }
    }

    object LiveSliderAdapterDiffUtilCallback : DiffUtil.ItemCallback<SeriesScoresModel>() {

        override fun areItemsTheSame(
            oldItem: SeriesScoresModel,
            newItem: SeriesScoresModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SeriesScoresModel,
            newItem: SeriesScoresModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveSliderAdapterViewHolder {
        val binding :ItemSeriesLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),R.layout.item_series_layout,parent,false
        )
        return LiveSliderAdapterViewHolder(binding,context)
    }

    override fun onBindViewHolder(holder: LiveSliderAdapterViewHolder, position: Int) {
//        holder.setIsRecyclable(false)
        holder.bindSeriesData(currentList[position])
        holder.itemView.setSafeOnClickListener {

            if (currentList[position].series_id!=null) {
                selectedSeriesId = currentList[position].series_id!!
            }

            val direction=BrowseFragmentDirections.actionSeriesFragmentToSeriesMatchFragment()
            navigateData.navigation(direction)
        }

    }




}