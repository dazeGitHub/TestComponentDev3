package com.toys.common.bean

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.SerializationService
import com.google.gson.Gson
import com.toys.common.data.constant.Constants
import java.lang.reflect.Type

data class User(val username:String, val age: Int)

@Route(path = "${Constants.RouterPath.GROUP}/json")
data class JsonServiceImpl (val username:String, val age: Int) : SerializationService {
    override fun init(context: Context?) {

    }

    override fun <T : Any?> json2Object(input: String?, clazz: Class<T>?): T {
        return Gson().fromJson(input, clazz)
    }

    override fun object2Json(instance: Any?): String {
        return Gson().toJson(instance)
    }

    override fun <T : Any?> parseObject(input: String?, clazz: Type?): T {
        return Gson().fromJson(input, clazz)
    }
}