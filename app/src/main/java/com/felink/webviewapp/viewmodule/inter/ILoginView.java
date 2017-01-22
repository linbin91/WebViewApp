package com.felink.webviewapp.viewmodule.inter;

import com.felink.webviewapp.base.BaseView;
import com.felink.webviewapp.bean.LoginBean;

/**
 * Created by Administrator on 2017/1/20.
 */

public interface ILoginView extends BaseView {
    public void loginResult(LoginBean loginBean);
}
