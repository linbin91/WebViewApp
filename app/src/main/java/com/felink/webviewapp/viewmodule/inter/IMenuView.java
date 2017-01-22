package com.felink.webviewapp.viewmodule.inter;

import com.felink.webviewapp.base.BaseView;
import com.felink.webviewapp.bean.MenuItem;

import java.util.List;

/**
 * Created by Administrator on 2017/1/20.
 */

public interface IMenuView extends BaseView {
    public void showMenuList(List<MenuItem> items);
}
