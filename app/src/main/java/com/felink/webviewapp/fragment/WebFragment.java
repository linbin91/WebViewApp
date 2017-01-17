package com.felink.webviewapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.felink.webviewapp.R;
import com.felink.webviewapp.base.BaseFragment;

/**
 * Created Administrator
 * on 2017/1/14
 * deprecated:
 */

public class WebFragment extends BaseFragment {

    private String url;
    private WebView mWebView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = View.inflate(mContext, R.layout.web_fragment_layout, null);
        initData();
        initView();
    }

    private void initData() {
        url = (String) getArguments().get("url");
    }

    private void initView() {
        mWebView = (WebView) mView.findViewById(R.id.webview);
        mWebView.loadUrl(url);
        WebSettings wSet = mWebView.getSettings();
        wSet.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static WebFragment newWebFragment(Bundle args) {

        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {

        mContext = null;
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
