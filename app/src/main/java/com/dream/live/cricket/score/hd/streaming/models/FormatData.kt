package com.dream.live.cricket.score.hd.streaming.models

import androidx.media3.common.Format
import androidx.media3.common.Tracks

data class FormatData(var token: Format?=null,
    var trckGroup: Tracks.Group?=null,
    var checkUncheck:Boolean=false
)