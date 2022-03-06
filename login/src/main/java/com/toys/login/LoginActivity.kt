package com.toys.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.toys.annotation.BindPath;
import com.toys.base.BaseActivity;
import com.toys.common.LiveDataBus;

/**
 * <pre>
 *     author : ZYZ
 *     e-mail : zyz163mail@163.com
 *     time   : 2021/04/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@BindPath(key = "login/login")
public class LoginActivity extends BaseActivity {

    private LiveDataBus.BusMutableLiveData<String> appLiveDataObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appLiveDataObj = LiveDataBus.getInstance().with("app", String.class);

        //模拟 intent，MainActivity 中发送 value，LoginActivity 中接收 value
        appLiveDataObj.observe(LoginActivity.this, true, new Observer<String>() {
            @Override
            public void onChanged(String content) {
                Log.e("TAG", "LoginActivity Observer onChanged content = " + content + " ThreadName = " + Thread.currentThread().getName());
                //LoginActivity Observer onChanged content = testData2_postValue ThreadName = main
                //LoginActivity Observer onChanged content = MainActivity_postValue_likeIntent ThreadName = main
            }
        });
    }

    public void jumpMemberActivity(View view) {
//        Class member = ARouter.getInstance().getActivity("member/member");
//        if(member != null){
//            startActivity(new Intent(this, member));
//        }

        //点击按钮时向 app 模块的 MainActivity 里的 LiveData 对象发送一个消息，每个观察者在接收时不管之前发送了多少消息，只会接收到发送的最后一条消息 (即 `Post Event From LoginActivity3`)
        //而且每个消息只会接收一次，即使再次调用 postValue() 发送的值是一样的，也不是同一个消息
        appLiveDataObj.postValue("Post Event From LoginActivity1");
        appLiveDataObj.postValue("Post Event From LoginActivity2");
        appLiveDataObj.postValue("Post Event From LoginActivity3");
    }
}
