package com.dream.live.cricket.score.hd.scores.model

import com.google.gson.annotations.SerializedName

data class NewsdetailModel(@SerializedName("id"              ) var id              : Int?               = null,
                           @SerializedName("context"         ) var context         : String?            = null,
                           @SerializedName("headline"        ) var headline        : String?            = null,
                           @SerializedName("publishTime"     ) var publishTime     : String?            = null,
                           @SerializedName("coverImage"      ) var coverImage      : CoverImage2?        = CoverImage2(),
                           @SerializedName("content"         ) var content         : ArrayList<ContentInside> = arrayListOf(),
                           @SerializedName("authors"         ) var authors         : ArrayList<Authors> = arrayListOf(),
                           @SerializedName("tags"            ) var tags            : ArrayList<TagsNews>    = arrayListOf(),
                           @SerializedName("appIndex"        ) var appIndex        : AppIndex?          = AppIndex(),
                           @SerializedName("storyType"       ) var storyType       : String?            = null,
                           @SerializedName("lastUpdatedTime" ) var lastUpdatedTime : String?            = null,
                           @SerializedName("intro"           ) var intro           : String?            = null,
                           @SerializedName("source"          ) var source          : String?            = null,
                           )
data class CoverImage2 (

    @SerializedName("id"      ) var id      : String? = null,
    @SerializedName("caption" ) var caption : String? = null,
    @SerializedName("source"  ) var source  : String? = null

)
data class ContentInside (

    @SerializedName("content"  ) var content : Content? = Content(),
)
data class Content (

    @SerializedName("contentType"  ) var contentType  : String? = null,
    @SerializedName("contentValue" ) var contentValue : String? = null

)

data class Authors (

    @SerializedName("id"      ) var id      : Int?    = null,
    @SerializedName("name"    ) var name    : String? = null,
    @SerializedName("imageId" ) var imageId : Int?    = null

)


data class TagsNews (

    @SerializedName("itemName" ) var itemName : String? = null,
    @SerializedName("itemType" ) var itemType : String? = null,
    @SerializedName("itemId"   ) var itemId   : String? = null

)
