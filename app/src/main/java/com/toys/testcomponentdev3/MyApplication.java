package com.toys.testcomponentdev3;

import android.app.Application;

import com.toys.arouter.ARouter;

/**
 * <pre>
 *     author : ZYZ
 *     e-mail : zyz163mail@163.com
 *     time   : 2021/04/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //通过反射获取工具类 ActivityUtil 然后去执行它们
        ARouter.getInstance().init(this);
    }
}
