package com.dream.live.cricket.score.hd.streaming.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dream.live.cricket.score.hd.scores.utility.listeners.ApiResponseListener
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.dream.live.cricket.score.hd.streaming.models.AddUser
import com.dream.live.cricket.score.hd.streaming.models.DataModel
import com.dream.live.cricket.score.hd.streaming.models.StoneFile
import com.dream.live.cricket.score.hd.streaming.network.RetrofitController
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.appVersionCode
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.baseUrlChannel
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.baseUrlDemo
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.cementType
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.channel_url_val
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.myUserCheck1
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.passVal
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.passphraseVal
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.stringId
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userBaseExtraDel1
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userBaseExtraDel2
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userIp
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants.userLinkVal
import com.dream.live.cricket.score.hd.streaming.utils.objects.Defamation
import com.dream.live.cricket.score.hd.streaming.utils.objects.Defamation.stoneDel
import com.dream.live.cricket.score.hd.utils.InternetUtil
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.await
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class OneViewModel(application: Application?) : AndroidViewModel(application!!) {

    private val app: Application? = application
    private val tags: String = "OneViewModel"
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    val navigationCheck = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
//    val isInternet = MutableLiveData<Boolean>()
    var userLink = MutableLiveData<Boolean>()
//    var baseValue = MutableLiveData<Boolean>()
    var userLinkStatus = MutableLiveData<Boolean>()
    var apiResponseListener: ApiResponseListener? = null


    private val _dataModelList = MutableLiveData<DataModel>()
    val dataModelList: LiveData<DataModel>
        get() = _dataModelList

    init {
        isLoading.value = true
        navigationCheck.value = "nothing"
        userLink.value = false
    }

    fun onRefreshFixtures() {

        getApiData()
    }

    fun getApiData() {

        isLoading.value = true

        if (InternetUtil.isInternetOn(app)) {
            if (baseUrlChannel != "") {

                coroutineScope.launch {
                    val addUser = StoneFile()
                    addUser.id = stringId
                    addUser.auth_token = cementType
                    addUser.build_no = "0".toString()
//                    addUser.build_no = appVersionCode.toString()

                    val body = Gson().toJson(addUser)
                        .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                    val getResponse = RetrofitController.apiServiceChannel.getChannelsAsync(
                        body
                    )

                    try {

                        val responseResult = getResponse.await()
                        withContext(Dispatchers.Main) {
                            responseResult.let {
                                try {
                                    val stringValue = it.data?.let { it1 -> stoneDel(it1, passVal) }
                                    var jobj: JSONObject? = JSONObject()
                                    jobj = stringValue?.let { JSONObject(it) }
                                    val gson: Gson = GsonBuilder()
                                        .setLenient()
                                        .create()


                                    val date = gson.fromJson(jobj.toString(), DataModel::class.java)

                                    _dataModelList.value = date
                                    if (userIp.equals("userIp", true)) {
                                        getIP()
                                    }

                                    if (date.extra_1.toString().isNotEmpty()) {
                                        date.extra_1 = Defamation.decryptBase6(date.extra_1)
                                        val encrypt = date.extra_1.toString().trim()
                                        val yourArray: List<String> =
                                            encrypt.split(userBaseExtraDel2)
                                        myUserCheck1 = yourArray[0].trim()
                                        val myRVal: List<String> =
                                            yourArray[1].split(userBaseExtraDel1)
                                        passphraseVal = myRVal[0].trim()
                                    }


                                } catch (e: Exception) {
                                    apiResponseListener?.onFailure("Something is wrong with response")
                                    isLoading.value = false
                                }


                            }
                            isLoading.value = false

                        }

                    } catch (e: Exception) {
                        Log.d("Exception", "" + "coming34......" + e.localizedMessage)

                        withContext(Dispatchers.Main) {
                            isLoading.value = false
                            apiResponseListener?.onFailure("Something went wrong, Please try again")
                        }
                        if (e is SocketTimeoutException || e is UnknownHostException){
                            withContext(Dispatchers.Main) {
                                isLoading.value = false
                                apiResponseListener?.onFailure("Server is taking too long to respond.")
                            }
                        }
                    }
                }

            } else {
                apiResponseListener?.onFailure("Something went wrong,Please retry.")
            }

        } else {
            isLoading.value = false
            apiResponseListener?.onFailure("Internet connection lost! , please check your internet connection")
        }

    }


    fun getDemoData() {
        isLoading.value = true

        if (baseUrlDemo != "") {
            if (InternetUtil.isInternetOn(app)) {
//                isInternet.value = true
                val addUser = AddUser()
                addUser.passphrase = passphraseVal
                addUser.channel_url = channel_url_val
                val body = Gson().toJson(addUser)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


                coroutineScope.launch {
                    val getResponse = RetrofitController.apiServiceDemo.getDemoDataAsync(
                        body
                    )

                    try {
                        val responseResult = getResponse.await()
                        withContext(Dispatchers.Main) {
                            responseResult.let {
                                if (!it?.url.isNullOrEmpty()) {

                                    userLinkVal = it?.url.toString()
                                    userLinkStatus.value = true

                                }

                            }
                            isLoading.value = false
                        }

                    } catch (e: Exception) {
                        Log.d(tags, "getDemoData Exception ..." + e.localizedMessage)

                        withContext(Dispatchers.Main) {

                            isLoading.value = false
                        }

                    }

                }

            } else {
//                isInternet.value = false
                isLoading.value = false
            }
        } else {
//            baseValue.value = false
        }
    }


    private fun getIP() {
        if (InternetUtil.isInternetOn(app)) {
            coroutineScope.launch {
                val getResponse = RetrofitController.apiServiceIP.getIPAsync()
                try {
                    val responseResult = getResponse?.await()
                    withContext(Dispatchers.Main) {
                        if (responseResult != null) {
                            userIp = responseResult.toString()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }

    }

    // On ViewModel Cleared
    override fun onCleared() {
        super.onCleared()
        viewModelJob.let {
            viewModelJob.cancel()
        }

    }

}