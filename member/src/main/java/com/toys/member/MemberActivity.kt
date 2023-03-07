package com.toys.member

import com.toys.base.BaseActivity
import android.os.Bundle
import com.toys.common.data.constant.Constants
import com.zyz.annotation.Route

/**
 * <pre>
 * author : ZYZ
 * e-mail : zyz163mail@163.com
 * time   : 2021/04/30
 * desc   :
 * version: 1.0
</pre> *
 */
@Route(key = Constants.RouterPath.MEMBER)
class MemberActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)
    }
}