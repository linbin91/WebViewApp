package com.felink.webviewapp.aty;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.felink.webviewapp.R;
import com.felink.webviewapp.bean.VerificationCodeBean;
import com.felink.webviewapp.viewmodule.impl.VerificationCodePresenterImpl;
import com.felink.webviewapp.viewmodule.inter.IVerificationCodeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/18.
 */

public class LoginActivity extends AppCompatActivity implements IVerificationCodeView {
    @BindView(R.id.et_username)
    AppCompatEditText etUsername;
    @BindView(R.id.et_password)
    AppCompatEditText etPassword;
    @BindView(R.id.et_verifycodequestionlabel)
    TextView etVerifycodequestionlabel;
    @BindView(R.id.et_verifycodequestion)
    TextView etVerifycodequestion;
    @BindView(R.id.et_verifycode)
    AppCompatEditText etVerifycode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private ActionBar actionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //取得ActionBar
        actionBar = getSupportActionBar();
//设置不显示标题
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.login);
        //设置返回键可用
        actionBar.setDisplayShowHomeEnabled(true);
        //设置显示logo
        actionBar.setDisplayUseLogoEnabled(false);
////设置actionbar背景
        Drawable background =getResources().getDrawable(R.color.toolbar_color);
        actionBar.setBackgroundDrawable(background);
        actionBar.setDisplayHomeAsUpEnabled(true);

        VerificationCodePresenterImpl verificationCodePresenter = new VerificationCodePresenterImpl(this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_login)
    public void login(){}

    @OnClick(R.id.btn_register)
    public void register(){}

    @Override
    public void showVerificationCode(VerificationCodeBean bean) {
        etVerifycodequestion.setText(bean.value);
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
