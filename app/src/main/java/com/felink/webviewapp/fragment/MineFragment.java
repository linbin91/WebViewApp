package com.felink.webviewapp.fragment;

import android.os.Bundle;
import android.view.View;

import com.felink.webviewapp.R;
import com.felink.webviewapp.base.BaseFragment;

/**
 * Created Administrator
 * on 2017/1/15
 * deprecated:
 */

public class MineFragment extends BaseFragment {
    public static MineFragment mineFragmentInstance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = View.inflate(mContext, R.layout.mine_fragment_layout, null);
    }

    public static MineFragment getInstance(){
        if (mineFragmentInstance == null){
            mineFragmentInstance = new MineFragment();
        }

        return mineFragmentInstance;
    }

}
