package com.dream.live.cricket.score.hd.scores.model

import com.google.gson.annotations.SerializedName

data class CommentryModelClass(
    @SerializedName("commentaryList")
    var commentaryList: ArrayList<CommentaryList> = arrayListOf(),
    @SerializedName("matchHeader")
    var matchHeader: MatchHeader? = MatchHeader(),
    @SerializedName("miniscore")
    var miniscore: Miniscore? = Miniscore(),
    @SerializedName("commentarySnippetList")
    var commentarySnippetList: ArrayList<CommentarySnippet> = arrayListOf(),
    @SerializedName("page")
    var page: String? = null,
    @SerializedName("enableNoContent")
    var enableNoContent: Boolean? = null,
    @SerializedName("matchVideos")
    var matchVideos: ArrayList<MatchVideos> = arrayListOf(),
    @SerializedName("responseLastUpdated")
    var responseLastUpdated: Long? = null

)
data class MatchVideos(
    @SerializedName("infraType"     ) var infraType     : String?             = null,
    @SerializedName("headline"      ) var headline      : String?             = null,
    @SerializedName("commTimestamp" ) var commTimestamp : String?             = null,
    @SerializedName("itemId"        ) var itemId        : String?             = null,
    @SerializedName("appLinkUrl"    ) var appLinkUrl    : String?             = null,
    @SerializedName("imageId"       ) var imageId       : Int?                = null,
    @SerializedName("mappingId"     ) var mappingId     : String?             = null,
    @SerializedName("videoUrl"      ) var videoUrl      : String?             = null,
    @SerializedName("adTag"         ) var adTag         : String?             = null,
    @SerializedName("language"      ) var language      : String?             = null,
    @SerializedName("videoId"       ) var videoId       : Long?                = null,
    @SerializedName("videoType"     ) var videoType     : String?             = null,
    @SerializedName("category"      ) var category      : ArrayList<Category> = arrayListOf(),
    @SerializedName("tags"          ) var tags          : ArrayList<Tags>     = arrayListOf()
)

data class Tags (

    @SerializedName("itemName" ) var itemName : String? = null,
    @SerializedName("itemType" ) var itemType : String? = null,
    @SerializedName("itemId"   ) var itemId   : String? = null

)

data class Category (

    @SerializedName("id"      ) var id      : Int?    = null,
    @SerializedName("name"    ) var name    : String? = null,
    @SerializedName("imageID" ) var imageID : Int?    = null

)
data class CommentarySnippet(
    @SerializedName("commId"        ) var commId        : Int?                  = null,
    @SerializedName("matchId"       ) var matchId       : Int?                  = null,
    @SerializedName("inningsId"     ) var inningsId     : Int?                  = null,
    @SerializedName("infraType"     ) var infraType     : String?               = null,
    @SerializedName("headline"      ) var headline      : String?               = null,
    @SerializedName("imageId"       ) var imageId       : Int?                  = null,
    @SerializedName("itemId"        ) var itemId        : String?               = null,
    @SerializedName("timestamp"     ) var timestamp     : Long?                  = null,
    @SerializedName("parsedContent" ) var parsedContent : ArrayList<String>     = arrayListOf(),
    @SerializedName("videoGeo"      ) var videoGeo      : ArrayList<String>     = arrayListOf(),
    @SerializedName("videoType"     ) var videoType     : String?               = null,
    @SerializedName("url"           ) var url           : String?               = null,
    @SerializedName("isLive"        ) var isLive        : Boolean?              = null,
    @SerializedName("videoId"       ) var videoId       : Long?                  = null,
    @SerializedName("mappingId"     ) var mappingId     : String?               = null,
    @SerializedName("videoUrl"      ) var videoUrl      : String?               = null,
    @SerializedName("adTag"         ) var adTag         : String?               = null,
    @SerializedName("categories"    ) var categories    : ArrayList<Categories> = arrayListOf(),
    @SerializedName("language"      ) var language      : String?               = null,
    @SerializedName("planId"        ) var planId        : Int?                  = null,
    @SerializedName("tags"          ) var tags          : ArrayList<String>     = arrayListOf(),
    @SerializedName("isPremiumFree" ) var isPremiumFree : Boolean?              = null

)

