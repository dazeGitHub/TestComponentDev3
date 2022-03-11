package com.toys.common.data.livedata

import kotlin.jvm.Synchronized
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.lang.Exception
import java.lang.NullPointerException
import java.util.HashMap

/**
 * <pre>
 * author : ZYZ
 * e-mail : zyz163mail@163.com
 * time   : 2021/05/10
 * desc   : 管理所有 LiveData 的封装类
 * version: 1.0
</pre> *
 */
class LiveDataBus private constructor() {

    companion object {
        val instance = LiveDataBus()
    }

    //存储所有 LiveData 的容器，LiveData 对象相当于一个通信的管道，如果两个地方单向通信则需要一个 LiveData 对象，但是如果有多个地方单向通信则需要多个 LiveData，所有这里通过 map 来管理所有的 LiveData
    private val map: MutableMap<String, BusMutableLiveData<Any>?>

    init {
        map = HashMap()
    }

    /**
     * 存和取一体的方法
     */
    @Synchronized
    fun <T> with(key: String, type: Class<T>?): BusMutableLiveData<T>? {
        if (!map.containsKey(key)) {
            map[key] = BusMutableLiveData()
        }
        return map[key] as BusMutableLiveData<T>?
    }

    inner class BusMutableLiveData<T> : MutableLiveData<T>() {
        //false : 需要粘性事件(接收到之前的数据)    true : 不需要粘性事件(不接收到之前的数据)
        private var isViscosity = false
        fun observe(owner: LifecycleOwner, isViscosity: Boolean, observer: Observer<T>) {
            this.isViscosity = isViscosity
            observe(owner, observer)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)
            try {
                if (isViscosity) {
                    //通过反射 获取到 LiveData 的 mVersion，获取到 observer 的 mLastVersion，将 mVersion 的值给 mLastVersion，因为是通过反射，所以如果 sdk 有修改，则这里也需要去修改
                    hook(observer)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @Throws(Exception::class)
        private fun hook(observer: Observer<in T>) {
            //获取到 LiveData 的类对象
            val liveDataClass = LiveData::class.java
            //根据类对象获取到 mVersion 的反射对象
            val mVersionField = liveDataClass.getDeclaredField("mVersion")
            //打开权限
            mVersionField.isAccessible = true
            //获取到 mObservers 的反射对象
            val mObserversField = liveDataClass.getDeclaredField("mObservers")
            //打开权限
            mObserversField.isAccessible = true
            //从当前的 liveData 对象中获取 mObservers 这个成员变量在当前对象中的值
            val mObservers = mObserversField[this]
            //获取到 mObservers 这个 map 的 get 方法
            val get = mObservers.javaClass.getDeclaredMethod("get", Any::class.java)
            //打开权限
            get.isAccessible = true
            //执行 get 方法  LiveData 的 mObservers 是 SafeIterableMap 类型的，而 SafeIterableMap 返回的类型是 Entry 类型的
            val invokeEntry = get.invoke(mObservers, observer)
            //定义一个空对象 LifecycleBoundObserver
            var observerWrapper: Any? = null
            if (invokeEntry != null && invokeEntry is Map.Entry<*, *>) {
                observerWrapper = invokeEntry.value //获取到的值都是 LifecycleBoundObserver 类型的
            }
            if (observerWrapper == null) {
                throw NullPointerException("observerWrapper 不能为空")
            }

            //虽然 observerWrapper 是 LifecycleBoundObserver 类型的，但是直接去拿它的 observerWrapper 的 mLastVersion 是拿不到的
            //得到父类 ObserverWrapper 的类对象
            val aClass: Class<*> = observerWrapper.javaClass.superclass
            //获取 mLastVersion 的发射对象
            val mLastVersionField = aClass.getDeclaredField("mLastVersion")
            //打开权限
            mLastVersionField.isAccessible = true

            //获取到 mVersion 的值
            val o = mVersionField[this]
            //把它的值赋值给 mLastVersion
            mLastVersionField[observerWrapper] = o
        }
    }
}