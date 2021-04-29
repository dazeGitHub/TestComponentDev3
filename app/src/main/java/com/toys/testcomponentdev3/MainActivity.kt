package com.toys.testcomponentdev3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.toys.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("TAG", "is_application = $is_application")
    }
}