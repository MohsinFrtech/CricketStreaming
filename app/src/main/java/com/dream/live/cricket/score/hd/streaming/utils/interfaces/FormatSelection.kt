package com.dream.live.cricket.score.hd.streaming.utils.interfaces

import com.dream.live.cricket.score.hd.streaming.models.FormatData


////This interface is for controlling navigation between fragments
interface FormatSelection {
    fun navigation(viewId: FormatData, pos:Int)
}