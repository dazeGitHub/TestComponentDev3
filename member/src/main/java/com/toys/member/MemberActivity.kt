package com.toys.member

import com.toys.annotation.BindPath
import com.toys.base.BaseActivity
import android.os.Bundle
import com.toys.member.R

/**
 * <pre>
 * author : ZYZ
 * e-mail : zyz163mail@163.com
 * time   : 2021/04/30
 * desc   :
 * version: 1.0
</pre> *
 */
@BindPath(key = "member/member")
class MemberActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)
    }
}