package com.example.peng.myshoppingcar.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.security.auth.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

public abstract class AbstractUiCallBack<T> implements Callback,okhttp3.Callback {
    public abstract void success(T t);
    public abstract void fail(Exception e);
    private Handler handler = null;
    private Class clazz;
    public AbstractUiCallBack(){
        handler = new Handler(Looper.getMainLooper());
        Type type = getClass().getGenericSuperclass();
        Type[] arr = ((ParameterizedType)type).getActualTypeArguments();
        clazz = (Class) arr[0];    }

    public void onFailure(Call call, IOException e) {
        fail(e);
    }

    public void onResponse(Call call, Response response) throws IOException {
        try {
            String result = response.body().string();
            Gson gson = new Gson();
            final  T t = (T) gson.fromJson(result,clazz);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    success(t);
                    //成功的回调出去
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            fail(e);//失败的回调
        }
    }

    public void failed(IOException e) {
        fail(e);
    }
}
