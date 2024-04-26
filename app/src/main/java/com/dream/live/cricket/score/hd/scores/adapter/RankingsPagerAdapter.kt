package com.dream.live.cricket.score.hd.scores.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dream.live.cricket.score.hd.scores.ui.fragments.ranking.AllRounderRankingFragment
import com.dream.live.cricket.score.hd.scores.ui.fragments.ranking.BatsManRankingFragment
import com.dream.live.cricket.score.hd.scores.ui.fragments.ranking.BowlerRankingFragment
import com.dream.live.cricket.score.hd.scores.ui.fragments.ranking.TeamsRankingFragment

class RankingsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {


    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TeamsRankingFragment()
            1 -> BatsManRankingFragment()
            2 -> BowlerRankingFragment()
            3 -> AllRounderRankingFragment()
            else -> {
                AllRounderRankingFragment()
            }
        }
    }

}