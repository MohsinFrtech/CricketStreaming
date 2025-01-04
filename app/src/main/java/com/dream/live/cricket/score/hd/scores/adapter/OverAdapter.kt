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
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.OverItemLayoutBinding
import com.dream.live.cricket.score.hd.databinding.OverTotalBinding
import com.dream.live.cricket.score.hd.scores.model.OverItemModel
import com.dream.live.cricket.score.hd.streaming.adapters.ChannelAdapter.ChannelAdapterViewHolder
import com.dream.live.cricket.score.hd.streaming.adapters.ChannelAdapter.NativeChannelViewHolder
import com.dream.live.cricket.score.hd.streaming.models.Channel


class OverAdapter(
    val context: Context,
    val list: List<OverItemModel?>
) :
    ListAdapter<OverItemModel, RecyclerView.ViewHolder>(DiffCallback) {

    private val overBallLayout = 1
    private val overNumberLayout = 0

    class ViewHolder(private var binding: OverItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val fontsize = 20f

        fun bind(squadModel: OverItemModel) {
            binding?.textva?.text = squadModel.name.toString()


            if (squadModel?.name?.trim()?.equals("W") == true) {
                binding?.backLay?.setBackgroundColor(Color.parseColor("#801424"))
            } else if (squadModel?.name?.trim()?.equals("4") == true) {
                binding?.backLay?.setBackgroundColor(Color.parseColor("#0000FF"))
            } else if (squadModel?.name?.trim()?.equals("6") == true) {
                binding?.backLay?.setBackgroundColor(Color.parseColor("#008000"))
            } else if (squadModel?.name?.trim()?.contains("|") == true) {
                binding?.textva?.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontsize)
                binding?.backLay?.setBackgroundColor(Color.parseColor("#00FFFFFF"))
            } else {
                binding?.backLay?.setBackgroundColor(Color.parseColor("#191721"))
            }

            binding.executePendingBindings()
        }

    }

    class OverNumberViewHolder(private var binding: OverTotalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val fontsize = 20f

        fun bind(squadModel: OverItemModel) {

            val afterVal = removeLetter(squadModel.name.toString(),'|')
            binding?.overNumber?.text = afterVal+" "
            binding.executePendingBindings()
        }

        fun removeLetter(text: String, letterToRemove: Char): String {
            return text.filter { it != letterToRemove }
        }
    }


    override fun getItemViewType(position: Int): Int {
        if (list[position]?.name?.contains("|") == true) {
            return overNumberLayout
        }
        return overBallLayout
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OverItemModel>() {
        override fun areItemsTheSame(oldItem: OverItemModel, newItem: OverItemModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: OverItemModel, newItem: OverItemModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        return ViewHolder(OverItemLayoutBinding.inflate(layoutInflater, parent, false))

        return when (viewType) {
            overNumberLayout -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                OverNumberViewHolder(OverTotalBinding.inflate(layoutInflater, parent, false))
            }

            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                ViewHolder(OverItemLayoutBinding.inflate(layoutInflater, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            overBallLayout -> {
                val viewHolder: ViewHolder= holder as ViewHolder

                val league = currentList[position]
                viewHolder.bind(league)
            }
            else->{
                val viewHolder: OverNumberViewHolder= holder as OverNumberViewHolder
                val league = currentList[position]
                viewHolder.bind(league)
            }

        }
    }




}