package com.dream.live.cricket.score.hd.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log

object InternetUtil {

    fun isInternetOn(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager?.activeNetwork
            val networkCapabilities = connectivityManager?.getNetworkCapabilities(network)
            networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            val networkInfo = connectivityManager?.activeNetworkInfo
            networkInfo?.isConnectedOrConnecting == true
        }
    }
    fun isPrivateDnsSetup(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val linkProperties: LinkProperties? =
                connectivityManager.getLinkProperties(connectivityManager.activeNetwork)

            if (linkProperties?.isPrivateDnsActive == true) {
                Log.d("PrivatednsServer", "server" + linkProperties?.privateDnsServerName)
                if (linkProperties.privateDnsServerName != null) {
                    val listPrivateDns = listOf("adguard", "nextdns", "rethinkdns", "controld")
                    if (linkProperties?.privateDnsServerName?.containsAnyOfIgnoreCase(listPrivateDns) == true) {

                        return true
                    } else {
                        return false
                    }
                } else {
                    return false
                }
            } else {
                return false
                Log.d("PrivatednsServer", "server" + linkProperties?.privateDnsServerName)
            }
        } else {
            return false
        }
    }
    fun String.containsAnyOfIgnoreCase(keywords: List<String>): Boolean {
        for (keyword in keywords) {
            if (this.contains(keyword, true)) return true
        }
        return false
    }
}