package com.felink.webviewapp.aty;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

/**
 * Created by Administrator on 2017/1/18.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        try {
            OkHttpUtils
                    .getInstance()
                    .setCertificates(getAssets().open("vogerp.cer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
