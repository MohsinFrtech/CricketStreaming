package com.traumsportzone.live.cricket.tv.models

import androidx.media3.common.Format
import androidx.media3.common.Tracks

data class FormatDataAudio(var token: Format?=null,
                           var trckGroup: Tracks.Group?=null,
                           var checkUncheck:Boolean=false
)