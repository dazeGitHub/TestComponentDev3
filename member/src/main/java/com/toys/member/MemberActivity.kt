package com.toys.member

import com.toys.base.BaseActivity
import android.os.Bundle
import com.toys.common.Constants
import com.toys.member.R
import com.zyz.annotation.BindPath

/**
 * <pre>
 * author : ZYZ
 * e-mail : zyz163mail@163.com
 * time   : 2021/04/30
 * desc   :
 * version: 1.0
</pre> *
 */
@BindPath(key = Constants.RouterPath.MEMBER)
class MemberActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)
    }
}