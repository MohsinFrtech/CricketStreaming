package com.dream.live.cricket.score.hd.streaming.utils.interfaces

import com.dream.live.cricket.score.hd.scores.model.LiveScoresModel

interface ObserveLiveScore {
    fun scoresData(liveModel: List<LiveScoresModel>)
}