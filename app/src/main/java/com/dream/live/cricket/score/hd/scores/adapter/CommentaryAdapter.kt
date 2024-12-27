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
import com.dream.live.cricket.score.hd.databinding.CommentryLayoutBinding
import com.dream.live.cricket.score.hd.scores.model.CommentaryList


class CommentaryAdapter() :
    ListAdapter<CommentaryList, CommentaryAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: CommentryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(commentary: CommentaryList) {

            if (!commentary.commText.isNullOrEmpty()) {
                if (commentary.overNumber != null) {
                    if (commentary.overNumber != 0.0) {
                        binding?.overNumber?.text = commentary.overNumber.toString()
                        binding?.overNumber?.visibility = View.VISIBLE
                    } else {
                        binding?.overNumber?.visibility = View.GONE
                    }
                } else {
                    binding?.overNumber?.visibility = View.GONE
                }
                if (commentary.commentaryFormats != null) {
                    if (!commentary.commentaryFormats?.bold?.formatId.isNullOrEmpty()) {
                        var s = SpannableString("")

                        var changedCommonText = commentary.commText
                        var count = 0
                        commentary.commentaryFormats?.bold?.formatId?.forEach { formatIdNext ->

                            if (changedCommonText?.contains(
                                    formatIdNext.toString()
                                ) == true
                            ) {
                                var replaceString = ""

                                var len =
                                    commentary.commentaryFormats!!.bold?.formatValue?.get(count)?.length
                                val indexOf = changedCommonText?.indexOf(formatIdNext)
                                val length = len?.let { indexOf?.plus(it) }
                                replaceString = changedCommonText!!.replace(
                                    formatIdNext
                                        .toString(),
                                    commentary.commentaryFormats!!.bold?.formatValue?.get(count)
                                        .toString(), true
                                )

                                setBoldValue(replaceString)
                                var ss = SpannableString(replaceString)
                                val boldSpan = StyleSpan(Typeface.BOLD)
                                if (indexOf != null) {
                                    if (length != null) {
                                        ss.setSpan(
                                            boldSpan,
                                            indexOf,
                                            length,
                                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                        )
                                    }
                                }
                                changedCommonText = ss.toString()
                                s = ss
                                count++

                            } else {
//                                s.append(commentary.commText.toString())
//                           replaceString = commentary.commText.toString()
//                           binding?.squads?.text = replaceString
                            }
                        }
//                        s.bold {
//                            append(
//                                commentary.commentaryFormats!!.bold?.formatValue?.get(
//                                    0
//                                ).toString()
//                            )
//                        }.append(changedCommonText)


                        binding?.squads?.text = s

                    } else if (!commentary.commentaryFormats?.italic?.formatId.isNullOrEmpty()) {
                        var s = SpannableString("")

                        var changedCommonText = commentary.commText
                        var count = 0
                        commentary.commentaryFormats?.italic?.formatId?.forEach { formatIdNext ->

                            if (changedCommonText?.contains(
                                    formatIdNext.toString()
                                ) == true
                            ) {
                                var replaceString = ""

                                var len =
                                    commentary.commentaryFormats!!.italic?.formatValue?.get(count)?.length
                                val indexOf = changedCommonText?.indexOf(formatIdNext)
                                val length = len?.let { indexOf?.plus(it) }
                                replaceString = changedCommonText!!.replace(
                                    formatIdNext
                                        .toString(),
                                    commentary.commentaryFormats!!.italic?.formatValue?.get(count)
                                        .toString(), true
                                )

                                setBoldValue(replaceString)
                                var ss = SpannableString(replaceString)
                                val boldSpan = StyleSpan(Typeface.BOLD)
                                if (indexOf != null) {
                                    if (length != null) {
                                        ss.setSpan(
                                            boldSpan,
                                            indexOf,
                                            length,
                                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                        )
                                    }
                                }
                                changedCommonText = ss.toString()
                                s = ss
                                count++

                            } else {
//                                s.append(commentary.commText.toString())
//                           replaceString = commentary.commText.toString()
//                           binding?.squads?.text = replaceString
                            }
                        }
//                        s.bold {
//                            append(
//                                commentary.commentaryFormats!!.bold?.formatValue?.get(
//                                    0
//                                ).toString()
//                            )
//                        }.append(changedCommonText)


                        binding?.squads?.text = s
                    } else {
                        val replaceString2 = commentary.commText.toString()
                        binding?.squads?.text = replaceString2

                    }

                    // if commentary for not null
                    //Replace commentary format in commentary text..
                }

            }

            if (commentary.overSeparator != null) {
                if (commentary.overSeparator!!.runs != null) {
                    if (commentary.overSeparator!!.runs!! >= 0.0) {
                        binding?.mainBatsmanHead?.visibility=View.VISIBLE
                        binding?.fulloverNumber?.text =
                            commentary.overSeparator?.overNum?.toString()
                        binding?.tvSeries?.text = commentary.overSeparator!!.oSummary?.toString()

                        binding?.tvMatch?.text = commentary.overSeparator!!.batStrikerNames?.get(0).toString()+"  "+
                                commentary.overSeparator!!.batStrikerRuns.toString()+"("+commentary.overSeparator!!.batStrikerBalls+")"

                        binding?.tvDateTime?.text = commentary.overSeparator!!.batNonStrikerNames?.get(0).toString()+"  "+
                                commentary.overSeparator!!.batNonStrikerRuns.toString()+"("+commentary.overSeparator!!.batNonStrikerBalls+")"

                        binding?.tvVenue?.text = commentary.overSeparator!!.bowlNames?.get(0).toString()+"  "+
                                commentary.overSeparator!!.bowlOvers.toString()+"-"+commentary.overSeparator!!.bowlWickets+"-"+
                                commentary.overSeparator!!.bowlRuns.toString()+"-"+commentary.overSeparator!!.bowlMaidens.toString()

                    }
                }
                else
                {
                    binding?.mainBatsmanHead?.visibility=View.GONE
                }
            }
            else{
                binding?.mainBatsmanHead?.visibility=View.GONE
            }

            binding.executePendingBindings()
        }

        private fun setBoldValue(replaceString: String) {

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CommentaryList>() {
        override fun areItemsTheSame(oldItem: CommentaryList, newItem: CommentaryList): Boolean {
            return oldItem.inningsId == newItem.inningsId
        }

        override fun areContentsTheSame(oldItem: CommentaryList, newItem: CommentaryList): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(CommentryLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.setIsRecyclable(false)
        val league = currentList[position]
        holder.bind(league)


    }


}