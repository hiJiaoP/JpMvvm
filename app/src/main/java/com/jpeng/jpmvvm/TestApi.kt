package com.jpeng.jpmvvm

import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * @ClassName: TestApi
 * @Description:
 * @Author: jiaop
 * @Date: 2020/5/10
 */
interface TestApi {

    @Headers("Cache-Control:public ,max-age=60")
    @GET("/getWangYiNews")
    suspend fun getNews(): TestBean

}