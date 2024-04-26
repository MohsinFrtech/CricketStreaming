package com.dream.live.cricket.score.hd.scores.model

data class PlayersRankingModel(
    val id: Int,
    val player_id: Int? = null,
    val name: String? = null,
    val rank: Int? = null,
    val format: String? = null,
    val category: String? = null,
    val rating: Int? = null,
    val matches: String? = null,
    val avg: Double? = null,
    val points: Int? = null,
    val last_updated_on: String? = null,
    val image_id: Int? = null,
    val country: String? = null,
    val country_id: Int? = null,
    val created_at: String? = null,
    val updated_at: String? = null
)