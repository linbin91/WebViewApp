package com.felink.webviewapp.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.felink.webviewapp.R;

/**
 * Created by Administrator on 2017/1/22.
 */

public class MineItemHolder extends RecyclerView.ViewHolder {
    public TextView tv;
    public ImageView iv;
    public MineItemHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.tv);
        iv = (ImageView) itemView.findViewById(R.id.iv);
    }
}
