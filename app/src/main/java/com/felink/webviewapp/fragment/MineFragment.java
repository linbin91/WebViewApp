package com.felink.webviewapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.felink.webviewapp.R;
import com.felink.webviewapp.adpter.MineAdapter;
import com.felink.webviewapp.aty.LoginActivity;
import com.felink.webviewapp.aty.WebViewActivity;
import com.felink.webviewapp.base.BaseFragment;
import com.felink.webviewapp.bean.LoginBean;
import com.felink.webviewapp.bean.MainModuleBean;
import com.felink.webviewapp.customeview.CircleImageView;
import com.felink.webviewapp.customeview.DividerItemDecoration;
import com.felink.webviewapp.customeview.RecyclerItemClickListener;
import com.felink.webviewapp.data.UrlData;
import com.felink.webviewapp.utils.UrlParse;
import com.felink.webviewapp.viewmodule.impl.MineItemPresenterImpl;
import com.felink.webviewapp.viewmodule.inter.IMineView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created Administrator
 * on 2017/1/15
 * deprecated:
 */

public class MineFragment extends BaseFragment implements IMineView {
    public static MineFragment mineFragmentInstance;
    @BindView(R.id.user_icon)
    CircleImageView userIcon;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mView = View.inflate(mContext, R.layout.mine_fragment_layout, null);
        MineItemPresenterImpl presenter = new MineItemPresenterImpl(this);
    }

    public static MineFragment getInstance() {
        if (mineFragmentInstance == null) {
            mineFragmentInstance = new MineFragment();
        }

        return mineFragmentInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.user_icon)
    public void login() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void showUserLoginView(LoginBean bean) {
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.userName)) {
                username.setText(bean.userName);
            }
            if (!TextUtils.isEmpty(bean.userIcon)) {
                Glide.with(this)
                        .load(bean.userIcon)
                        .into(userIcon);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initMineItem(final List<MainModuleBean> datas) {
        if (datas != null && datas.size() != 0){
            MineAdapter adapter = new MineAdapter(getContext(),datas);
            LinearLayoutManager layoutManager =  new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerview.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.HORIZONTAL,1,R.color.black_50));
            recyclerview.setLayoutManager(layoutManager);
            recyclerview.setAdapter(adapter);
            recyclerview.addOnItemTouchListener(new RecyclerItemClickListener(recyclerview) {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    String url = generateWebUrl(datas.get(position));
                    intent.putExtra("url",url);
                    intent.putExtra("title",datas.get(position).name);
                    startActivity(intent);
                }
            });
        }
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

    private String generateWebUrl(MainModuleBean bean) {
        UrlParse urlParse = new UrlParse(UrlData.MODULE_WEB_URL);
        urlParse.putValue("_nodename",bean._nodename);
        urlParse.putValue("_chunkname",bean._chunkname);
        urlParse.putValue("_page",bean._page);
        urlParse.putValue("_framename",bean._framename);
        urlParse.putValue("_appclient","android");
        return urlParse.toString();
    }
}
