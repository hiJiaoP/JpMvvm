package com.jpeng.jpmvvm.http

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * @ClassName: LiveDataExt
 * @Description: 扩展LiveData函数
 * @Author: jiaop
 * @Date: 2020/5/10
 */
fun <T> MediatorLiveData<ResultData<T>>.addSource(liveData: LiveData<ResultData<T>>) {
    this.addSource(liveData) {
        if (it.requestStatus == RequestStatus.COMPLETE) {
            this.removeSource(liveData)
        }
        this.value = it
    }
}