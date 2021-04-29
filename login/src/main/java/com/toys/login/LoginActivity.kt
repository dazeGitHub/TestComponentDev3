package com.toys.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.toys.arouter.ARouter
import com.toys.base.BaseActivity
import java.lang.Exception

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var member : Class<*>? = ARouter.instance.getActivity("member")
        member?.let{
            startActivity(Intent(this@LoginActivity, member))
        }

        if(is_application){

        }
    }
}
