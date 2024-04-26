package com.micro.live.cricket.hd.network

import com.dream.live.cricket.score.hd.streaming.models.DataStone
import com.dream.live.cricket.score.hd.streaming.models.DataStone2
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.IpApi
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.channelApi
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userApi
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET(IpApi)
    fun getIPAsync(): Call<String?>?


    @POST(userApi)
    @Headers("Content-Type: application/json")
    fun getDemoDataAsync(
        @Body body: RequestBody
    ): Call<DataStone2?>

    @POST(channelApi)
    @Headers("Content-Type: application/json")
    fun getChannelsAsync(
        @Body body: RequestBody
    ): Call<DataStone>


}