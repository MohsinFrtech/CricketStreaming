package com.dream.live.cricket.score.hd.scores.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.FragmentBrowseBinding
import com.dream.live.cricket.score.hd.utils.Logger

class BrowseFragmentMain : Fragment() {

    var binding: FragmentBrowseBinding? = null
    private var navDirections: NavDirections? = null
    private val logger = Logger()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_browse, container, false)
        binding = DataBindingUtil.bind(view)




        return view
    }
}