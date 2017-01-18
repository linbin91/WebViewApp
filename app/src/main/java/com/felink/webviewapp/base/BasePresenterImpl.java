package com.felink.webviewapp.base;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by linbin_dian91 on 2016/3/18.
 */
public class BasePresenterImpl<T extends  BaseView> extends Callback<String> implements BasePresenter{

    protected WeakReference<T> mViewRef;

    public  BasePresenterImpl(T view){
        mViewRef = new WeakReference<T>(view);
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onDestroy() {
        if (mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }


    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        String content = response.body().string();
        return content;
    }

    @Override
    public void onError(Call call, Exception e) {
        Log.e("linbin","Exception ="+ e.toString());
    }

    @Override
    public void onResponse(String response) {
    }

    public  <V> ArrayList<V> jsonToArrayList(String json, Class<V> clazz)
    {
        Type type = new TypeToken<ArrayList<JsonObject>>()
        {}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<V> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects)
        {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }
}
