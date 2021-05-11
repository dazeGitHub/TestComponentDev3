package com.toys.testcomponentdev3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.toys.annotation.BindPath;
import com.toys.arouter.ARouter;
import com.toys.base.BaseActivity;
import com.toys.common.LiveDataBus;

/**
 * <pre>
 *     author : ZYZ
 *     e-mail : zyz163mail@163.com
 *     time   : 2021/04/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@BindPath(key = "main/main")
public class MainActivity extends BaseActivity {

    MutableLiveData<String> liveData;

    //方式一: 先使用 liveData.postValue() 发送值，再注册观察者

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Log.e("TAG", "is_application =" + is_application);
//
//        liveData = new MutableLiveData<>();
////        liveData.setValue("testData_setValue"); //
////        liveData.postValue("testData2_postValue");
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
////             liveData.setValue("testData_setValue_thread");
//             liveData.postValue("testData_postValue_thread");
//            }
//        }).start();
//    }
//
//    public void jumpLoginActivity(View view) {
//        //只有 AppCompatActivity 才实现了 LifeCycleOwner 接口，所以只有当前 Activity 继承自 AppCompatActivity 才可以传入 observe() 中做为实参，而传递给 liveData 的 Observer 能感知生命周期就是通过实现了 LifeCycleOwner 接口的 Activity
//        liveData.observe(MainActivity.this, new Observer<String>() {
//            @Override
//            public void onChanged(String content) {
//                Log.e("TAG", "onChanged content = " + content + " ThreadName = " + Thread.currentThread().getName());
//
//                //在主线程中 setValue() 和 postValue() 打印结果是一样的 :
//                //onChanged content = testData_setValue ThreadName = main
//                //onChanged content = testData2_postValue ThreadName = main
//
//                //在子线程中 setValue() 会抛异常
//                //java.lang.IllegalStateException: Cannot invoke setValue on a background thread
//                //onChanged content = testData_postValue_thread ThreadName = main
//            }
//        });
////      startActivity(new Intent(this, ARouter.getInstance().getActivity("login/login")));
//    }

    //方式二: 先注册观察者，再使用 liveData.postValue() 发送值，也是可以的

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Log.e("TAG", "is_application =" + is_application);
//
////        liveData = new MutableLiveData<>();
//        liveData = LiveDataBus.getInstance().with("app",String.class);
//        liveData.observe(MainActivity.this, new Observer<String>() {
//            @Override
//            public void onChanged(String content) {
//                Log.e("TAG", "Observer1 onChanged content = " + content + " ThreadName = " + Thread.currentThread().getName());
//                //Observer1 onChanged content = testData2_postValue ThreadName = main
//            }
//        });
//
//        liveData.observe(MainActivity.this, new Observer<String>() {
//            @Override
//            public void onChanged(String content) {
//                Log.e("TAG", "Observer2 onChanged content = " + content + " ThreadName = " + Thread.currentThread().getName());
//                //Observer2 onChanged content = testData2_postValue ThreadName = main
//            }
//        });
//
////        MainActivity.this.getLifecycle().addObserver(new MyObserver());
//    }

    public void jumpLoginActivity(View view) {
//        liveData.postValue("testData2_postValue");
        startActivity(new Intent(this, ARouter.getInstance().getActivity("login/login")));
    }

    //该 GenericLifecycleObserver 接口可以在 Activity 生命周期发生变化时回调
//    @SuppressLint("RestrictedApi")
//    class MyObserver implements GenericLifecycleObserver {
//
//        @Override
//        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
//            Log.e("TAG", "MyObserver onStateChanged " + event.name());
//
//            //MyObserver onStateChanged ON_CREATE
//            //MyObserver onStateChanged ON_START
//            //MyObserver onStateChanged ON_RESUME
//        }
//    }

    //方式三 : 在 LoginActivity 注册 Observer，在当前 Activity 发送 value
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("TAG", "is_application =" + is_application);

//        liveData = new MutableLiveData<>();
        liveData = LiveDataBus.getInstance().with("app",String.class);
        liveData.setValue("MainActivity_postValue_likeIntent");

//        MainActivity.this.getLifecycle().addObserver(new MyObserver());
    }

}
