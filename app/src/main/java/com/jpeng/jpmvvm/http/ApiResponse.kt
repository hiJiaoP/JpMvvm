package com.jpeng.jpmvvm.http

/**
 * @ClassName: ApiResponse
 * @Description: 区分哪种网络状态返回的数据
 * @Author: jiaop
 * @Date: 2020/5/10
 */

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val throwable: Throwable) : ApiResponse<T>()

sealed class ApiResponse<T> {

    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error)
        }

        fun <T> create(body: T?): ApiResponse<T> {
            return if (body == null) {
                ApiEmptyResponse()
            } else {
                ApiSuccessResponse(body)
            }
        }
    }

}

