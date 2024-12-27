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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dream.live.cricket.score.hd.databinding.OverItemLayoutBinding


class OverAdapter(val context: Context) :
    ListAdapter<String, OverAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: OverItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            private val fontsize = 20f

        fun bind(squadModel: String) {
            binding?.textva?.text = squadModel.toString()

            if (squadModel?.trim()?.equals("W") == true) {
                binding?.backLay?.setBackgroundColor(Color.parseColor("#801424"))
            } else if (squadModel?.trim()?.equals("4") == true) {
                binding?.backLay?.setBackgroundColor(Color.parseColor("#0000FF"))
            } else if (squadModel?.trim()?.equals("6") == true) {
                binding?.backLay?.setBackgroundColor(Color.parseColor("#008000"))
            }else if (squadModel?.trim()?.equals("|") == true) {
                binding?.textva?.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontsize)
                binding?.backLay?.setBackgroundColor(Color.parseColor("#00FFFFFF"))
            }
            else
            {
                binding?.backLay?.setBackgroundColor(Color.parseColor("#191721"))
            }

            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(OverItemLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val league = currentList[position]
        holder.bind(league)

    }



}