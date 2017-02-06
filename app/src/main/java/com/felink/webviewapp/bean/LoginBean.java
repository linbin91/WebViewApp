package com.felink.webviewapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/1/20.
 */

public class LoginBean {
    public String orgId;
    public String userId;
    public String orgName;
    public String city;
    public String locale;
    public String userName;
    public String userIcon;
    @SerializedName("verifyCode.error")
    public String error;
}
