package com.dream.live.cricket.score.hd.scores.utility.listeners

interface ApiResponseListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}