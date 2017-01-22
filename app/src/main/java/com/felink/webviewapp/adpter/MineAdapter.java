package com.felink.webviewapp.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.felink.webviewapp.R;
import com.felink.webviewapp.bean.MainModuleBean;
import com.felink.webviewapp.holder.MineItemHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/1/22.
 */

public class MineAdapter extends RecyclerView.Adapter<MineItemHolder> {
    private Context context;
    private List<MainModuleBean> datas;

    public MineAdapter(Context context, List<MainModuleBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public MineItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mine_item, parent, false);
        MineItemHolder mineItemHolder = new MineItemHolder(view);
        return mineItemHolder;
    }

    @Override
    public void onBindViewHolder(MineItemHolder holder, int position) {
        holder.tv.setText(datas.get(position).name);
        if (!TextUtils.isEmpty(datas.get(position).icon)){
            Glide.with(context)
                    .load(datas.get(position).icon)
                    .into(holder.iv);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
