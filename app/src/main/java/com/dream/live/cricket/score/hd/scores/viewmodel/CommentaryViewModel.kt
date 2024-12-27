package com.dream.live.cricket.score.hd.scores.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dream.live.cricket.score.hd.scores.model.CommentryModelClass
import com.dream.live.cricket.score.hd.scores.model.LiveToken
import com.dream.live.cricket.score.hd.scores.model.NewsModel
import com.dream.live.cricket.score.hd.scores.model.NewsdetailModel
import com.dream.live.cricket.score.hd.scores.model.ScoreboardModel
import com.dream.live.cricket.score.hd.scores.model.SquadModel
import com.dream.live.cricket.score.hd.scores.network.ApiServices
import com.dream.live.cricket.score.hd.scores.utility.Cons
import com.dream.live.cricket.score.hd.scores.utility.listeners.ApiResponseListener
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.await

class CommentaryViewModel : ViewModel() {
    var apiResponseListener: ApiResponseListener? = null
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    private val _commentaryModel = MutableLiveData<CommentryModelClass?>()
    val commentaryModel: LiveData<CommentryModelClass?>
        get() = _commentaryModel
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _squadModel = MutableLiveData<SquadModel?>()
    val squadModel: LiveData<SquadModel?>
        get() = _squadModel

    private val _scoreCardModel = MutableLiveData<ScoreboardModel?>()
    val scoreCardModel: LiveData<ScoreboardModel?>
        get() = _scoreCardModel


    private val _storiesList = MutableLiveData<NewsModel?>()
    val storiesList: LiveData<NewsModel?>
        get() = _storiesList

    private val _newsDetail = MutableLiveData<NewsdetailModel?>()
    val newsDetail: LiveData<NewsdetailModel?>
        get() = _newsDetail

    init {
        _isLoading.value = true
    }

    fun loadCommentary() {
        _isLoading.value = true
//        _commentaryModel.value= CommentryModelClass()
        coroutineScope.launch {
            loadParticularMatchCommentary()
        }
    }

    fun loadNewsDetail(){
        _isLoading.value = true
        coroutineScope.launch {
            loadNewsDetailData()
        }
    }

    private suspend fun loadNewsDetailData()
    {
        val addUser = LiveToken()
        addUser.token = Cons.s_token
        addUser.news_id = Cons.newsId
        val body = Gson().toJson(addUser)
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val getResponse = ApiServices.retrofitService2.getNewsDetail(body)
        try {
            val responseResult = getResponse.await()
            withContext(Dispatchers.Main) {
                responseResult.let {
                    if (it != null) {
                        _isLoading.value = false
                        _newsDetail.value = it
                        apiResponseListener?.onSuccess()
                    }

                }

            }

        } catch (e: Exception) {
            Log.d("live Api", "Exception : ${e.localizedMessage}")
            Log.d("live Api", "Exception : ${e.cause}")
            withContext(Dispatchers.Main) {
                _isLoading.value = false
                apiResponseListener?.onFailure("Something went wrong,please retry")
            }
        }
    }
    fun loadScoreBoard(){
        coroutineScope.launch {
            getMatchScoreBoard()
        }
    }

    fun getAllNewsList(){
        coroutineScope.launch {
            getAllNewsListFromBackend()
        }
    }

    private suspend fun getAllNewsListFromBackend() {
        val addUser = LiveToken()
        addUser.token = Cons.s_token
        val body = Gson().toJson(addUser)
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val getResponse = ApiServices.retrofitService2.getAllNews(body)
        try {
            val responseResult = getResponse.await()
            withContext(Dispatchers.Main) {
                responseResult.let {
                    if (it != null) {
                        apiResponseListener?.onSuccess()
                        _storiesList.value = it
//                        _isLoading.value = false
                        apiResponseListener?.onSuccess()
                    }

                }

            }

        } catch (e: Exception) {
            Log.d("live Api", "Exception : ${e.localizedMessage}")
            Log.d("live Api", "Exception : ${e.cause}")
            withContext(Dispatchers.Main) {
                apiResponseListener?.onFailure("Something went wrong,please retry")
            }

        }
    }

    private suspend fun getMatchScoreBoard() {
        val addUser = LiveToken()
        addUser.token = Cons.s_token
        addUser.match_id = Cons.matchId
        val body = Gson().toJson(addUser)
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val getResponse = ApiServices.retrofitService2.getScoreboard(body)
        try {
            val responseResult = getResponse.await()
            withContext(Dispatchers.Main) {
                responseResult.let {
                    if (it != null) {
                        apiResponseListener?.onSuccess()
                        _scoreCardModel.value = it
//                        _isLoading.value = false
                        apiResponseListener?.onSuccess()
                    }

                }

            }

        } catch (e: Exception) {
            Log.d("live Api", "Exception : ${e.localizedMessage}")
            Log.d("live Api", "Exception : ${e.cause}")
            withContext(Dispatchers.Main) {
                apiResponseListener?.onFailure("Something went wrong,please retry")
            }

        }
    }

    private suspend fun loadParticularMatchCommentary() {
        val addUser = LiveToken()
        addUser.token = Cons.s_token
        addUser.match_id = Cons.matchId
        val body = Gson().toJson(addUser)
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val getResponse = ApiServices.retrofitService2.getMatchCommentary(body)
        try {
            val responseResult = getResponse.await()
            withContext(Dispatchers.Main) {
                responseResult.let {
                    if (it != null) {
                        apiResponseListener?.onSuccess()
                        _commentaryModel.value = it
                        _isLoading.value = false
                    }

                }

            }

        } catch (e: Exception) {

            Log.d("live Api", "Exception : ${e.localizedMessage}")
            Log.d("live Api", "Exception : ${e.cause}")

            withContext(Dispatchers.Main) {
                apiResponseListener?.onFailure("Something went wrong,please retry")
                _isLoading.value = false

            }

        }

    }

    fun getMatchTeamSquad(){
        coroutineScope.launch {
            loadMatchSquadInfo()
        }
    }

    private suspend fun loadMatchSquadInfo() {
        val addUser = LiveToken()
        addUser.token = Cons.s_token
        addUser.match_id = Cons.matchId
        val body = Gson().toJson(addUser)
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val getResponse = ApiServices.retrofitService2.getTeamSquadInfo(body)
        try {
            val responseResult = getResponse.await()
            withContext(Dispatchers.Main) {
                responseResult.let {
                    if (it != null) {
                        apiResponseListener?.onSuccess()
                        _squadModel.value = it
//                        _isLoading.value = false
                        apiResponseListener?.onSuccess()
                    }

                }

            }

        } catch (e: Exception) {
            Log.d("live Api", "Exception : ${e.localizedMessage}")
            Log.d("live Api", "Exception : ${e.cause}")
            withContext(Dispatchers.Main) {
                apiResponseListener?.onFailure("Something went wrong,please retry")
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
//        _commentaryModel.value=CommentryModelClass()
//        _squadModel.value = SquadModel()
    }


}