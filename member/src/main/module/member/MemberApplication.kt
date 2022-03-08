package member

import android.app.Application
import com.toys.base.BaseApplication
import com.zyz.xrouter.XRouter

/**
 * <pre>
 * author : ZYZ
 * e-mail : zyz163mail@163.com
 * time   : 2021/04/29
 * desc   :
 * version: 1.0
</pre> *
 */
class MemberApplication : BaseApplication() {
    companion object{
        var mMemberTestVar = "memeber"
    }

    override fun onCreate() {
        super.onCreate()
        //通过反射获取工具类 ActivityUtil 然后去执行它们
        XRouter.getInstance().init(this)
    }
}