package com.felink.webviewapp.viewmodule.inter;

import com.felink.webviewapp.base.BaseView;
import com.felink.webviewapp.bean.MainModuleBean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public interface IMainModuleView extends BaseView{
    void initMainModuleView(List<MainModuleBean> moduleBeans);
}
