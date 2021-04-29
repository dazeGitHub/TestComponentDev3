package com.toys.arouter

import android.app.Activity

/**
 * <pre>
 * author : ZYZ
 * e-mail : zyz163mail@163.com
 * time   : 2021/04/29
 * desc   : 管理装载所有 Activity.class 的容器
 * version: 1.0
</pre> *
 */
class ARouter private constructor() {

    //装载 Activity 的容器也叫路由表
    private val map: MutableMap<String, Class<out Activity?>?>? = HashMap()

    /**
     * 将 Activity 的类对象加入到 map 中
     * @param key
     * @param clazz
     */
    fun addActivity(key: String?, clazz: Class<out Activity?>?) {
        if (key != null && clazz != null && !map!!.containsKey(key)) {
            map[key] = clazz
        }
    }

    /**
     * 获取指定的 Activity 的 class
     * @param key
     * @return
     */
    fun getActivity(key: String?): Class<*>? {
        return if (key != null && map!!.containsKey(key)) {
            map[key]
        } else null
    }

    companion object {
        var instance = ARouter()
    }
}