package com.life.mm.friends.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.life.mm.R;
import com.life.mm.framework.user.ContactInfo;

import java.util.List;

public class PhoneContactAdapter extends BaseAdapter {

    private final List<ContactInfo> infoList;
    private final Context context;

    public PhoneContactAdapter(List<ContactInfo> infoList, Context context) {
        this.infoList = infoList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder = null;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_phone_contact, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }
        ContactInfo info = infoList.get(position);

        // 进行分组, 比较上一个拼音的首字母和自己是否一致, 如果不一致, 就显示tv_index
        String currentLetter = info.getNamePY().charAt(0) + "";
        String indexStr = null;
        if (position == 0) {// 1. 如果是第一位
            indexStr = currentLetter;
        } else {
            // 获取上一个拼音
            String preLetter = infoList.get(position - 1).getNamePY().charAt(0) + "";
            if (!TextUtils.equals(currentLetter, preLetter)) {
                // 2. 当跟上一个不同时, 赋值, 显示
                indexStr = currentLetter;
            }
        }

        holder.tv_index.setVisibility(indexStr == null ? View.GONE : View.VISIBLE);
        holder.tv_index.setText(indexStr);

        holder.tv_name.setText(info.getName());
        return view;
    }

    private class ViewHolder {
        private TextView tv_index;
        private TextView tv_name;

        ViewHolder(View view) {
            tv_index = (TextView) view.findViewById(R.id.tv_index);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
        }
    }

}
