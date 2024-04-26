package com.dream.live.cricket.score.hd.streaming.utils
import android.util.Log

class Logger {

   private val isLoggerEnabled= true


    fun printLog(tag:String,message: String)
    {
        if (isLoggerEnabled)
        {
            Log.d(tag,message)
        }


    }

}