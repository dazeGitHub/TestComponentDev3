package com.toys.base;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public abstract class BaseActivity extends AppCompatActivity {

    //得到了在 config.gradle 中的 is_application 变量
    public boolean is_application = BuildConfig.is_application;
}