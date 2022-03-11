package com.toys.login

import com.toys.base.BaseActivity
import com.toys.common.data.livedata.LiveDataBus.BusMutableLiveData
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.toys.common.bean.User
import com.toys.common.data.constant.Constants
import com.toys.common.data.livedata.LiveDataBus
import com.toys.common.utils.TLog
import com.zyz.annotation.Autowired
import com.zyz.annotation.Route
import com.zyz.xrouter.XRouter
import com.zyz.xrouter.XRouterKnife

/**
 * <pre>
 * author : ZYZ
 * e-mail : zyz163mail@163.com
 * time   : 2021/04/30
 * desc   :
 * version: 1.0
</pre> *
 */
@Route(key = Constants.RouterPath.LOGIN)
class LoginActivity : BaseActivity() {

    @Autowired("age")
    var age: Int? = null
    @Autowired(value = "username")
    var userNameStr: String? = null
    @Autowired(value = "user")
    var userObj: User? = null

    private var appLiveDataObj: BusMutableLiveData<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        XRouterKnife.bind(this, Constants.JSON_TRANS_IMP)

        "LoginActivity age = $age userNameStr = $userNameStr userObj = $userObj".let{
            TLog.e(TAG,it)
            findViewById<TextView>(R.id.tv_receive_msg).text = it
        }

        appLiveDataObj = LiveDataBus.instance.with("app", String::class.java)

        //模拟 intent，MainActivity 中发送 value，LoginActivity 中接收 value
        appLiveDataObj?.observe(this@LoginActivity, true, Observer { content ->
            TLog.e(
                "TAG",
                "LoginActivity Observer onChanged content = " + content + " ThreadName = " + Thread.currentThread().name
            )
            //LoginActivity Observer onChanged content = testData2_postValue ThreadName = main
            //LoginActivity Observer onChanged content = MainActivity_postValue_likeIntent ThreadName = main
        })
    }

    fun jumpMemberActivity(view: View?) {
//        Class member = ARouter.getInstance().getActivity("member/member");
//        if(member != null){
//            startActivity(new Intent(this, member));
//        }

        //点击按钮时向 app 模块的 MainActivity 里的 LiveData 对象发送一个消息，每个观察者在接收时不管之前发送了多少消息，只会接收到发送的最后一条消息 (即 `Post Event From LoginActivity3`)
        //而且每个消息只会接收一次，即使再次调用 postValue() 发送的值是一样的，也不是同一个消息
//        appLiveDataObj!!.postValue("Post Event From LoginActivity1")
//        appLiveDataObj!!.postValue("Post Event From LoginActivity2")
//        appLiveDataObj!!.postValue("Post Event From LoginActivity3")

        XRouter.getInstance().jumpActivity(this, Constants.RouterPath.MEMBER_TEST_PATH)
    }
}