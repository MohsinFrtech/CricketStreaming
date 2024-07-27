package com.dream.live.cricket.score.hd.scores.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.dream.live.cricket.score.hd.scores.model.LiveToken
import com.dream.live.cricket.score.hd.scores.model.RankingTeams
import com.dream.live.cricket.score.hd.scores.network.ApiServices
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.scores.utility.listeners.ApiResponseListener
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.await
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RankingViewModel : ViewModel() {
    var apiResponseListener: ApiResponseListener? = null


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val _teamsList = MutableLiveData<List<RankingTeams>?>()
    val teamsList: MutableLiveData<List<RankingTeams>?>
        get() = _teamsList

    private val _teamsOdiList = MutableLiveData<List<RankingTeams>>()
    val teamsOdiList: LiveData<List<RankingTeams>>?
        get() = _teamsOdiList
    private val _teamsT20List = MutableLiveData<List<RankingTeams>>()
    val teamsT20List: LiveData<List<RankingTeams>>?
        get() = _teamsT20List
    private val _teamsTestList = MutableLiveData<List<RankingTeams>>()
    val teamsTestList: LiveData<List<RankingTeams>>?
        get() = _teamsTestList

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        _isLoading.value = false
        getODIRanking()
        getT20Ranking()
        getTestRanking()

    }

    fun getSpinnerData(): List<String> {
        val list = ArrayList<String>()

        list.add("ODI")
        list.add("T20")
        list.add("TEST")

        return list
    }

    fun getODIRanking() {
        _isLoading.value = true
        try {
            coroutineScope.launch {
                val addUser = LiveToken()

                addUser.token = Cons.s_token
                val body = Gson().toJson(addUser)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val getResponse = ApiServices.retrofitService.getOdiRankingDataAsync(body)
                try {
                    apiResponseListener?.onStarted()
                    val responseResult = getResponse.await()

                    withContext(Dispatchers.Main) {
                        responseResult.let {
                            if (it != null) {
                                _teamsOdiList.value = it
                            }
                        }

                        apiResponseListener?.onSuccess()
                        _isLoading.value = false
                    }
                } catch (e: Exception) {
                    when (e) {
                        is UnknownHostException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                _isLoading.value = false
                            }
                        }
                        is SocketTimeoutException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                _isLoading.value = false
                            }
                        }
                        else -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("An error occurred. Try again later!")
                                _isLoading.value = false
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getT20Ranking() {
        _isLoading.value = true
        try {
            coroutineScope.launch {
                val addUser = LiveToken()

                addUser.token = Cons.s_token
                val body = Gson().toJson(addUser)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val getResponse = ApiServices.retrofitService.getT20RankingDataAsync(body)
                try {
                    apiResponseListener?.onStarted()
                    val responseResult = getResponse.await()

                    withContext(Dispatchers.Main) {
                        responseResult.let {
                            if (it != null) {
                                _teamsT20List.value = it
                            }
                        }

                        apiResponseListener?.onSuccess()
                        _isLoading.value = false
                    }
                } catch (e: Exception) {
                    when (e) {
                        is UnknownHostException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                _isLoading.value = false
                            }
                        }
                        is SocketTimeoutException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                _isLoading.value = false
                            }
                        }
                        else -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("An error occurred. Try again later!")
                                _isLoading.value = false
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getTestRanking() {
        _isLoading.value = true
        try {
            coroutineScope.launch {
                val addUser = LiveToken()

                addUser.token = Cons.s_token
                val body = Gson().toJson(addUser)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val getResponse = ApiServices.retrofitService.getTestRankingDataAsync(body)
                try {
                    apiResponseListener?.onStarted()
                    val responseResult = getResponse.await()

                    withContext(Dispatchers.Main) {
                        responseResult.let {
                            if (it != null) {

                                _teamsTestList.value = it
                            }
                        }

                        apiResponseListener?.onSuccess()
                        _isLoading.value = false
                    }
                } catch (e: Exception) {
                    when (e) {
                        is UnknownHostException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                _isLoading.value = false
                            }
                        }
                        is SocketTimeoutException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                _isLoading.value = false
                            }
                        }
                        else -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("An error occurred. Try again later!")
                                _isLoading.value = false
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun formatTeamsSelected(name: Any) {
        _teamsList.value = null
        when (name) {
            0 -> {
                _teamsList.value = _teamsOdiList.value

            }
            1 -> {
                _teamsList.value = _teamsT20List.value

            }
            2 -> {
                _teamsList.value = _teamsTestList.value

            }
        }

    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.let {
            viewModelJob.cancel()
        }

    }
}
