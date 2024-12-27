package com.dream.live.cricket.score.hd.scores.ui.fragments.matchdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chartboost.sdk.impl.s
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.NewsLayoutBinding
import com.dream.live.cricket.score.hd.scores.adapter.NewsAdapter
import com.dream.live.cricket.score.hd.scores.model.StoryList
import com.dream.live.cricket.score.hd.scores.utility.listeners.NavigateData
import com.dream.live.cricket.score.hd.scores.viewmodel.CommentaryViewModel

class NewsFragment : Fragment() , NavigateData {
    var binding: NewsLayoutBinding? = null
    private val newsViewModel by lazy {
        ViewModelProvider(requireParentFragment())[CommentaryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val lay = inflater.inflate(R.layout.news_layout, container, false)
        binding = DataBindingUtil.bind(lay)
        setUpNewsRecycler()
        return lay
    }

    //News Recycler setup....
    private fun setUpNewsRecycler() {
        newsViewModel?.storiesList?.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                 if (!it.storyList.isNullOrEmpty()){
                     showNews()
                     setUpNewsData(it.storyList)
                 }
                 else
                 {
                    hideNews()
                 }
            } 
        })
    }

    private fun setUpNewsData(storyList: ArrayList<StoryList>) {
        val newList = storyList.filter {
            it.story!=null && it.story!!.hline!=null
        }
        val listAdapter =
            NewsAdapter(this)
        binding?.newsRecycler?.layoutManager = LinearLayoutManager(requireContext())
        binding?.newsRecycler?.adapter = listAdapter
        listAdapter.submitList(newList)
    }

    private fun hideNews() {
        binding?.noNews?.visibility = View.VISIBLE
        binding?.newsRecycler?.visibility = View.GONE
    }
    private fun showNews() {
        binding?.noNews?.visibility = View.GONE
        binding?.newsRecycler?.visibility = View.VISIBLE
    }

    override fun navigation(viewId: NavDirections) {
        findNavController().navigate(viewId)
    }
}