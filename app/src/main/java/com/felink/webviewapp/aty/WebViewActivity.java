package com.felink.webviewapp.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.felink.webviewapp.R;
import com.felink.webviewapp.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/1/22.
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadWebView();
    }

    private void loadWebView() {
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(url)){
            webview.loadUrl(url);
        }
        if (!TextUtils.isEmpty(title)){
            setToolBarTitle(title);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    private void initView() {
        webview = (WebView)findViewById(R.id.webview);
        WebSettings wSet = webview.getSettings();
        wSet.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
