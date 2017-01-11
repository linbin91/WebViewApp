package com.felink.webviewapp.base;

import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by linbin_dian91 on 2016/3/18.
 */
public class BasePresenterImpl<T extends  BaseView,V> extends Callback<V> implements BasePresenter{

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
    public V parseNetworkResponse(Response response) throws Exception {
        return null;
    }

    @Override
    public void onError(Call call, Exception e) {

    }

    @Override
    public void onResponse(V response) {

    }
}
