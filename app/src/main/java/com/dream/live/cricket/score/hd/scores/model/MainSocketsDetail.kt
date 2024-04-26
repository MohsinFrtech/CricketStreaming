package com.dream.live.cricket.score.hd.scores.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainSocketsDetail(
    var response_detail: ResponseDetail?) {

    @JsonClass(generateAdapter = true)
    data class ResponseDetail(
        var live_matches_detail: List<LiveMatchesDetail>?
    ) {
        @JsonClass(generateAdapter = true)
        data class LiveMatchesDetail(
            var commentaries: String?,
            var commentary: Int?,
            var game_state: Int?,
            var game_state_str: String?,
            var live_batsmen: List<LiveBatsmen>?,
            var live_bowlers: List<LiveBowlers>?,
            var live_inning: LiveInning?,
            var live_inning_number: Int?,
            var live_score: LiveScore?,
            var mid: Int?,
            var status: Int?,
            var status_note: String?,
            var status_str: String?,
            var team_batting: String?,
            var team_bowling: String?,
            var wagon: Int?
        ) {
            @JsonClass(generateAdapter = true)
            data class LiveBatsmen(
                var balls_faced: String?,
                var batsman_id: String?,
                var fours: String?,
                var name: String?,
                var runs: String?,
                var sixes: String?,
                var strike_rate: String?
            )

            @JsonClass(generateAdapter = true)
            data class LiveBowlers(
                var bowler_id: String?,
                var econ: String?,
                var maidens: String?,
                var name: String?,
                var overs: String?,
                var runs_conceded: String?,
                var wickets: String?
            )

            @JsonClass(generateAdapter = true)
            data class LiveInning(
                var batting_team_id: Int?,
                var current_partnership: CurrentPartnership?,
                var fielding_team_id: Int?,
                var iid: Int?,
                var last_five_overs: String?,
                var last_ten_overs: String?,
                var last_wicket: LastWicket?,
                var name: String?,
                var number: Int?,
                var recent_scores: String?,
                var result: String?,
                var scores: String?,
                var scores_full: String?,
                var short_name: String?,
                var status: Int?
            ) {
                @JsonClass(generateAdapter = true)
                data class CurrentPartnership(
                    var balls: String?,
                    var overs: String?,
                    var runs: String?
                )


                @JsonClass(generateAdapter = true)
                data class LastWicket(
                    var balls: String?,
                    var batsman_id: String?,
                    var bowler_id: String?,
                    var dismissal: String?,
                    var how_out: String?,
                    var name: String?,
                    var number: Int?,
                    var overs_at_dismissal: String?,
                    var runs: String?,
                    var score_at_dismissal: Int?
                )
            }

            @JsonClass(generateAdapter = true)
            data class  LiveScore(
                var overs: String?,
                var required_runrate: String?,
                var runrate: String?,
                var runs: String?,
                var target: String?,
                var wickets: String?
            )
        }
    }
}