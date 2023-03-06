package com.toys.common.data.constant

import com.google.gson.Gson
//import com.toys.common.data.imp.IJsonTransferImp

object Constants {
    val GSON = Gson()
//    val JSON_TRANS_IMP = IJsonTransferImp()

//    const val ROUTER_SCHEME = "dm://"
    
    // 路由必须以 / 开头, 如果使用 dm:// 就会报错
    // Caused by: com.alibaba.android.arouter.exception.HandlerException: ARouter::Extract the default group failed,
    // the path must be start with '/' and contain more than 2 '/'!

    object RouterPath {
        const val GROUP = ""

        const val MAIN = "/${GROUP}/main"
        const val LOGIN = "/${GROUP}/login"
        const val MEMBER = "/${GROUP}/member"

        const val LOGIN_TEST_PATH_NO_PARAM = "$LOGIN"
        const val LOGIN_TEST_PATH = "$LOGIN?age=25&username=zhangsan&user={\"username\":\"lisi\", \"age\":30}"
        const val MEMBER_TEST_PATH = "$MEMBER"
    }
}