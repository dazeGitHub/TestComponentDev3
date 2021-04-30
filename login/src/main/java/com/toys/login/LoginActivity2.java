package com.toys.login;

import android.content.Intent;
import android.os.Bundle;

import com.toys.annotation.BindPath;
import com.toys.arouter.ARouter;
import com.toys.base.BaseActivity;

/**
 * <pre>
 *     author : ZYZ
 *     e-mail : zyz163mail@163.com
 *     time   : 2021/04/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@BindPath(key = "login2/login2")
public class LoginActivity2 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Class member = ARouter.getInstance().getActivity("member");
        if(member != null){
            startActivity(new Intent(this, member));
        }
    }
}
