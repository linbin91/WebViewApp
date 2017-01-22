package com.felink.webviewapp.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.felink.webviewapp.R;
import com.felink.webviewapp.bean.MenuItem;

import java.util.List;

/**
 * Created by Administrator on 2017/1/22.
 */

public class MenuAdapter extends BaseAdapter {
    private Context mCxt;
    private List<MenuItem> menuItems;
    private LayoutInflater mInflater;
    public MenuAdapter(Context context, List<MenuItem> menuItems){
        this.mCxt = context;
        this.menuItems = menuItems;
        mInflater = LayoutInflater.from(mCxt);
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.drawer_list_item, parent,false);
            holder = new ViewHolder();
            holder.textView = (TextView)convertView.findViewById(R.id.tv_menu);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.textView.setText(menuItems.get(position).text);
        return convertView;
    }

    public static class ViewHolder{
        public TextView textView;
    }
}
