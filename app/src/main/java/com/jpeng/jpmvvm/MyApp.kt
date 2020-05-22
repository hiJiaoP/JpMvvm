package com.jpeng.jpmvvm

import android.app.Application
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @ClassName: MyApp
 * @Description:
 * @Author: jiaop
 * @Date: 2020/5/11
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    //自定义一个非空且只能一次性赋值的委托属性
    companion object {
        private var instance: MyApp by NotNullSingleValueVar()

        fun instance() = instance
    }

    //定义一个属性管理类，进行非空和重复赋值的判断
    private class NotNullSingleValueVar<T>() : ReadWriteProperty<Any?, T> {
        private var value: T? = null
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value ?: throw IllegalStateException("application not initialized")
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = if (this.value == null) value
            else throw IllegalStateException("application already initialized")
        }
    }


}