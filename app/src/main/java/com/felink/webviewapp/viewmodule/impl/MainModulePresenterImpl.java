package com.felink.webviewapp.viewmodule.impl;

import android.text.TextUtils;

import com.felink.webviewapp.base.BasePresenterImpl;
import com.felink.webviewapp.bean.MainModuleBean;
import com.felink.webviewapp.viewmodule.inter.IMainModuleView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

/**
 * Created Administrator
 * on 2017/1/11
 * deprecated:
 */

public class MainModulePresenterImpl extends BasePresenterImpl<IMainModuleView>{
    public MainModulePresenterImpl(IMainModuleView view,String url) {
        super(view);
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(this);
    }

    @Override
    public void onResponse(String response) {
        super.onResponse(response);
        if (!TextUtils.isEmpty(response)){
            List<MainModuleBean> mainModuleBeanList = jsonToArrayList(response,MainModuleBean.class);
            if (mainModuleBeanList != null && mainModuleBeanList.size() != 0){
                mViewRef.get().initMainModuleView(mainModuleBeanList);
            }
        }
    }
}
