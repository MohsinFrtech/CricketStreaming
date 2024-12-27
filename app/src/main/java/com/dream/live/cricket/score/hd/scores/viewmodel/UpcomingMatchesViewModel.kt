package com.dream.live.cricket.score.hd.scores.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.dream.live.cricket.score.hd.scores.model.LiveScoresModel
import com.dream.live.cricket.score.hd.scores.model.LiveToken
import com.dream.live.cricket.score.hd.scores.network.ApiServices
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.scores.utility.listeners.ApiResponseListener
import com.dream.live.cricket.score.hd.streaming.utils.objects.SharedPreference
import com.google.gson.JsonArray
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.await
import java.util.Date

class UpcomingMatchesViewModel(application: Application) : AndroidViewModel(application) {
    private var apiResponseListener: ApiResponseListener? = null
    private var appContext = application
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    private val _sliderList = MutableLiveData<List<LiveScoresModel?>>()
    val sliderList: LiveData<List<LiveScoresModel?>>
        get() = _sliderList
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private var preference: SharedPreference? = null

    var isTabSelect:MutableLiveData<String> = MutableLiveData<String>()

    init {
        _isLoading.value=true
        isTabSelect.value="T20"
        loadUpcomingMatches()
    }

    fun againLoad()
    {
//        _isLoading.value=false
//        isTabSelect.value="T20"
        _isLoading.value=true
        loadUpcomingMatches()
    }

    private fun saveResponse(liveScoresModels: List<LiveScoresModel>) {
        preference = SharedPreference(appContext)
        var jsonData = Gson().toJson(liveScoresModels)
        var lastRefresh =  Date() // If the user just loaded the page you don't want to refresh either
        Log.d("json Representation","data"+lastRefresh)

//        setInterval(function(){
//
//            //first, check time, if it is 9 AM, reload the page
//            var now =  Date()
//            var lastdiff = now -  lastRefresh
//            if (now.getHours() == 9 &&  now- lastRefresh > 1000 * 60 * 60 * 6) { // If it is between 9 and ten AND the last refresh was longer ago than 6 hours refresh the page.
//                location.reload();
//            }
//
//            //do other stuff
//
//        },60000);
    }

    private fun loadUpcomingMatches() {
        coroutineScope.launch {
            val addUser = LiveToken()

            addUser.token = Cons.s_token
            val body = Gson().toJson(addUser)
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val getResponse = ApiServices.retrofitService.getUpcomingDataAsync(body)

            try {

                val responseResult = getResponse.await()
                withContext(Dispatchers.Main) {
                    responseResult.let {
                        if (it != null) {

//                            saveResponse(it)
//                            it.sortedByDescending { it1 ->
//                                it1.updated_at
//                            }

                            _sliderList.value = it
                            _isLoading.value=false

                            /* _sliderSetChanged.call()
                             _dataSetChanged.call()*/
                            apiResponseListener?.onSuccess()
                        }

                    }

                }

            } catch (e: Exception) {

                Log.d("live Api", "Exception : ${e.localizedMessage}")
                Log.d("live Api", "Exception : ${e.cause}")

                withContext(Dispatchers.Main) {

                    _isLoading.value=false

                }

            }

        }

    }


}