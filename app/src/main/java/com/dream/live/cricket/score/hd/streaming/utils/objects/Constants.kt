package com.dream.live.cricket.score.hd.streaming.utils.objects

import com.cleversolutions.ads.MediationManager
import com.dream.live.cricket.score.hd.BuildConfig
import com.dream.live.cricket.score.hd.streaming.models.FormatData
import com.dream.live.cricket.score.hd.streaming.utils.interfaces.OnHomePressedListener


object Constants {
    const val dash = "dash"
    var clearKeyKey=""
    var clearKeyId =""
    var USER_AGENT = "ExoPlayer-Drm"
    var xForwardedKey =""
    var mListener: OnHomePressedListener? = null
    var cas_Ai = "casAi"
    var casAiId =""
    var casAiAdManager: MediationManager? = null
    var isCasAiInit = false

    var playerActivityInPip=false
    var location1Provider = "none"
    var tapPositionProvider ="none"
    const val tap = "Tap"

    val dataFormats: MutableList<FormatData> =
        java.util.ArrayList<FormatData>()
    //Ads
    const val unityTestMode = false
    const val admob = "admob"
    const val adManagerAds = "admanager"
    const val facebook = "facebook"
    const val chartBoost = "chartboost"
    const val unity = "unity"
    const val startApp = "startapp"
    var checkNativeAdProvider = "none"
    var middleAdProvider = "none"
    var positionClick2 = 0
    var previousClick2 = -1
    const val consentKey="Consent"
    var admobInterstitial = ""
    var googleAdMangerInterstitial = ""
    var googleAdMangerBanner = ""
    var googleAdMangerNative = ""

    const val adUnitId = "Interstitial_Android"
    var facebookPlacementIdInterstitial = ""
    var fbPlacementIdBanner = ""
    var chartBoostAppID = ""
    var chartBoostAppSig = ""
    var nativeAdmob: String = ""
    var unityGameID = ""
    var startAppId = ""
    var nativeFacebook = ""
    var admobBannerId = ""
    var modeCheckValue = ""
    const val adLocation2topPermanent = "Location2TopPermanent"
    var location2TopPermanentProvider = "none"
    const val rateUsKey = "rateus"
    var rateUsText = ""
    var rateUsDialogValue: Boolean = false
    var rateShown = false
    var nativeAdProvider=""

    //Ad Locations
    const val adMiddle = "Middle"
    const val adTap = "tap"
    const val adBefore = "BeforeVideo"
    const val adAfter = "AfterVideo"
    const val adLocation1 = "Location1"
    const val adLocation2top = "Location2Top"
    const val adLocation2bottom = "Location2Bottom"
    const val nativeAdLocation = "native"
    var location2TopProvider = "none"
    var adLocation1Provider = "none"
    var locationBeforeProvider = "none"

    var location2BottomProvider = "none"
    var locationAfter = "none"
    var isInitAdmobSdk = false
    var isInitFacebookSdk = false
    var isUnitySdkInit = false
    var isChartboostSdkInit = false
    var isStartAppSdkInit = false
    const val preferenceKey = "Message"
    const val preferenceMode = "mode"

    //DefamationClass
    var myUserLock1 = "locked"
    var myUserCheck1 = "myUserCheck1"

    const val key = "nonenFootBall@Key"
    const val salt = "Fit4533op"
    const val mySecretCheckDel: String = "&"
    const val mySecretSize = 16
    var userIp = "userIp"
    const val userBase = "?token="
    const val userBaseDel = "/"
    const val algoTypeS1 = "SHA-1"
    const val algoTypeS2 = "SHA-256"
    const val algoName = "iso-8859-1"

    var splash_status = false
    var app_update_dialog = false

    //Api data
    const val stringId = "6"
    var authToken = ""
    const val buildNo = ""
    var passVal = ""
    var emptyCheck = ""
    const val IpApi = "https://ip-api.streamingucms.com/"
    var baseUrlChannel = ""
    var baseUrlDemo = ""
    var userLinkVal = ""
    var positionClick = -1
    var previousClick = -1
    const val userApi = "get_url"
    const val channelApi = "details"
    const val sepUrl = ".net"
    const val channelId = "id"
    const val channelAuth = "auth_token"
    const val channelBuild = "build_no"
    var passphraseVal = ""
    var channel_url_val = ""

    //playLand
    const val userBaseExtraDel1 = "999"
    const val userBaseExtraDel2 = "%"
    const val userRepAlgo = "[cCITS]"

    //userData
    const val userType1 = "flussonic"
    const val userType2 = "cdn"
    const val userType3 = "p24"
    const val userType4 = "cdnp2p"
    const val phraseDel = "@"
    var userLink = ""
    var defaultString = ""
    //StoneData

    const val chName = "UTF-8"
    const val asp = "AES"
    const val instanceVal = "PBKDF2WithHmacSHA1"
    const val transForm = "AES/CBC/PKCS5Padding"

    //billing
    var removeAds = false
    var oldSku: List<String> = ArrayList()
    var videoFinish = false

    ///RecentFragment
    const val testFormat = "TEST"
    const val odiFormat = "ODI"
    const val t20Format = "T20"
    var selectedSeriesId = 1

    const val userType5 = "app"

    //splash
    var updateScreenStatus = false
    var mailId = "apps.greek@gmail.com"
    var mailText = "Send Email..."

    //userValues
    var cementData = "cementData"
    var cementType = "cementType"
    var cementMainData = "cementMainData"
    var cementMainType = "cementMainType"


    const val appVersionName = BuildConfig.VERSION_NAME
    const val appVersionCode = BuildConfig.VERSION_CODE

}