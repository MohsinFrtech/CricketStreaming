package com.dream.live.cricket.score.hd.scores.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.dream.live.cricket.score.hd.scores.model.LiveScoresModel
import com.dream.live.cricket.score.hd.scores.model.LiveToken
import com.dream.live.cricket.score.hd.scores.network.ApiServices
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.scores.utility.listeners.ApiResponseListener
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.await

class AllMatchesViewModel : ViewModel() {
    private var apiResponseListener: ApiResponseListener? = null

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    private val _sliderList = MutableLiveData<List<LiveScoresModel?>>()
    val sliderList: LiveData<List<LiveScoresModel?>>
        get() = _sliderList
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    var isTabSelect:MutableLiveData<String> = MutableLiveData<String>()

    init {
        _isLoading.value=true
        isTabSelect.value="T20"
        loadRecentMatches()
    }

    fun againLoad()
    {
//        _isLoading.value=false
//        isTabSelect.value="T20"
        loadRecentMatches()
    }
    private fun loadRecentMatches() {
        coroutineScope.launch {
            val addUser = LiveToken()
            addUser.token = Cons.s_token
            val body = Gson().toJson(addUser)
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val getResponse = ApiServices.retrofitService.getRecentDataAsync(body)

            try {

                val responseResult = getResponse.await()
                withContext(Dispatchers.Main) {
                    responseResult.let {
                        if (it != null) {

                            it.sortedByDescending { it1 ->
                                it1.updated_at
                            }

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