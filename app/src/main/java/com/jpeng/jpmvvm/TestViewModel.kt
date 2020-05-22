package com.jpeng.jpmvvm

import androidx.lifecycle.viewModelScope
import com.jpeng.jpmvvm.http.BaseViewModel
import com.jpeng.jpmvvm.http.addSource
import com.jpeng.jpmvvm.http.simpleRequestLiveData

/**
 * @ClassName: TestViewModel
 * @Description:
 * @Author: jiaop
 * @Date: 2020/5/10
 */
class TestViewModel : BaseViewModel<TestBean, TestApi>(TestApi::class.java) {

    override fun executeBusiness() {
        val liveData = viewModelScope.simpleRequestLiveData<TestBean> {
            api {
                mApi.getNews()
            }
        }
        mLiveData.addSource(liveData)
    }

}