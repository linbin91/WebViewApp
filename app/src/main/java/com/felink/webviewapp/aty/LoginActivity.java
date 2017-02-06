package com.felink.webviewapp.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.felink.webviewapp.R;
import com.felink.webviewapp.base.BaseActivity;
import com.felink.webviewapp.bean.LoginBean;
import com.felink.webviewapp.bean.VerificationCodeBean;
import com.felink.webviewapp.data.UrlData;
import com.felink.webviewapp.utils.UrlParse;
import com.felink.webviewapp.viewmodule.impl.LoginPresenterImpl;
import com.felink.webviewapp.viewmodule.impl.VerificationCodePresenterImpl;
import com.felink.webviewapp.viewmodule.inter.ILoginView;
import com.felink.webviewapp.viewmodule.inter.IVerificationCodeView;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/1/18.
 */

public class LoginActivity extends BaseActivity implements IVerificationCodeView,ILoginView {
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
        setToolBarTitle(R.string.login);
        VerificationCodePresenterImpl verificationCodePresenter = new VerificationCodePresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_login)
    public void login(){
        if (TextUtils.isEmpty(etUsername.getText().toString())){
            Toast.makeText(this,R.string.please_edit_name,Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())){
            Toast.makeText(this,R.string.please_edit_password,Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(etVerifycode.getText().toString())){
            Toast.makeText(this,R.string.please_edit_verificationcode,Toast.LENGTH_SHORT).show();
            return;
        }
        UrlParse urlParse = new UrlParse(UrlData.LOGIN_URL);
        urlParse.putValue("username",etUsername.getText().toString());
        urlParse.putValue("pwd",etPassword.getText().toString());
        urlParse.putValue("verifyCode",etVerifycode.getText().toString());
        LoginPresenterImpl loginPresenter = new LoginPresenterImpl(this,urlParse.toString());
    }

    @OnClick(R.id.btn_register)
    public void register(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

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

    @Override
    public void loginResult(LoginBean loginBean) {
        if (!TextUtils.isEmpty(loginBean.error)){
            Toast.makeText(this,R.string.login_error,Toast.LENGTH_SHORT).show();
        }else {
            EventBus.getDefault().post(loginBean);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
