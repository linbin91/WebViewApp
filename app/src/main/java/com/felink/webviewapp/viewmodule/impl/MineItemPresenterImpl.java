package com.felink.webviewapp.viewmodule.impl;

import android.text.TextUtils;

import com.felink.webviewapp.base.BasePresenterImpl;
import com.felink.webviewapp.bean.MainModuleBean;
import com.felink.webviewapp.viewmodule.inter.IMineView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import static com.felink.webviewapp.data.UrlData.MINE_ITEMS_URL;

/**
 * Created Administrator
 * on 2017/1/11
 * deprecated:
 */

public class MineItemPresenterImpl extends BasePresenterImpl<IMineView>{
    public MineItemPresenterImpl(IMineView view) {
        super(view);
        OkHttpUtils.get()
                .url(MINE_ITEMS_URL)
                .build()
                .execute(this);
    }

    @Override
    public void onResponse(String response) {
        super.onResponse(response);
        if (!TextUtils.isEmpty(response)){
            List<MainModuleBean> mainModuleBeanList = jsonToArrayList(response,MainModuleBean.class);
            if (mainModuleBeanList != null && mainModuleBeanList.size() != 0){
                mViewRef.get().initMineItem(mainModuleBeanList);
            }
        }
    }
}
