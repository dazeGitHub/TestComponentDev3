package com.toys.testcomponentdev3

import com.alibaba.android.arouter.launcher.ARouter
import com.toys.base.BaseApplication
//import com.zyz.xrouter.XRouter

/**
 * <pre>
 * author : ZYZ
 * e-mail : zyz163mail@163.com
 * time   : 2021/04/30
 * desc   :
 * version: 1.0
</pre> *
 */
class MyApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {   // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        //通过反射获取工具类 ActivityUtil 然后去执行它们
//        XRouter.getInstance().init(this)
    }
}