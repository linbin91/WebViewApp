package com.felink.webviewapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.felink.webviewapp.R;
import com.felink.webviewapp.aty.LoginActivity;
import com.felink.webviewapp.base.BaseFragment;
import com.felink.webviewapp.customeview.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Administrator
 * on 2017/1/15
 * deprecated:
 */

public class MineFragment extends BaseFragment {
    public static MineFragment mineFragmentInstance;
    @BindView(R.id.user_icon)
    CircleImageView userIcon;
    @BindView(R.id.my_message)
    TextView myMessage;
    @BindView(R.id.my_team)
    TextView myTeam;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = View.inflate(mContext, R.layout.mine_fragment_layout, null);
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
    public void login(){
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

}
