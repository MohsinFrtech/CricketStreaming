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

class MatchesCategoryViewModel : ViewModel() {
    var apiResponseListener: ApiResponseListener? = null

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    private val _matchesList = MutableLiveData<List<LiveScoresModel?>>()
    val matchesList: LiveData<List<LiveScoresModel?>>
        get() = _matchesList
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    var isTabSelect:MutableLiveData<String> = MutableLiveData<String>()

    init {

        _isLoading.value=true
        isTabSelect.value= Cons.match_category_recent

    }


    fun loadMatchesList(teamId: Int) {
        _isLoading.value = true

        coroutineScope.launch {
            val addUser = LiveToken()

            addUser.token = Cons.s_token
            val body = Gson().toJson(addUser)
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val getResponse = ApiServices.retrofitService.getMatchesList(body, teamId)

            try {

                val responseResult = getResponse.await()
                withContext(Dispatchers.Main) {
                    responseResult.let {
                        if (it != null) {

                            it.sortedByDescending { it1 ->
                                it1.updated_at
                            }

                            _matchesList.value = it
                            _isLoading.value = false

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

                    _isLoading.value = false

                }

            }

        }

    }


}