package com.jpeng.jpmvvm

import com.google.gson.annotations.SerializedName

/**
 * @ClassName: TestBean
 * @Description:
 * @Author: jiaop
 * @Date: 2020/5/10
 */
data class TestBean(
    @SerializedName("code")
    val code: Int = 0, // 200
    @SerializedName("message")
    val message: String = "", // 成功!
    @SerializedName("result")
    val result: List<Result> = listOf()
) {

    data class Result(
        @SerializedName("image")
        val image: String = "", // http://cms-bucket.ws.126.net/2020/0422/02f9aad4p00q961tg00o5c000s600e3c.png?imageView&thumbnail=140y88&quality=85
        @SerializedName("passtime")
        val passtime: String = "", // 2020-04-22 10:00:33
        @SerializedName("path")
        val path: String = "", // https://news.163.com/20/0422/09/FAQCS9NS0001899O.html
        @SerializedName("title")
        val title: String = "" // 专家:疫情爆发后湖北官员水平比西方领导人平均数高
    )

}