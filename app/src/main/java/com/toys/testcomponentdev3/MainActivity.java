package com.toys.testcomponentdev3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
@BindPath(key = "main/main")
public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("TAG", "is_application =" + is_application);
    }

    public void jumpLoginActivity(View view) {
        startActivity(new Intent(this, ARouter.getInstance().getActivity("login/login")));
    }
}
