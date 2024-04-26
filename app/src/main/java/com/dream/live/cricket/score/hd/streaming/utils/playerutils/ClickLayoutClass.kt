package com.dream.live.cricket.score.hd.streaming.utils.playerutils

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout


class ClickLayoutClass : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)



    override fun performClick(): Boolean {
        super.performClick()
        // do what you want
        return true
    }


}