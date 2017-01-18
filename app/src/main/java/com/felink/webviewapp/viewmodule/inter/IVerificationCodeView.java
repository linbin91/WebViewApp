package com.felink.webviewapp.viewmodule.inter;

import com.felink.webviewapp.base.BaseView;
import com.felink.webviewapp.bean.VerificationCodeBean;

/**
 * Created by Administrator on 2017/1/18.
 */

public interface IVerificationCodeView extends BaseView {
    public void showVerificationCode(VerificationCodeBean bean);
}
