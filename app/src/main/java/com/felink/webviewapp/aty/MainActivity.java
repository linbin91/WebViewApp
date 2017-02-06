package com.felink.webviewapp.aty;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.felink.webviewapp.R;
import com.felink.webviewapp.adpter.MenuAdapter;
import com.felink.webviewapp.base.BaseActivity;
import com.felink.webviewapp.base.BaseFragment;
import com.felink.webviewapp.bean.LoginBean;
import com.felink.webviewapp.bean.MainModuleBean;
import com.felink.webviewapp.data.UrlData;
import com.felink.webviewapp.fragment.MineFragment;
import com.felink.webviewapp.fragment.WebFragment;
import com.felink.webviewapp.utils.UrlParse;
import com.felink.webviewapp.viewmodule.impl.MainModulePresenterImpl;
import com.felink.webviewapp.viewmodule.impl.MenuItemPresenterImpl;
import com.felink.webviewapp.viewmodule.inter.IMainModuleView;
import com.felink.webviewapp.viewmodule.inter.IMenuView;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends BaseActivity implements IMainModuleView,IMenuView{

    private ListView listView;
    private RadioGroup tabs;
    private DrawerLayout mDrawerLayout;
    private ActionBar actionBar;
    private ActionBarDrawerToggle mDrawerToggle;
    private Map<Integer,BaseFragment> fragmentMap;
    private Map<Integer,String> titleMap;
    private BaseFragment mCurrentFragment;
    private List<MainModuleBean> moduleBeans;
    private long mExitTime;
    private Socket mSocket;
    private String userId;

    {
        try {
            mSocket = IO.socket("https://www.vogerp.com:8090");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        MainModulePresenterImpl presenter = new MainModulePresenterImpl(this, UrlData.GET_TAB_URL);
        EventBus.getDefault().register(this);
        MenuItemPresenterImpl presenter1 = new MenuItemPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_layout;
    }

    protected void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.drawer_open,R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        tabs = (RadioGroup) findViewById(R.id.tabs);
        listView = (ListView) findViewById(R.id.left_drawer);

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
                radioButton.setPadding(0,0,0,0);
                radioButton.setButtonDrawable(android.R.color.transparent);
                RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                radioButton.setGravity(Gravity.CENTER_HORIZONTAL);
                radioButton.setText(bean.name);
                Resources resource = (Resources) getBaseContext().getResources();
                ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.tab_text_select);
                if (csl != null) {
                    radioButton.setTextColor(csl);
                }
                tabs.addView(radioButton, lp);
            }
        }
        if (moduleBeans.size() > 0) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setPadding(0,0,0,0);
            radioButton.setId(R.id.mine_tab);
            radioButton.setButtonDrawable(android.R.color.transparent);
            radioButton.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.tab_icon_me), null, null);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            radioButton.setText(getResources().getString(R.string.mine));
            radioButton.setGravity(Gravity.CENTER);
            Resources resource = (Resources) getBaseContext().getResources();
            ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.tab_text_select);
            if (csl != null) {
                radioButton.setTextColor(csl);
            }
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
        urlParse.putValue("_appclient","android");

        return urlParse.toString();
    }

    protected void setListener() {

        tabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.main_tab:
                        setToolBarTitle(titleMap.get(R.id.main_tab));
                        showFragment(fragmentMap.get(R.id.main_tab));
                        break;
                    case R.id.order_tab:
                        setToolBarTitle(titleMap.get(R.id.order_tab));
                        showFragment(fragmentMap.get(R.id.order_tab));
                        break;
                    case R.id.product_tab:
                        setToolBarTitle(titleMap.get(R.id.product_tab));
                        showFragment(fragmentMap.get(R.id.product_tab));
                        break;
                    case R.id.mine_tab:
                        setToolBarTitle(getResources().getString(R.string.mine));
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

    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void getMenuItems(LoginBean loginBean) {
        userId = loginBean.userId;
        MenuItemPresenterImpl presenter = new MenuItemPresenterImpl(this);

        mSocket.on("connect", connect);
        mSocket.on("loginSuccess", loginSuccess);
        mSocket.on("notifyFrom", notifyFrom);
        mSocket.connect();

    }



    private Emitter.Listener connect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mSocket.emit("register",getMsg());
        }
    };

    private Emitter.Listener loginSuccess = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mSocket.emit("notifihistory",getMsg());
        }
    };

    private Emitter.Listener notifyFrom = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray data = (JSONArray) args[0];
                    // add the message to view
                    if (data != null && !TextUtils.isEmpty(data.toString())){
                        Toast.makeText(MainActivity.this,data.toString(),Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    };

    private JSONObject getMsg(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("partyId",userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void showMenuList(final List<com.felink.webviewapp.bean.MenuItem> items) {
        MenuAdapter adapter = new MenuAdapter(this,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                StringBuilder sb = new StringBuilder(UrlData.BASE_URL);
                sb.append(items.get(position).a_attr.href);
                intent.putExtra("url",sb.toString());
                intent.putExtra("title",items.get(position).text);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawers();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {//
                // 如果两次按键时间间隔大于2000毫秒，则不退出
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();// 更新mExitTime
            } else {
                System.exit(0);// 否则退出程序
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

}
