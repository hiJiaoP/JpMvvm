package com.jpeng.jpmvvm.http

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

/**
 * @ClassName: BaseViewModel
 * @Description:
 * @Author: jiaop
 * @Date: 2020/5/10
 */
abstract class BaseViewModel<T, E>(
    service: Class<E>
) : ViewModel(), IBaseViewModel {

    val mApi = RetrofitFactory.instance.getService(service)

    val mLiveData = MediatorLiveData<ResultData<T>>()

}

interface IBaseViewModel {
    fun executeBusiness()
}