data class Categories (

    @SerializedName("ID"      ) var ID      : Int?    = null,
    @SerializedName("name"    ) var name    : String? = null,
    @SerializedName("imageID" ) var imageID : Int?    = null

)
data class OverSeperator( @SerializedName("score"              ) var score              : Double?              = null,
                          @SerializedName("wickets"            ) var wickets            : Double?              = null,
                          @SerializedName("inningsId"          ) var inningsId          : Int?              = null,
                          @SerializedName("o_summary"          ) var oSummary           : String?           = null,
                          @SerializedName("runs"               ) var runs               : Double?              = null,
                          @SerializedName("batStrikerIds"      ) var batStrikerIds      : ArrayList<Int>    = arrayListOf(),
                          @SerializedName("batStrikerNames"    ) var batStrikerNames    : ArrayList<String> = arrayListOf(),
                          @SerializedName("batStrikerRuns"     ) var batStrikerRuns     : Double?              = null,
                          @SerializedName("batStrikerBalls"    ) var batStrikerBalls    : Double?              = null,
                          @SerializedName("batNonStrikerIds"   ) var batNonStrikerIds   : ArrayList<Int>    = arrayListOf(),
                          @SerializedName("batNonStrikerNames" ) var batNonStrikerNames : ArrayList<String> = arrayListOf(),
                          @SerializedName("batNonStrikerRuns"  ) var batNonStrikerRuns  : Double?              = null,
                          @SerializedName("batNonStrikerBalls" ) var batNonStrikerBalls : Double?              = null,
                          @SerializedName("bowlIds"            ) var bowlIds            : ArrayList<Int>    = arrayListOf(),
                          @SerializedName("bowlNames"          ) var bowlNames          : ArrayList<String> = arrayListOf(),
                          @SerializedName("bowlOvers"          ) var bowlOvers          : Double?              = null,
                          @SerializedName("bowlMaidens"        ) var bowlMaidens        : Double?              = null,
                          @SerializedName("bowlRuns"           ) var bowlRuns           : Double?              = null,
                          @SerializedName("bowlWickets"        ) var bowlWickets        : Double?              = null,
                          @SerializedName("timestamp"          ) var timestamp          : Long?              = null,
                          @SerializedName("overNum"            ) var overNum            : Double?           = null,
                          @SerializedName("batTeamName"        ) var batTeamName        : String?           = null,
                          @SerializedName("event"              ) var event              : String?           = null
)
data class CommentaryList(

    @SerializedName("commText") var commText: String? = null,
    @SerializedName("timestamp") var timestamp: Long? = null,
    @SerializedName("ballNbr") var ballNbr: Int? = null,
    @SerializedName("overNumber") var overNumber: Double? = null,
    @SerializedName("inningsId") var inningsId: Int? = null,
    @SerializedName("event") var event: String? = null,
    @SerializedName("batTeamName") var batTeamName: String? = null,
    @SerializedName("commentaryFormats") var commentaryFormats: CommentaryFormats? = CommentaryFormats(),
    @SerializedName("overSeparator") var overSeparator: OverSeperator? = OverSeperator()

)

data class PlayerOfTheMatch(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("fullName") var fullName: String? = null,
    @SerializedName("nickName") var nickName: String? = null,
    @SerializedName("captain") var captain: Boolean? = null,
    @SerializedName("keeper") var keeper: Boolean? = null,
    @SerializedName("substitute") var substitute: Boolean? = null,
    @SerializedName("teamName") var teamName: String? = null,
    @SerializedName("faceImageId") var faceImageId: Int? = null
)

