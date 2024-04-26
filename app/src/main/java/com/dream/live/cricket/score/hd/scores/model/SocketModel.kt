package com.dream.live.cricket.score.hd.scores.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SocketModel(

    @Json(name = "identifier") var message2: Identifier,
    @Json(name = "message") var message: List<LiveScoresModel>?,

    ) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class Identifier(

    var channel: String?,
    ) : Parcelable
