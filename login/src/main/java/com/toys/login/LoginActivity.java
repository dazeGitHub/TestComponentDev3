package com.toys.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
@BindPath(key = "login/login")
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void jumpMemberActivity(View view) {
        startActivity(new Intent(this, ARouter.getInstance().getActivity("member/member")));
    }
}