data class PlayerOfTheSeries(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("fullName") var fullName: String? = null,
    @SerializedName("nickName") var nickName: String? = null,
    @SerializedName("captain") var captain: Boolean? = null,
    @SerializedName("role") var role: String? = null,
    @SerializedName("keeper") var keeper: Boolean? = null,
    @SerializedName("substitute") var substitute: Boolean? = null,
    @SerializedName("teamId") var teamId: Int? = null,
    @SerializedName("battingStyle") var battingStyle: String? = null,
    @SerializedName("bowlingStyle") var bowlingStyle: String? = null,
    @SerializedName("teamName") var teamName: String? = null,
    @SerializedName("faceImageId") var faceImageId: Int? = null
)

data class CommentaryFormats(

    @SerializedName("bold") var bold: Bold? = Bold(),
    @SerializedName("italic") var italic: Italic? = Italic()

)

data class Bold(

    @SerializedName("formatId") var formatId: ArrayList<String> = arrayListOf(),
    @SerializedName("formatValue") var formatValue: ArrayList<String> = arrayListOf()

)

data class Italic(

    @SerializedName("formatId") var formatId: ArrayList<String> = arrayListOf(),
    @SerializedName("formatValue") var formatValue: ArrayList<String> = arrayListOf()

)

data class Miniscore(

    @SerializedName("inningsId") var inningsId: Int? = null,
    @SerializedName("batsmanStriker") var batsmanStriker: BatsmanStriker? = BatsmanStriker(),
    @SerializedName("batsmanNonStriker") var batsmanNonStriker: BatsmanNonStriker? = BatsmanNonStriker(),
    @SerializedName("batTeam") var batTeam: BatTeam? = BatTeam(),
    @SerializedName("bowlerStriker") var bowlerStriker: BowlerStriker? = BowlerStriker(),
    @SerializedName("bowlerNonStriker") var bowlerNonStriker: BowlerNonStriker? = BowlerNonStriker(),
    @SerializedName("overs") var overs: Double? = null,
    @SerializedName("recentOvsStats") var recentOvsStats: String? = null,
    @SerializedName("partnerShip") var partnerShip: PartnerShip? = PartnerShip(),
    @SerializedName("currentRunRate") var currentRunRate: Double? = null,
    @SerializedName("requiredRunRate") var requiredRunRate: Double? = null,
    @SerializedName("lastWicket") var lastWicket: String? = null,
    @SerializedName("matchScoreDetails") var matchScoreDetails: MatchScoreDetails? = MatchScoreDetails(),
    @SerializedName("latestPerformance") var latestPerformance: ArrayList<LatestPerformance> = arrayListOf(),
    @SerializedName("ppData") var ppData: PpData? = PpData(),
    @SerializedName("matchUdrs") var matchUdrs: MatchUdrs? = MatchUdrs(),
    @SerializedName("overSummaryList") var overSummaryList: ArrayList<String> = arrayListOf(),
    @SerializedName("oversRem") var oversRem: Double? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("lastWicketScore") var lastWicketScore: Double? = null,
    @SerializedName("remRunsToWin") var remRunsToWin: Double? = null,
    @SerializedName("responseLastUpdated") var responseLastUpdated: Long? = null,
    @SerializedName("event") var event: String? = null,
    @SerializedName("ad") var ad: Ad? = Ad()

)

data class LatestPerformance(

    @SerializedName("runs") var runs: Int? = null,
    @SerializedName("wkts") var wkts: Int? = null,
    @SerializedName("label") var label: String? = null
)


data class Ad(

    @SerializedName("name") var name: String? = null,
    @SerializedName("layout") var layout: String? = null,
    @SerializedName("position") var position: Int? = null

)

