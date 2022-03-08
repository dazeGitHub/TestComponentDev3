package com.toys.utils;

import com.toys.arouter.ARouter;
import com.toys.arouter.IRouter;
import com.toys.login.LoginActivity;

/**
 * <pre>
 *     author : ZYZ
 *     e-mail : zyz163mail@163.com
 *     time   : 2021/04/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ActivityUtil implements IRouter {

    @Override
    public void addActivity() {
        //大公司的 key 命名是有规范的
        ARouter.getInstance().addActivity("login/login", LoginActivity.class);
    }
}