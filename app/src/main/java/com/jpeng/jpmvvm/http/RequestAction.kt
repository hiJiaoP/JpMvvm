package com.jpeng.jpmvvm.http

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope

/**
 * @ClassName: RequestAction
 * @Description: DSL 包装所需方法
 * @Author: jiaop
 * @Date: 2020/5/10
 */
open class RequestAction<ResponseType, ResultType> {

    var api: (suspend () -> ResponseType)? = null

    @Suppress("UNCHECKED_CAST")
    var transformer: ((ResponseType?) -> ResultType?)? = {
        it as? ResultType
    }
        private set

    fun api(block: suspend () -> ResponseType) {
        this.api = block
    }

    // 将网络数据类型，转换为需要的数据类型
    fun transformer(block: (ResponseType?) -> ResultType?) {
        this.transformer = block
    }

}

/**
 * DSL网络请求
 * 针对 网络返回的数据类型 和 我们需要的数据类型是一致的情况
 */
inline fun <ResultType> CoroutineScope.simpleRequestLiveData(
    dsl: RequestAction<ResultType, ResultType>.() -> Unit
): LiveData<ResultData<ResultType>> {
    return requestLiveData(dsl)
}

/**
 * DSL网络请求
 *
 * ResponseType 为网络返回的数据类型
 * ResultType 为我们需要的数据类型
 *
 */
inline fun <ResponseType, ResultType> CoroutineScope.requestLiveData(
    dsl: RequestAction<ResponseType, ResultType>.() -> Unit
): LiveData<ResultData<ResultType>> {
    val action = RequestAction<ResponseType, ResultType>().apply(dsl)
    return liveData(this.coroutineContext) {

        // 通知网络请求开始
        emit(ResultData.start<ResultType>())

        val apiResponse = try {
            // 获取网络请求数据
            val resultBean = action.api?.invoke()
            ApiResponse.create<ResponseType>(resultBean)
        } catch (e: Throwable) {
            ApiResponse.create<ResponseType>(e)
        }

        // 根据 ApiResponse 类型，处理相对事务
        val result = when (apiResponse) {
            is ApiEmptyResponse -> {
                null
            }
            is ApiSuccessResponse -> {
                val result = action.transformer?.invoke(apiResponse.body)
                result.apply {
                    emit(
                        ResultData.success<ResultType>(
                            this,
                            false
                        )
                    )
                }
            }
            is ApiErrorResponse -> {
                emit(
                    ResultData.error<ResultType>(
                        apiResponse.throwable
                    )
                )
                null
            }
        }

        emit(ResultData.complete<ResultType>(result))
    }
}