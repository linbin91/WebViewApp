package com.felink.webviewapp.data;

/**
 * Created Administrator
 * on 2017/1/11
 * deprecated:
 */

public class UrlData {

    public static final String BASE_URL = "https://www.vogerp.com/uimaster/";
    //获取四个tab栏的url
    public static final String GET_TAB_URL = "https://www.vogerp.com/uimaster/ajaxservice?_ajaxUserEvent=webservice&_serviceName=org.shaolin.vogerp.commonmodel.page.AjaxService.mobileBottomFunctionList&_appclient=andriod";
    //点击每个tab访问web url
    public static final String MODULE_WEB_URL = "https://www.vogerp.com/uimaster/webflow.do?https://www.vogerp.com/uimaster/webflow.do?";
    //获取验证码
    public static final String GET_VERIFICATION_CODE = "https://www.vogerp.com/uimaster/ajaxservice?_ajaxUserEvent=webservice&_serviceName=org.shaolin.bmdp.adminconsole.page.AjaxService.userPreLogin&_appclient=andriod";
    //注册用户url
    public static final String REGISTER_URL = "https://www.vogerp.com/uimaster/webflow.do?_timestamp=97&_chunkname=org.shaolin.bmdp.adminconsole.diagram.LoginAuthentication&_nodename=Registration&_appclient=andriod";
    //登录url
    public static final String LOGIN_URL = "https://www.vogerp.com/uimaster/ajaxservice?_ajaxUserEvent=webservice&_serviceName=org.shaolin.bmdp.adminconsole.page.AjaxService.userLogin&username=&pwd=&verifyCode=a&_appclient=andriod";
    //获取menuitems
    public static final String MENU_ITEMS_URL = "https://www.vogerp.com/uimaster/ajaxservice?_ajaxUserEvent=webservice&_serviceName=org.shaolin.vogerp.commonmodel.page.AjaxService.functionList&_appclient=andriod";
    //获取我的items
    public static final String MINE_ITEMS_URL = "https://www.vogerp.com/uimaster/ajaxservice?_ajaxUserEvent=webservice&_serviceName=org.shaolin.vogerp.commonmodel.page.AjaxService.userPageItems&_appclient=andriod";
}
