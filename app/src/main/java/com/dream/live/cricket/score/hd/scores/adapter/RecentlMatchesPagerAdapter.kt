package com.dream.live.cricket.score.hd.scores.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dream.live.cricket.score.hd.scores.ui.fragments.RecentMatchesFragment

class RecentlMatchesPagerAdapter (fm: Fragment, status:String): FragmentStateAdapter(fm) {
    var status: String? = null;

    init {
        this.status = status
    }


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> RecentMatchesFragment.newInstance("T20", )
        1 -> RecentMatchesFragment.newInstance("0DI")
        else -> RecentMatchesFragment.newInstance("TEST")
    }
}