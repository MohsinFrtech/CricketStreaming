package com.dream.live.cricket.score.hd.streaming.utils.objects

import android.content.Context
import android.content.SharedPreferences
import com.dream.live.cricket.score.hd.R

class SharedPreference(context: Context?) {

    private var mPref: SharedPreferences? = null
    private var mEditor: SharedPreferences.Editor? = null

    init {
        mPref = context?.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        mEditor = mPref?.edit()
    }

    fun saveString(key: String, value: String) {
        mEditor?.putString(key, value)
        mEditor?.commit()
    }

    fun getString(key: String):String? {
        return mPref?.getString(key, "dark")
    }

    fun saveBool(key: String, value: Boolean) {
        mEditor?.putBoolean(key, value)
        mEditor?.commit()
    }

    fun saveRateUsBool(key: String, value: Boolean) {
        mEditor?.putBoolean(key, value)
        mEditor?.commit()
    }

    fun getRateUsBool(key: String):Boolean? {
        return mPref?.getBoolean(key, false)
    }
    fun getBool(key: String):Boolean? {
       return mPref?.getBoolean(key, false)
    }

    fun saveUpcomingData(key: String, value: String){
        mEditor?.putString(key, value)
        mEditor?.commit()
    }
    fun getUpcomingMatchesData(key: String):String? {
        return mPref?.getString(key, "nothing")
    }

}