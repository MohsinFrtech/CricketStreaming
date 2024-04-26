package com.dream.live.cricket.score.hd.scores.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SeriesScoresModel(
    var id: Int?,
    var series_id: Int?,
    var series_name: String?,
    var start_date: Long?,
    var end_date: Long?,
    var state: String?,
    var is_time_announced: Boolean?,
    var updated_at: String?,

    ) : Parcelable

