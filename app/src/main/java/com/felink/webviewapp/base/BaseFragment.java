package com.felink.webviewapp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 *
 * Fragment基类，子类必须在OnCreate方法中完成对mView的赋值
 *
 * @author linbin
 * @date 20160402
 */
public  class BaseFragment extends Fragment implements View.OnClickListener{

    private String TAG = "Fragment Lifecycle";

    /**
     * Fragment的View，子类必须在OnCreate方法中完成对mView的赋值
     */
    protected View mView;
    protected Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (mView == null) {
            onCreate(savedInstanceState);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        try {
            if (mView != null && mView.getParent() instanceof ViewGroup) {
                ((ViewGroup) mView.getParent()).removeView(mView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mView instanceof ViewGroup) {
            ((ViewGroup) mView).removeAllViewsInLayout();
        }
        mView = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void onSelected() {

    }

    public void scrollToTop() {

    }


    @Override
    public void onClick(View v) {

    }
}
