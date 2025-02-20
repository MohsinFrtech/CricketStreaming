package com.traumsportzone.live.cricket.tv.utils.interfaces

import com.dream.live.cricket.score.hd.streaming.models.FormatDataAudio


////This interface is for controlling navigation between fragments
interface FormatSelectionAudio {
    fun navigation(viewId: FormatDataAudio, pos:Int, countryCode:String?)
}