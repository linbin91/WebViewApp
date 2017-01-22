package com.felink.webviewapp.viewmodule.impl;

import android.text.TextUtils;

import com.felink.webviewapp.base.BasePresenterImpl;
import com.felink.webviewapp.bean.MenuItem;
import com.felink.webviewapp.data.UrlData;
import com.felink.webviewapp.viewmodule.inter.IMenuView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/1/20.
 */

public class MenuItemPresenterImpl extends BasePresenterImpl<IMenuView> {

    public MenuItemPresenterImpl(IMenuView view) {
        super(view);
        OkHttpUtils.get()
                .url(UrlData.MENU_ITEMS_URL)
                .build()
                .execute(this);
    }

    @Override
    public void onResponse(String response) {
        super.onResponse(response);

        if (!TextUtils.isEmpty(response)){
            Gson gson = new Gson();
            List<MenuItem> menuItemLists = jsonToArrayList(response,MenuItem.class);
            if (menuItemLists != null && menuItemLists.size() != 0){
                mViewRef.get().showMenuList(menuItemLists);
            }
        }
    }
}
