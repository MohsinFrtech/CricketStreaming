package com.dream.live.cricket.score.hd.streaming.utils.objects

import com.dream.live.cricket.score.hd.scores.model.LiveScoresModel
import java.util.ArrayList

object MyNativeAd {

    ////Function to return list with empty positions.....
    fun checkNativeAd(list: List<LiveScoresModel?>): List<LiveScoresModel?> {
        val tempChannels: MutableList<LiveScoresModel?> =
            ArrayList()
        for (i in list.indices) {
            val diff = i % 5
            if (diff == 2) {

                tempChannels.add(null)
            }
            tempChannels.add(list[i])
            if (list.size == 2) {
                if (i == 1) {
                    tempChannels.add(null)

                }
            }

        }
        return tempChannels
    }

}