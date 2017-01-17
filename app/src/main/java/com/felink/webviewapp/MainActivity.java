package com.felink.webviewapp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.felink.webviewapp.base.BaseFragment;
import com.felink.webviewapp.bean.MainModuleBean;
import com.felink.webviewapp.data.UrlData;
import com.felink.webviewapp.fragment.MineFragment;
import com.felink.webviewapp.fragment.WebFragment;
import com.felink.webviewapp.utils.UrlParse;
import com.felink.webviewapp.viewmodule.impl.MainModulePresenterImpl;
import com.felink.webviewapp.viewmodule.inter.IMainModuleView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IMainModuleView{

    private ListView listView;
    private RadioGroup tabs;
    private DrawerLayout mDrawerLayout;
    private String [] datas = {"基本信息","产品管理"};
    private ActionBar actionBar;
    private ActionBarDrawerToggle mDrawerToggle;
    private Map<Integer,BaseFragment> fragmentMap;
    private Map<Integer,String> titleMap;
    private BaseFragment mCurrentFragment;
    private List<MainModuleBean> moduleBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        MainModulePresenterImpl presenter = new MainModulePresenterImpl(this, UrlData.GET_TAB_URL);

    }


    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
//        mDrawerLayout.openDrawer(Gravity.RIGHT);
        tabs = (RadioGroup) findViewById(R.id.tabs);
        listView = (ListView) findViewById(R.id.left_drawer);
        listView.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,datas));

        //取得ActionBar
        actionBar = getSupportActionBar();
//设置不显示标题
        actionBar.setDisplayShowTitleEnabled(true);
        //设置返回键可用
        actionBar.setDisplayShowHomeEnabled(true);
        //设置显示logo
        actionBar.setDisplayUseLogoEnabled(false);
////设置actionbar背景
//        Drawable background =getResources().getDrawable(R.drawable.top_bar_background);
//        actionBar.setBackgroundDrawable(background);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.drawer_open,R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item) || mDrawerToggle.onOptionsItemSelected(item);
    }

    @Override
    public void initMainModuleView(List<MainModuleBean> moduleBeans) {
        this.moduleBeans = moduleBeans;
        StringBuilder sb = new StringBuilder();
        fragmentMap = new HashMap<>();
        titleMap = new HashMap<>();
        for (MainModuleBean bean : moduleBeans) {
            if (!TextUtils.isEmpty(bean.name)) {
                Bundle bundle = new Bundle();
                String url = generateWebUrl(bean);
                bundle.putString("url",url);

                RadioButton radioButton = new RadioButton(this);
                radioButton.setButtonDrawable(android.R.color.transparent);
                if (bean.name.contains("主页")) {
                    fragmentMap.put(R.id.main_tab,WebFragment.newWebFragment(bundle));
                    titleMap.put(R.id.main_tab,bean._nodename);
                    radioButton.setId(R.id.main_tab);
                    radioButton.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.tab_icon_main), null, null);
                } else if (bean.name.contains("订单")) {
                    fragmentMap.put(R.id.product_tab,WebFragment.newWebFragment(bundle));
                    titleMap.put(R.id.product_tab,bean._nodename);
                    radioButton.setId(R.id.product_tab);
                    radioButton.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.tab_icon_order), null, null);
                } else if (bean.name.contains("产品")) {
                    titleMap.put(R.id.order_tab,bean._nodename);
                    fragmentMap.put(R.id.order_tab,WebFragment.newWebFragment(bundle));
                    radioButton.setId(R.id.order_tab);
                    radioButton.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.tab_icon_func), null, null);
                }
                radioButton.setButtonDrawable(android.R.color.transparent);
                RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                radioButton.setGravity(Gravity.CENTER);
                radioButton.setText(bean.name);
                tabs.addView(radioButton, lp);
            }
        }
        if (moduleBeans.size() > 0) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(R.id.mine_tab);
            radioButton.setButtonDrawable(android.R.color.transparent);
            radioButton.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.tab_icon_me), null, null);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            radioButton.setText(getResources().getString(R.string.mine));
            radioButton.setGravity(Gravity.CENTER);
            tabs.addView(radioButton, lp);
        }
        setListener();
    }

    private String generateWebUrl(MainModuleBean bean) {
        UrlParse urlParse = new UrlParse(UrlData.MODULE_WEB_URL);
        urlParse.putValue("_nodename",bean._nodename);
        urlParse.putValue("_chunkname",bean._chunkname);
        urlParse.putValue("_page",bean._page);
        urlParse.putValue("_framename",bean._framename);
        return urlParse.toString();
    }

    protected void setListener() {

        tabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.main_tab:
                        actionBar.setTitle(titleMap.get(R.id.main_tab));
                        showFragment(fragmentMap.get(R.id.main_tab));
                        break;
                    case R.id.order_tab:
                        actionBar.setTitle(titleMap.get(R.id.order_tab));
                        showFragment(fragmentMap.get(R.id.order_tab));
                        break;
                    case R.id.product_tab:
                        actionBar.setTitle(titleMap.get(R.id.product_tab));
                        showFragment(fragmentMap.get(R.id.product_tab));
                        break;
                    case R.id.mine_tab:
                        actionBar.setTitle(getResources().getString(R.string.mine));
                        showFragment(MineFragment.getInstance());
                        break;
                    default:
                        break;
                }
            }
        });
        tabs.check(R.id.main_tab);
    }

    /**
     * 显示4个页签中的一个
     *
     * @param fragment
     */
    public void showFragment(BaseFragment fragment) {
        if (fragment == mCurrentFragment){
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.tab_content, fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
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
