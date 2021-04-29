package com.toys.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.toys.base.BaseActivity
import java.lang.Exception

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var memberClass : Class<*>? = null
        try{
            memberClass  = classLoader.loadClass("com.toys.member.MemberActivity")
        }catch (e: Exception){
            e.printStackTrace()
        }

        if(is_application){

        }
    }
}