data class MatchUdrs(

    @SerializedName("matchId") var matchId: Int? = null,
    @SerializedName("inningsId") var inningsId: Int? = null,
    @SerializedName("timestamp") var timestamp: String? = null,
    @SerializedName("team1Id") var team1Id: Int? = null,
    @SerializedName("team1Remaining") var team1Remaining: Int? = null,
    @SerializedName("team1Successful") var team1Successful: Int? = null,
    @SerializedName("team1Unsuccessful") var team1Unsuccessful: Int? = null,
    @SerializedName("team2Id") var team2Id: Int? = null,
    @SerializedName("team2Remaining") var team2Remaining: Int? = null,
    @SerializedName("team2Successful") var team2Successful: Int? = null,
    @SerializedName("team2Unsuccessful") var team2Unsuccessful: Int? = null

)

data class PpData(
    @SerializedName("pp_1") var pp1: Pp1? = Pp1()
)

data class Pp1(

    @SerializedName("ppId") var ppId: Int? = null,
    @SerializedName("ppOversFrom") var ppOversFrom: Double? = null,
    @SerializedName("ppOversTo") var ppOversTo: Int? = null,
    @SerializedName("ppType") var ppType: String? = null,
    @SerializedName("runsScored") var runsScored: Int? = null

)

data class PartnerShip(

    @SerializedName("balls") var balls: Int? = null,
    @SerializedName("runs") var runs: Int? = null
)

data class MatchScoreDetails(

    @SerializedName("matchId") var matchId: Int? = null,
    @SerializedName("inningsScoreList") var inningsScoreList: ArrayList<InningsScoreList> = arrayListOf(),
    @SerializedName("tossResults") var tossResults: TossResults? = TossResults(),
    @SerializedName("matchTeamInfo") var matchTeamInfo: ArrayList<MatchTeamInfo> = arrayListOf(),
    @SerializedName("isMatchNotCovered") var isMatchNotCovered: Boolean? = null,
    @SerializedName("matchFormat") var matchFormat: String? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("customStatus") var customStatus: String? = null,
    @SerializedName("highlightedTeamId") var highlightedTeamId: Int? = null

)

data class InningsScoreList(

    @SerializedName("inningsId") var inningsId: Int? = null,
    @SerializedName("batTeamId") var batTeamId: Int? = null,
    @SerializedName("batTeamName") var batTeamName: String? = null,
    @SerializedName("score") var score: Int? = null,
    @SerializedName("wickets") var wickets: Int? = null,
    @SerializedName("overs") var overs: Double? = null,
    @SerializedName("isDeclared") var isDeclared: Boolean? = null,
    @SerializedName("isFollowOn") var isFollowOn: Boolean? = null,
    @SerializedName("ballNbr") var ballNbr: Int? = null

)

data class BowlerStriker(

    @SerializedName("bowlId") var bowlId: Int? = null,
    @SerializedName("bowlName") var bowlName: String? = null,
    @SerializedName("bowlMaidens") var bowlMaidens: Double? = null,
    @SerializedName("bowlNoballs") var bowlNoballs: Double? = null,
    @SerializedName("bowlOvs") var bowlOvs: Double? = null,
    @SerializedName("bowlRuns") var bowlRuns: Double? = null,
    @SerializedName("bowlWides") var bowlWides: Double? = null,
    @SerializedName("bowlWkts") var bowlWkts: Double? = null,
    @SerializedName("bowlEcon") var bowlEcon: Double? = null

)

data class BatsmanStriker(

    @SerializedName("batBalls") var batBalls: Int? = null,
    @SerializedName("batDots") var batDots: Int? = null,
    @SerializedName("batFours") var batFours: Int? = null,
    @SerializedName("batId") var batId: Int? = null,
    @SerializedName("batName") var batName: String? = null,
    @SerializedName("batMins") var batMins: Int? = null,
    @SerializedName("batRuns") var batRuns: Int? = null,
    @SerializedName("batSixes") var batSixes: Int? = null,
    @SerializedName("batStrikeRate") var batStrikeRate: Double? = null
)

