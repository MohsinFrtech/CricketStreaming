package com.dream.live.cricket.score.hd.scores.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dream.live.cricket.score.hd.scores.model.LiveToken
import com.dream.live.cricket.score.hd.scores.model.PlayersRankingModel
import com.dream.live.cricket.score.hd.scores.network.ApiServices
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.scores.utility.listeners.ApiResponseListener
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.await
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class PlayersRankingViewModel : ViewModel() {
    var apiResponseListener: ApiResponseListener? = null


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val _teamsList = MutableLiveData<List<PlayersRankingModel>?>()
    val teamsList: MutableLiveData<List<PlayersRankingModel>?>
        get() = _teamsList

    private val _teamsOdiList = MutableLiveData<List<PlayersRankingModel>?>()
    val teamsOdiList: MutableLiveData<List<PlayersRankingModel>?>
        get() = _teamsOdiList
    private val _teamsT20List = MutableLiveData<List<PlayersRankingModel>>()
    val teamsT20List: LiveData<List<PlayersRankingModel>>?
        get() = _teamsT20List
    private val _teamsTestList = MutableLiveData<List<PlayersRankingModel>>()
    val teamsTestList: LiveData<List<PlayersRankingModel>>?
        get() = _teamsTestList

    private val _batsmenList = MutableLiveData<List<PlayersRankingModel>>()
    val batsmenList: LiveData<List<PlayersRankingModel>>?
        get() = _batsmenList

    private val _bowlersList = MutableLiveData<List<PlayersRankingModel>>()
    val bowlersList: LiveData<List<PlayersRankingModel>>?
        get() = _bowlersList

    private val _allRoundersList = MutableLiveData<List<PlayersRankingModel>>()
    val allRoundersList: LiveData<List<PlayersRankingModel>>?
        get() = _allRoundersList


    /*private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading*/


    init {
        //_isLoading.value=true
        _teamsOdiList.value = null
        getODIRanking()
        getT20Ranking()
        getTestRanking()
    }

    fun getSpinnerData(): List<String> {
        val list = ArrayList<String>()

        list.add("ODI")
        list.add("T20")
        list.add("Test")

        return list

    }

    fun getODIRanking() {
        //_isLoading.value = true
        try {
            coroutineScope.launch {
                val addUser = LiveToken()

                addUser.token = Cons.s_token
                val body = Gson().toJson(addUser)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val getResponse = ApiServices.retrofitService.getOdiRankingPlayerAsync(body)
                try {
                    apiResponseListener?.onStarted()
                    val responseResult = getResponse.await()

                    withContext(Dispatchers.Main) {
                        responseResult.let {
                            if (it != null) {
                                it.sortedBy { it1 ->
                                    it1.rank
                                }
                                _teamsOdiList.value = it
                            }
                        }

                        apiResponseListener?.onSuccess()
                        //_isLoading.value = false
                    }
                } catch (e: Exception) {
                    when (e) {
                        is UnknownHostException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                //_isLoading.value = false
                            }
                        }
                        is SocketTimeoutException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                //_isLoading.value = false
                            }
                        }
                        else -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("An error occurred. Try again later!")
                                //_isLoading.value = false
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
        //_isLoading.value = true
        try {
            coroutineScope.launch {
                val addUser = LiveToken()
                addUser.token = Cons.s_token
                val body = Gson().toJson(addUser)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val getResponse = ApiServices.retrofitService.getT20RankingPlayerAsync(body)
                try {
                    apiResponseListener?.onStarted()
                    val responseResult = getResponse.await()

                    withContext(Dispatchers.Main) {
                        responseResult.let {
                            if (it != null) {
                                it.sortedBy { it1 ->
                                    it1.rank
                                }
                                _teamsT20List.value = it
                            }
                        }

                        apiResponseListener?.onSuccess()
                        //_isLoading.value = false
                    }
                } catch (e: Exception) {
                    when (e) {
                        is UnknownHostException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                //_isLoading.value = false
                            }
                        }
                        is SocketTimeoutException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                //_isLoading.value = false
                            }
                        }
                        else -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("An error occurred. Try again later!")
                                //_isLoading.value = false
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
        //_isLoading.value = true
        try {
            coroutineScope.launch {
                val addUser = LiveToken()

                addUser.token = Cons.s_token
                val body = Gson().toJson(addUser)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val getResponse = ApiServices.retrofitService.getTestRankingPlayerAsync(body)
                try {
                    apiResponseListener?.onStarted()
                    val responseResult = getResponse.await()

                    withContext(Dispatchers.Main) {
                        responseResult.let {
                            if (it != null) {

                                it.sortedBy { it1 ->
                                    it1.rank
                                }
                                _teamsTestList.value = it

//                                addAllToList()
                            }
                        }

                        apiResponseListener?.onSuccess()
                        //_isLoading.value = false
                    }
                } catch (e: Exception) {
                    when (e) {
                        is UnknownHostException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                //_isLoading.value = false
                            }
                        }
                        is SocketTimeoutException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                //_isLoading.value = false
                            }
                        }
                        else -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("An error occurred. Try again later!")
                                //_isLoading.value = false
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    private fun addAllToList() {
//        newList.addAll(_teamsT20List.value ?: emptyList())
//        newList.addAll(_teamsTestList.value ?: emptyList())
//        newList.addAll(_teamsOdiList.value ?: emptyList())
//
//        newList.forEach {
//            if (it.category.equals(Cons.player_bowler, true)) {
//                bowlersNewList.add(it)
//            } else if (it.category.equals(Cons.player_batsmen, true)) {
//                batsmenNewList.add(it)
//            } else {
//                allRounderNewList.add(it)
//            }
//        }
//
//        _batsmenList.value = batsmenNewList
//        _bowlersList.value = bowlersNewList
//        _allRoundersList.value = allRounderNewList
//
//    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.let {
            viewModelJob.cancel()
        }

    }
}
