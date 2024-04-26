package com.dream.live.cricket.score.hd.scores.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class LiveScoresModel(
    var id: Int,
    var match_id: Int,
    var match_description: String,
    var match_format: String,
    var start_time: Long?,
    var end_time: Long?,
    var state: String? = "",
    var status: String? = "",
    var match_type: String? = "",
    var team_1: Team1?,
    var team_2: Team2?,
    var venue_info: VenueInfo?,
    var score_card: ScoreCard?,
    var series_name: String?,
    var series_id: Int?,
    var team_1_id: Int?,
    var team_2_id: Int?,
    var venue_id: Int?,
    var created_at: String?,
    var updated_at: String?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Team1(
    var teamId: Int,
    var teamName: String,
    var teamSName: String,
    var imageId: Int
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Team2(
    var teamId: Int,
    var teamName: String,
    var teamSName: String,
    var imageId: Int
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class VenueInfo(
    var id: Int?,
    var ground: String?,
    var city: String?,
    var timezone: String?,
    var latitude: Double?,
    var longitude: Double?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class ScoreCard(
    val team1Score: Team1Score?,
    val team2Score: Team2Score?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Team1Score(
    val inngs1: Innings1?,
    val inngs2: Innings2?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Team2Score(
    val inngs1: Innings1?,
    val inngs2: Innings2?
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class Innings1(
    val inningsId: Int?,
    val runs: Int?,
    val wickets: Int?,
    val overs: Double?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Innings2(
    val inningsId: Int?,
    val runs: Int?,
    val wickets: Int?=0,
    val overs: Double?
) : Parcelable

