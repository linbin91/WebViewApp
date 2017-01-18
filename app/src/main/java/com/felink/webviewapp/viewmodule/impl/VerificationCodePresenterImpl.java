package com.felink.webviewapp.viewmodule.impl;

import android.text.TextUtils;

import com.felink.webviewapp.base.BasePresenterImpl;
import com.felink.webviewapp.bean.VerificationCodeBean;
import com.felink.webviewapp.data.UrlData;
import com.felink.webviewapp.viewmodule.inter.IVerificationCodeView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/18.
 */

public class VerificationCodePresenterImpl extends BasePresenterImpl<IVerificationCodeView>{
    public VerificationCodePresenterImpl(IVerificationCodeView view) {
        super(view);
        OkHttpUtils.get()
                .url(UrlData.GET_VERIFICATION_CODE)
                .build()
                .execute(this);
    }

    @Override
    public void onResponse(String response) {
        super.onResponse(response);
        if (!TextUtils.isEmpty(response)){
            Gson gson = new Gson();
            VerificationCodeBean bean = gson.fromJson(response,VerificationCodeBean.class);
            if (bean != null){
                mViewRef.get().showVerificationCode(bean);
            }
        }
    }
}
