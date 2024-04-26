package com.dream.live.cricket.score.hd.scores.utility

import com.google.android.gms.ads.nativead.NativeAd
import java.text.SimpleDateFormat
import java.util.*

object Cons {
    var s_token = ""
    ///Socket
    var socketUrl = ""
    var socketAuth = ""
    const val match_format_t20 = "t20"
    const val match_format_test = "test"
    const val match_format_odi = "Odi"


    const val match_category_live = "Preview"
    const val match_category_recent = "Complete"
    const val match_category_upcoming = "Upcoming"


    const val player_bowler = "bowlers"
    const val player_batsmen = "batsmen"
    const val player_allRounders = "allrounders"

    var base_url_scores=""

    var currentNativeAd:NativeAd?=null
    var currentNativeAdFacebook:com.facebook.ads.NativeAd?=null

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        return format.format(date)
    }
    fun convertDateAndTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH)
        return format.format(date)
    }

}