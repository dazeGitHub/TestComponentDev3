package com.toys.login

import com.toys.base.BaseActivity
import android.os.Bundle
import com.toys.login.R
import android.content.Intent
import com.toys.base.BaseApplication
import com.zyz.annotation.Route
import com.zyz.xrouter.XRouter
//import login.LoginApplication

/**
 * <pre>
 * author : ZYZ
 * e-mail : zyz163mail@163.com
 * time   : 2021/04/30
 * desc   :
 * version: 1.0
</pre> *
 */
@Route(key = "login2/login2")
class LoginActivity2 : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(BaseApplication.is_application){
//            LoginApplication.mTestVar     //这么干不行, 如果 login 模块不是 Application 的状态时, 就会找不到 LoginApplication 这个类
        }

        val member: Class<*>? = XRouter.getInstance().getActivity("member/member")
        if (member != null) {
            startActivity(Intent(this, member))
        }
    }
}