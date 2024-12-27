package com.dream.live.cricket.score.hd.scores.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dream.live.cricket.score.hd.databinding.NewsApiLayoutBinding
import com.dream.live.cricket.score.hd.scores.model.StoryList
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.scores.utility.listeners.NavigateData


class NewsAdapter(private val navigateData: NavigateData) :
    ListAdapter<StoryList, NewsAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: NewsApiLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(storyList: StoryList) {
            if (storyList.story != null) {
                binding?.newsHeadline?.text = storyList?.story?.hline.toString()
                binding?.subtitle?.text = storyList?.story?.intro?.toString()

            }
            if (storyList.story?.pubTime!=null){
                val convertDate = storyList.story!!.pubTime?.toLong()
                    ?.let { Cons.convertDateAndTime(it) }
                binding?.time?.text = Cons.dateAndTime(convertDate)
            }

        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<StoryList>() {
        override fun areItemsTheSame(oldItem: StoryList, newItem: StoryList): Boolean {
            return oldItem.story?.id == newItem.story?.id
        }

        override fun areContentsTheSame(oldItem: StoryList, newItem: StoryList): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(NewsApiLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val league = currentList[position]
        holder.bind(league)

        holder.itemView.setOnClickListener {
            Cons.newsId = currentList[position].story?.id.toString()
           // val direction =  LiveScoreDetailDirections.actionLiveDetailsToNewsDetailFragment()
         //   navigateData.navigation(direction)
        }

    }


}