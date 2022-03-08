package com.toys.common;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     author : ZYZ
 *     e-mail : zyz163mail@163.com
 *     time   : 2021/05/10
 *     desc   : 管理所有 LiveData 的封装类
 *     version: 1.0
 * </pre>
 */
public class LiveDataBus {

    private static LiveDataBus liveDataBus = new LiveDataBus();

    //存储所有 LiveData 的容器，LiveData 对象相当于一个通信的管道，如果两个地方单向通信则需要一个 LiveData 对象，但是如果有多个地方单向通信则需要多个 LiveData，所有这里通过 map 来管理所有的 LiveData
    private Map<String, BusMutableLiveData<Object>> map;

    private LiveDataBus() {
        map = new HashMap<>();
    }

    public static LiveDataBus getInstance() {
        return liveDataBus;
    }

    /**
     * 存和取一体的方法
     */
    public synchronized <T> BusMutableLiveData<T> with(String key, Class<T> type) {
        if (!map.containsKey(key)) {
            map.put(key, new BusMutableLiveData<Object>());
        }
        return (BusMutableLiveData<T>) map.get(key);
    }

    public class BusMutableLiveData<T> extends MutableLiveData<T> {
        //false : 需要粘性事件(接收到之前的数据)    true : 不需要粘性事件(不接收到之前的数据)
        private boolean isViscosity = false;

        public void observe(@NonNull LifecycleOwner owner, boolean isViscosity, @NonNull Observer<T> observer) {
            this.isViscosity = isViscosity;
            observe(owner, observer);
        }

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
            super.observe(owner, observer);
            try {
                if (isViscosity) {
                    //通过反射 获取到 LiveData 的 mVersion，获取到 observer 的 mLastVersion，将 mVersion 的值给 mLastVersion，因为是通过反射，所以如果 sdk 有修改，则这里也需要去修改
                    hook(observer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void hook(Observer<? super T> observer) throws Exception {
            //获取到 LiveData 的类对象
            Class<LiveData> liveDataClass = LiveData.class;
            //根据类对象获取到 mVersion 的反射对象
            Field mVersionField = liveDataClass.getDeclaredField("mVersion");
            //打开权限
            mVersionField.setAccessible(true);
            //获取到 mObservers 的反射对象
            Field mObserversField = liveDataClass.getDeclaredField("mObservers");
            //打开权限
            mObserversField.setAccessible(true);
            //从当前的 liveData 对象中获取 mObservers 这个成员变量在当前对象中的值
            Object mObservers = mObserversField.get(this);
            //获取到 mObservers 这个 map 的 get 方法
            Method get = mObservers.getClass().getDeclaredMethod("get", Object.class);
            //打开权限
            get.setAccessible(true);
            //执行 get 方法  LiveData 的 mObservers 是 SafeIterableMap 类型的，而 SafeIterableMap 返回的类型是 Entry 类型的
            Object invokeEntry = get.invoke(mObservers, observer);
            //定义一个空对象 LifecycleBoundObserver
            Object observerWrapper = null;
            if (invokeEntry != null && invokeEntry instanceof Map.Entry) {
                observerWrapper = ((Map.Entry) invokeEntry).getValue(); //获取到的值都是 LifecycleBoundObserver 类型的
            }
            if (observerWrapper == null) {
                throw  new NullPointerException("observerWrapper 不能为空");
            }

            //虽然 observerWrapper 是 LifecycleBoundObserver 类型的，但是直接去拿它的 observerWrapper 的 mLastVersion 是拿不到的
            //得到父类 ObserverWrapper 的类对象
            Class<?> aClass = observerWrapper.getClass().getSuperclass();
            //获取 mLastVersion 的发射对象
            Field mLastVersionField = aClass.getDeclaredField("mLastVersion");
            //打开权限
            mLastVersionField.setAccessible(true);

            //获取到 mVersion 的值
            Object o = mVersionField.get(this);
            //把它的值赋值给 mLastVersion
            mLastVersionField.set(observerWrapper, o);
        }
    }
}
