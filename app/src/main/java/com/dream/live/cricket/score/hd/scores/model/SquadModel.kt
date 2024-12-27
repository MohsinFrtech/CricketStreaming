package com.dream.live.cricket.score.hd.scores.model

import com.google.gson.annotations.SerializedName

data class SquadModel(
    @SerializedName("matchInfo") var matchInfo: MatchInfo? = MatchInfo(),
    @SerializedName("venueInfo") var venueInfo: VenueInfoDetail? = VenueInfoDetail(),
    @SerializedName("broadcastInfo") var broadcastInfo: ArrayList<String> = arrayListOf()
)
data class VenueInfoDetail (

    @SerializedName("established"  ) var established  : Int?     = null,
    @SerializedName("capacity"     ) var capacity     : String?  = null,
    @SerializedName("knownAs"      ) var knownAs      : String?  = null,
    @SerializedName("ends"         ) var ends         : String?  = null,
    @SerializedName("city"         ) var city         : String?  = null,
    @SerializedName("country"      ) var country      : String?  = null,
    @SerializedName("timezone"     ) var timezone     : String?  = null,
    @SerializedName("homeTeam"     ) var homeTeam     : String?  = null,
    @SerializedName("floodlights"  ) var floodlights  : Boolean? = null,
    @SerializedName("curator"      ) var curator      : String?  = null,
    @SerializedName("profile"      ) var profile      : String?  = null,
    @SerializedName("imageUrl"     ) var imageUrl     : String?  = null,
    @SerializedName("ground"       ) var ground       : String?  = null,
    @SerializedName("groundLength" ) var groundLength : Int?     = null,
    @SerializedName("groundWidth"  ) var groundWidth  : Int?     = null,
    @SerializedName("otherSports"  ) var otherSports  : String?  = null

)
data class MatchInfo(

    @SerializedName("matchId") var matchId: Int? = null,
    @SerializedName("matchDescription") var matchDescription: String? = null,
    @SerializedName("matchFormat") var matchFormat: String? = null,
    @SerializedName("matchType") var matchType: String? = null,
    @SerializedName("complete") var complete: Boolean? = null,
    @SerializedName("domestic") var domestic: Boolean? = null,
    @SerializedName("matchStartTimestamp") var matchStartTimestamp: Long? = null,
    @SerializedName("matchCompleteTimestamp") var matchCompleteTimestamp: Long? = null,
    @SerializedName("dayNight") var dayNight: Boolean? = null,
    @SerializedName("year") var year: Int? = null,
    @SerializedName("dayNumber") var dayNumber: Int? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("team1") var team1: DetailTeam1? = DetailTeam1(),
    @SerializedName("team2") var team2: DetailTeam2? = DetailTeam2(),
    @SerializedName("series") var series: Series? = Series(),
    @SerializedName("umpire1") var umpire1: Umpire1? = Umpire1(),
    @SerializedName("umpire2") var umpire2: Umpire2? = Umpire2(),
    @SerializedName("umpire3") var umpire3: Umpire3? = Umpire3(),
    @SerializedName("referee") var referee: Referee? = Referee(),
    @SerializedName("tossResults") var tossResults: TossResults? = TossResults(),
    @SerializedName("result") var result: Result? = Result(),
    @SerializedName("venue") var venue: Venue? = Venue(),
    @SerializedName("status") var status: String? = null,
    @SerializedName("playersOfTheMatch") var playersOfTheMatch: ArrayList<PlayersOfTheMatch> = arrayListOf(),
    @SerializedName("playersOfTheSeries") var playersOfTheSeries: ArrayList<PlayerOfTheSeries> = arrayListOf(),
    @SerializedName("revisedTarget") var revisedTarget: RevisedTarget? = RevisedTarget(),
    @SerializedName("matchTeamInfo") var matchTeamInfo: ArrayList<MatchTeamInfo> = arrayListOf(),
    @SerializedName("isMatchNotCovered") var isMatchNotCovered: Boolean? = null,
    @SerializedName("HYSEnabled") var HYSEnabled: Int? = null,
    @SerializedName("livestreamEnabled") var livestreamEnabled: Boolean? = null,
    @SerializedName("isFantasyEnabled") var isFantasyEnabled: Boolean? = null,
    @SerializedName("livestreamEnabledGeo") var livestreamEnabledGeo: ArrayList<String> = arrayListOf(),
    @SerializedName("alertType") var alertType: String? = null,
    @SerializedName("shortStatus") var shortStatus: String? = null,
    @SerializedName("matchImageId") var matchImageId: Int? = null

)

