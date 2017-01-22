package com.felink.webviewapp.viewmodule.inter;

import com.felink.webviewapp.base.BaseView;
import com.felink.webviewapp.bean.MainModuleBean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/20.
 */

public interface IMineView extends BaseView {
    public void initMineItem(List<MainModuleBean> datas);
}
