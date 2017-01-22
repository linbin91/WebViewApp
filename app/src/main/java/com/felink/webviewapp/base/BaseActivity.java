package com.felink.webviewapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.felink.webviewapp.R;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by linbin_dian91 on 2016/3/17.
 */
public abstract class BaseActivity<T extends  BasePresenter> extends AppCompatActivity implements  BaseView {

    private Toolbar toolbar;
    protected  T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initToolBar();
    }

    protected void initToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //显示自动的返回箭头
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
    }

    protected abstract int getLayoutId();

    protected  void setToolBarTitle(int title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    protected void setToolBarTitle(String title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    @Subscribe()

    @Override
    public void toast(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

     @Override
     protected void onResume() {
      super.onResume();
         if (mPresenter != null) {
             mPresenter.onResume();
         }
     }


     @Override
     protected void onDestroy() {
         super.onDestroy();
         if (mPresenter != null) {
             mPresenter.onDestroy();
         }
         EventBus.getDefault().unregister(this);
     }
}
