package com.jpeng.jpmvvm

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


/**
 * @ClassName: NetUtils
 * @Description:
 * @Author: jiaop
 * @Date: 2020/5/13
 */
class NetUtils(private val context: Context) {

    /**
     * 当前网络是否可用
     */
    val NETWORK_ENABLE: Boolean
        get() {
            val connManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connManager.activeNetworkInfo
            return info.state == NetworkInfo.State.CONNECTED
        }

}