data class BowlerNonStriker(

    @SerializedName("bowlId") var bowlId: Int? = null,
    @SerializedName("bowlName") var bowlName: String? = null,
    @SerializedName("bowlMaidens") var bowlMaidens: Double? = null,
    @SerializedName("bowlNoballs") var bowlNoballs: Double? = null,
    @SerializedName("bowlOvs") var bowlOvs: Double? = null,
    @SerializedName("bowlRuns") var bowlRuns: Double? = null,
    @SerializedName("bowlWides") var bowlWides: Int? = null,
    @SerializedName("bowlWkts") var bowlWkts: Int? = null,
    @SerializedName("bowlEcon") var bowlEcon: Double? = null

)

data class BatsmanNonStriker(

    @SerializedName("batBalls") var batBalls: Int? = null,
    @SerializedName("batDots") var batDots: Int? = null,
    @SerializedName("batFours") var batFours: Int? = null,
    @SerializedName("batId") var batId: Int? = null,
    @SerializedName("batName") var batName: String? = null,
    @SerializedName("batMins") var batMins: Int? = null,
    @SerializedName("batRuns") var batRuns: Int? = null,
    @SerializedName("batSixes") var batSixes: Int? = null,
    @SerializedName("batStrikeRate") var batStrikeRate: Double? = null

)

data class BatTeam(

    @SerializedName("teamId") var teamId: Int? = null,
    @SerializedName("teamScore") var teamScore: Int? = null,
    @SerializedName("teamWkts") var teamWkts: Int? = null

)

data class MatchHeader(

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
    @SerializedName("status") var status: String? = null,
    @SerializedName("tossResults") var tossResults: TossResults? = TossResults(),
    @SerializedName("result") var result: Result? = Result(),
    @SerializedName("revisedTarget") var revisedTarget: RevisedTarget? = RevisedTarget(),
    @SerializedName("playersOfTheMatch") var playersOfTheMatch: ArrayList<PlayerOfTheMatch> = arrayListOf(),
    @SerializedName("playersOfTheSeries") var playersOfTheSeries: ArrayList<PlayerOfTheSeries> = arrayListOf(),
    @SerializedName("matchTeamInfo") var matchTeamInfo: ArrayList<MatchTeamInfo> = arrayListOf(),
    @SerializedName("isMatchNotCovered") var isMatchNotCovered: Boolean? = null,
    @SerializedName("team1") var team1: CTeam1? = CTeam1(),
    @SerializedName("team2") var team2: CTeam2? = CTeam2(),
    @SerializedName("seriesDesc") var seriesDesc: String? = null,
    @SerializedName("seriesId") var seriesId: Int? = null,
    @SerializedName("seriesName") var seriesName: String? = null,
    @SerializedName("alertType") var alertType: String? = null,
    @SerializedName("livestreamEnabled") var livestreamEnabled: Boolean? = null

)

data class MatchTeamInfo(
    @SerializedName("battingTeamId") var battingTeamId: Int? = null,
    @SerializedName("battingTeamShortName") var battingTeamShortName: String? = null,
    @SerializedName("bowlingTeamId") var bowlingTeamId: Int? = null,
    @SerializedName("bowlingTeamShortName") var bowlingTeamShortName: String? = null
)

data class CTeam1(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("playerDetails") var playerDetails: ArrayList<String> = arrayListOf(),
    @SerializedName("shortName") var shortName: String? = null

)

data class CTeam2(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("playerDetails") var playerDetails: ArrayList<String> = arrayListOf(),
    @SerializedName("shortName") var shortName: String? = null

)

data class TossResults(

    @SerializedName("tossWinnerId") var tossWinnerId: Int? = null,
    @SerializedName("tossWinnerName") var tossWinnerName: String? = null,
    @SerializedName("decision") var decision: String? = null

)

data class Result(

    @SerializedName("winningTeam") var winningTeam: String? = null,
    @SerializedName("resultType") var resultType: String? = null,
    @SerializedName("winByRuns") var winByRuns: Boolean? = null,
    @SerializedName("winByInnings") var winByInnings: Boolean? = null,
    @SerializedName("winningMargin") var winningMargin: Int? = null,

    )

data class RevisedTarget(

    @SerializedName("reason") var reason: String? = null

)