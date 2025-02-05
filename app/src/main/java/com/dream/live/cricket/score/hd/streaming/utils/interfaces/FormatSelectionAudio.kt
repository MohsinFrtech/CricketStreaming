package com.traumsportzone.live.cricket.tv.utils.interfaces

import com.traumsportzone.live.cricket.tv.models.FormatDataAudio


////This interface is for controlling navigation between fragments
interface FormatSelectionAudio {
    fun navigation(viewId: FormatDataAudio, pos:Int, countryCode:String?)
}