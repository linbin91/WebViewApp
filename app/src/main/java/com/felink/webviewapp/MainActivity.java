package com.felink.webviewapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.felink.webviewapp.bean.MainModuleBean;
import com.felink.webviewapp.data.UrlData;
import com.felink.webviewapp.utils.UrlParse;
import com.felink.webviewapp.viewmodule.impl.MainModulePresenterImpl;
import com.felink.webviewapp.viewmodule.inter.IMainModuleView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainModuleView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainModulePresenterImpl presenter = new MainModulePresenterImpl(this, UrlData.GET_TAB_URL);
    }

    @Override
    public void initMainModuleView(List<MainModuleBean> moduleBeans) {
        StringBuilder sb = new StringBuilder();
        for (MainModuleBean bean : moduleBeans){
            sb.append(bean.name);
        }
        UrlParse urlParse = new UrlParse("https://www.vogerp.com/uimaster/webflow.do?https://www.vogerp.com/uimaster/webflow.do?");
        urlParse.putValue("_nodename",moduleBeans.get(0)._nodename);
        urlParse.putValue("_chunkname",moduleBeans.get(0)._chunkname);
        urlParse.putValue("_page",moduleBeans.get(0)._page);
        urlParse.putValue("_framename",moduleBeans.get(0)._framename);
        ((WebView)findViewById(R.id.webview)).loadUrl(urlParse.toString());
        ((TextView)findViewById(R.id.tv)).setText(sb.toString());

        WebSettings wSet = ((WebView)findViewById(R.id.webview)).getSettings();
        wSet.setJavaScriptEnabled(true);
        ((WebView)findViewById(R.id.webview)).setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void toast(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
