package com.dream.live.cricket.score.hd.scores.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AllTeamsModel(
    var id: Int?,
    var team_id: Int?,
    var team_name: String?,
    var team_short_name: String?,
    var image_id: Int?,
    var created_at: String?,
    var updated_at: String?,

    ) : Parcelable

