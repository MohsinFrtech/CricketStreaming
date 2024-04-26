package com.dream.live.cricket.score.hd.scores.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.dream.live.cricket.score.hd.scores.model.AllTeamsModel
import com.dream.live.cricket.score.hd.scores.model.LiveToken
import com.dream.live.cricket.score.hd.scores.network.ApiServices
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.scores.utility.listeners.ApiResponseListener
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.await

class AllTeamViewModel : ViewModel() {
    private var apiResponseListener: ApiResponseListener? = null

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    private val _sliderList = MutableLiveData<List<AllTeamsModel?>>()
    val teamList: LiveData<List<AllTeamsModel?>>
        get() = _sliderList
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {

        _isLoading.value=true
        loadAllTeams()

    }

    fun againLoad()
    {
//        _isLoading.value=false
//        isTabSelect.value="T20"
        _isLoading.value=true
        loadAllTeams()
    }
    private fun loadAllTeams() {
        coroutineScope.launch {
            val addUser = LiveToken()

            addUser.token = Cons.s_token
            val body = Gson().toJson(addUser)
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val getResponse = ApiServices.retrofitService.getTeamsDataAsync(body)

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