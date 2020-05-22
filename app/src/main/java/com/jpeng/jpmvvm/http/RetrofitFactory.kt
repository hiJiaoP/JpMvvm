package com.jpeng.jpmvvm.http

import android.util.Log
import com.google.gson.Gson
import com.jpeng.jpmvvm.MyApp
import com.jpeng.jpmvvm.NetUtils
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * retrofit封装类
 */
class RetrofitFactory private constructor() {

    private val retrofit: Retrofit

    init {
        val gson = Gson().newBuilder()
            .setLenient()
            .serializeNulls()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl("https://api.apiopen.top")
            .client(initClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    companion object {
        val instance: RetrofitFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitFactory()
        }
    }

    private fun initClient() = OkHttpClient.Builder()
        .apply {
            connectTimeout(5, TimeUnit.SECONDS)
            readTimeout(5, TimeUnit.SECONDS)
            addInterceptor(initInterceptor())
            cache(
                Cache(
                    File(MyApp.instance().externalCacheDir.toString(), "cache"),
                    10 * 1024 * 1024
                )
            )
            addNetworkInterceptor(
                Interceptor { chain ->
                    var request = chain.request()
                    var response = chain.proceed(request)
                    if (NetUtils(MyApp.instance()).NETWORK_ENABLE) {
                        response.newBuilder()
                            .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .header("Cache-Control", request.cacheControl.toString())
                            .build()
                    }
                    response
                }
            )
//            sslSocketFactory(this, "https的crt文件assets下的证书路径，例如：cers/FiddlerRoot.cer")
        }
        .build()

    private fun initInterceptor() = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.i("RetrofitFactory", message)
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY)

    fun <T> getService(service: Class<T>): T {
        return retrofit.create(service)
    }

}