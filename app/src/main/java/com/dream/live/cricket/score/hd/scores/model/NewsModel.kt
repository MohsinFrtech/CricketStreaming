package com.dream.live.cricket.score.hd.scores.model

import com.google.gson.annotations.SerializedName

data class NewsModel (
    @SerializedName("storyList"       ) var storyList       : ArrayList<StoryList> = arrayListOf(),
    @SerializedName("lastUpdatedTime" ) var lastUpdatedTime : String?              = null,
    @SerializedName("appIndex"        ) var appIndex        : AppIndex?            = AppIndex()
)

data class StoryList (

    @SerializedName("story" ) var story : Story? = Story()

)
data class Story (

    @SerializedName("id"           ) var id           : Int?          = null,
    @SerializedName("hline"        ) var hline        : String?       = null,
    @SerializedName("intro"        ) var intro        : String?       = null,
    @SerializedName("pubTime"      ) var pubTime      : String?       = null,
    @SerializedName("source"       ) var source       : String?       = null,
    @SerializedName("storyType"    ) var storyType    : String?       = null,
    @SerializedName("imageId"      ) var imageId      : Int?          = null,
    @SerializedName("seoHeadline"  ) var seoHeadline  : String?       = null,
    @SerializedName("context"      ) var context      : String?       = null,
    @SerializedName("coverImage"   ) var coverImage   : CoverImage?   = CoverImage(),

)

data class AppIndex (

    @SerializedName("seoTitle" ) var seoTitle : String? = null,
    @SerializedName("webURL"   ) var webURL   : String? = null

)
data class CoverImage (

    @SerializedName("id"      ) var id      : String? = null,
    @SerializedName("caption" ) var caption : String? = null,
    @SerializedName("source"  ) var source  : String? = null

)