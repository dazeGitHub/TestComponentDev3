package com.toys.testcomponentdev3

import com.toys.base.BaseActivity
import androidx.lifecycle.MutableLiveData
import android.os.Bundle
import android.util.Log
import android.view.View
import com.toys.base.BaseApplication
import com.toys.common.data.constant.Constants
import com.toys.common.data.livedata.LiveDataBus
import com.toys.common.utils.TLog
import com.zyz.annotation.Route
import com.zyz.xrouter.XRouter

/**
 * <pre>
 * author : ZYZ
 * e-mail : zyz163mail@163.com
 * time   : 2021/04/30
 * desc   :
 * version: 1.0
</pre> *
 */
@Route(key = Constants.RouterPath.MAIN)
class MainActivity : BaseActivity() {
    var liveData: MutableLiveData<String>? = null

    //方式一: 先使用 liveData.postValue() 发送值，再注册观察者
    //    @Override
    //    protected void onCreate(Bundle savedInstanceState) {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_main);
    //
    //        TLog.e("TAG", "is_application =" + is_application);
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
    //                TLog.e("TAG", "onChanged content = " + content + " ThreadName = " + Thread.currentThread().getName());
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
    //        TLog.e("TAG", "is_application =" + is_application);
    //
    ////        liveData = new MutableLiveData<>();
    //        liveData = LiveDataBus.getInstance().with("app",String.class);
    //        liveData.observe(MainActivity.this, new Observer<String>() {
    //            @Override
    //            public void onChanged(String content) {
    //                TLog.e("TAG", "Observer1 onChanged content = " + content + " ThreadName = " + Thread.currentThread().getName());
    //                //Observer1 onChanged content = testData2_postValue ThreadName = main
    //            }
    //        });
    //
    //        liveData.observe(MainActivity.this, new Observer<String>() {
    //            @Override
    //            public void onChanged(String content) {
    //                TLog.e("TAG", "Observer2 onChanged content = " + content + " ThreadName = " + Thread.currentThread().getName());
    //                //Observer2 onChanged content = testData2_postValue ThreadName = main
    //            }
    //        });
    //
    ////        MainActivity.this.getLifecycle().addObserver(new MyObserver());
    //    }
    fun jumpLoginActivity(view: View?) {
//        liveData.postValue("testData2_postValue");
//        startActivity(Intent(this, XRouter.getInstance().getActivity("login/login")))
        XRouter.getInstance().jumpActivity(this,  url = Constants.RouterPath.LOGIN_TEST_PATH,scheme = null)
    }

    //该 GenericLifecycleObserver 接口可以在 Activity 生命周期发生变化时回调
    //    @SuppressLint("RestrictedApi")
    //    class MyObserver implements GenericLifecycleObserver {
    //
    //        @Override
    //        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
    //            TLog.e("TAG", "MyObserver onStateChanged " + event.name());
    //
    //            //MyObserver onStateChanged ON_CREATE
    //            //MyObserver onStateChanged ON_START
    //            //MyObserver onStateChanged ON_RESUME
    //        }
    //    }
    //方式三 : 在 LoginActivity 注册 Observer，在当前 Activity 发送 value
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TLog.e("TAG", "is_application =${BaseApplication.is_application}")

//        liveData = new MutableLiveData<>();
        liveData = LiveDataBus.instance.with("app", String::class.java)
        liveData?.value = "MainActivity_postValue_likeIntent"

//        MainActivity.this.getLifecycle().addObserver(new MyObserver());
    }
}