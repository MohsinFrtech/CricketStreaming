package com.dream.live.cricket.score.hd.scores.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.dream.live.cricket.score.hd.scores.model.LiveScoresModel
import com.dream.live.cricket.score.hd.scores.model.LiveToken
import com.dream.live.cricket.score.hd.scores.network.ApiServices
import com.dream.live.cricket.score.hd.scores.utility.Cons.s_token
import com.dream.live.cricket.score.hd.scores.utility.Cons.socketAuth
import com.dream.live.cricket.score.hd.scores.utility.Cons.socketUrl
import com.dream.live.cricket.score.hd.scores.utility.listeners.ApiResponseListener
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.ObserveLiveScore
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import retrofit2.await
import java.net.SocketTimeoutException
import java.net.URI
import java.net.UnknownHostException
import java.nio.ByteBuffer


class LiveViewModel : ViewModel(),ObserveLiveScore  {

    private var apiResponseListener: ApiResponseListener? = null

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _sliderList = MutableLiveData<List<LiveScoresModel?>?>()
    val sliderList: MutableLiveData<List<LiveScoresModel?>?>
        get() = _sliderList


    private var webClient: MyWebSocketClient? = null


    private val _isLoading = ObservableBoolean()
    val isLoading: ObservableBoolean
        get() = _isLoading
    private val _checkSocketConn = ObservableBoolean()

    var isSocketUrl = MutableLiveData<Boolean>()

    init {
        //Get Data from Api for slider
        _checkSocketConn.set(false)
        isSocketUrl.value = false
    }


    fun stopWebSocket() {
        if (webClient?.isOpen == true)
            webClient?.close(1000, "Closing connection")
    }

    fun runSocketCode()
    {
        try {
            Log.d("SocketCode", "start")
            val serverUri = URI(socketUrl)
            webClient = MyWebSocketClient(serverUri, liveScore = this)
            webClient?.connect()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }






    fun getsliderData() {
        _isLoading.set(true)

        if (socketUrl.isNotEmpty()) {
            isSocketUrl.value = true
        }

        coroutineScope.launch {
            try {

                coroutineScope.launch {
                    val addUser = LiveToken()

                    addUser.token = s_token
                    val body = Gson().toJson(addUser)
                        .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                    val getResponse = ApiServices.retrofitService.getLiveDataAsync(body)

                    try {
                        val responseResult = getResponse.await()
                        withContext(Dispatchers.Main) {
                            responseResult.let {
                                if (it != null) {

                                    it.sortedByDescending { it1 ->
                                        it1.updated_at
                                    }

                                    Log.d("live Api", it.toString())

                                    _sliderList.value = it


                                    /* _sliderSetChanged.call()
                                     _dataSetChanged.call()*/
                                    apiResponseListener?.onSuccess()
                                    _isLoading.set(false)
                                }

                            }
                            _isLoading.set(false)
                        }

                    } catch (e: Exception) {

                        Log.d("live Api", "Exception : ${e.localizedMessage}")
                        Log.d("live Api", "Exception : ${e.cause}")

                        withContext(Dispatchers.Main) {

                            _isLoading.set(false)

                        }

                    }

                }


            } catch (e: Exception) {
                if (e is UnknownHostException) {
                    withContext(Dispatchers.Main) {
                        apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                        _isLoading.set(false)
                    }
                } else {
                    when (e) {
                        is UnknownHostException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                _isLoading.set(false)
                            }
                        }
                        is SocketTimeoutException -> {
                            withContext(Dispatchers.Main) {
                                apiResponseListener?.onFailure("Kindly check you Internet Connection!")
                                _isLoading.set(false)
                            }
                        }
                        else -> {
                            withContext(Dispatchers.Main) {
                                println("yell" + e.toString())
                                //  apiResponseListener?.onFailure("An error occurred. Try again later!")
                                _isLoading.set(false)
                            }
                        }
                    }
                }
            }

        }

    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()

        // Close the WebSocket connection
        if (webClient?.isOpen == true)
            webClient?.close(1000, "Closing connection")
    }


    class MyWebSocketClient(serverUri: URI?, private val liveScore: ObserveLiveScore?) :
        WebSocketClient(serverUri) {

        override fun onOpen(handshakedata: ServerHandshake?) {
//            Log.d("SocketCode", "onOpen : ${handshakedata.toString()}")
            send(socketAuth)
        }

        override fun onMessage(message: String?) {
            try {
                val jobj: JSONObject? = message?.let { JSONObject(it) }
                val gson: Gson = GsonBuilder()
                    .setLenient()
                    .create()

//                Log.d("SocketCode", "message : $message")

                if (jobj?.has("type") == false) {


                    val identifier = jobj.getString("message")

                    // Use TypeToken to define the type of the list
                    val listType = object : TypeToken<List<LiveScoresModel>>() {}.type

                    // Parse the JSON array into a mutable list of LiveScoreModel objects
                    val liveScoreList: MutableList<LiveScoresModel> =
                        gson.fromJson(identifier, listType)

                    if (liveScoreList.isNotEmpty()) {
                        liveScore?.scoresData(liveScoreList)
                    }

                } else {
                    val type = jobj?.getString("type")
                    Log.d("SocketCode", "type : $type")
                }

            } catch (e: Exception) {
                Log.d("SocketCode2", "message exception" + e.message)

            }
        }

        override fun onMessage(bytes: ByteBuffer?) {
            super.onMessage(bytes)
        }

        override fun onClose(code: Int, reason: String?, remote: Boolean) {
            Log.d("SocketCode", "close" + reason.toString())

        }

        override fun onError(ex: java.lang.Exception?) {
            Log.d("SocketCode", "error" + ex.toString())

        }



    }



    override fun scoresData(liveModel: List<LiveScoresModel>) {
        liveModel.sortedByDescending { it1 ->
            it1.updated_at
        }
        coroutineScope.launch {
            withContext(Dispatchers.Main) {
                _sliderList.value = null

                _sliderList.value = liveModel
            }
        }


    }




}