data class PlayersOfTheMatch (

    @SerializedName("id"           ) var id           : Int?     = null,
    @SerializedName("name"         ) var name         : String?  = null,
    @SerializedName("fullName"     ) var fullName     : String?  = null,
    @SerializedName("nickName"     ) var nickName     : String?  = null,
    @SerializedName("captain"      ) var captain      : Boolean? = null,
    @SerializedName("role"         ) var role         : String?  = null,
    @SerializedName("keeper"       ) var keeper       : Boolean? = null,
    @SerializedName("substitute"   ) var substitute   : Boolean? = null,
    @SerializedName("teamId"       ) var teamId       : Int?     = null,
    @SerializedName("battingStyle" ) var battingStyle : String?  = null,
    @SerializedName("bowlingStyle" ) var bowlingStyle : String?  = null,
    @SerializedName("teamName"     ) var teamName     : String?  = null,
    @SerializedName("faceImageId"  ) var faceImageId  : Long?     = null

)

data class Referee(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("country") var country: String? = null

)


data class Venue(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("timezone") var timezone: String? = null,
    @SerializedName("latitude") var latitude: String? = null,
    @SerializedName("longitude") var longitude: String? = null

)

data class DetailTeam1(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("playerDetails") var playerDetails: ArrayList<PlayerDetails> = arrayListOf(),
    @SerializedName("shortName") var shortName: String? = null

)

data class DetailTeam2 (

    @SerializedName("id"            ) var id            : Int?                     = null,
    @SerializedName("name"          ) var name          : String?                  = null,
    @SerializedName("playerDetails" ) var playerDetails : ArrayList<PlayerDetails> = arrayListOf(),
    @SerializedName("shortName"     ) var shortName     : String?                  = null

)
data class PlayerDetails (

    @SerializedName("id"           ) var id           : Int?     = null,
    @SerializedName("name"         ) var name         : String?  = null,
    @SerializedName("fullName"     ) var fullName     : String?  = null,
    @SerializedName("nickName"     ) var nickName     : String?  = null,
    @SerializedName("captain"      ) var captain      : Boolean? = null,
    @SerializedName("role"         ) var role         : String?  = null,
    @SerializedName("keeper"       ) var keeper       : Boolean? = null,
    @SerializedName("substitute"   ) var substitute   : Boolean? = null,
    @SerializedName("teamId"       ) var teamId       : Int?     = null,
    @SerializedName("battingStyle" ) var battingStyle : String?  = null,
    @SerializedName("bowlingStyle" ) var bowlingStyle : String?  = null,
    @SerializedName("teamName"     ) var teamName     : String?  = null,
    @SerializedName("faceImageId"  ) var faceImageId  : Long?     = null

)
data class Series(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("seriesType") var seriesType: String? = null,
    @SerializedName("startDate") var startDate: Long? = null,
    @SerializedName("endDate") var endDate: Long? = null,
    @SerializedName("seriesFolder") var seriesFolder: String? = null,
    @SerializedName("odiSeriesResult") var odiSeriesResult: String? = null,
    @SerializedName("t20SeriesResult") var t20SeriesResult: String? = null,
    @SerializedName("testSeriesResult") var testSeriesResult: String? = null,
    @SerializedName("tournament") var tournament: Boolean? = null

)


data class Umpire1(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("country") var country: String? = null

)

data class Umpire2(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("country") var country: String? = null

)

data class Umpire3(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("country") var country: String? = null

)