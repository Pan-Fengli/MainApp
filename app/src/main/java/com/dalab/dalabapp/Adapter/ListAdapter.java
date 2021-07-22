package com.dalab.dalabapp.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dalab.dalabapp.R;

public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public ListAdapter(Context context)
    {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    static class ViewHolder
    {
        public ImageView imageView;
        public TextView title, content;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //
        ViewHolder holder = null;
        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imgItem);
            holder.title = convertView.findViewById(R.id.title);
            holder.content = convertView.findViewById(R.id.content);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position == 0)
        {
            holder.title.setText("止血训练");
            holder.content.setText("包含左上肢止血、右上肢止血、左下肢止血、右下肢止血");
            Glide.with(mContext).load("https://img0.baidu.com/it/u=795069135,1579187838&fm=26&fmt=auto&gp=0.jpg").into(holder.imageView);
        }
        else if(position == 1)
        {
            holder.title.setText("包扎训练");
            holder.content.setText("包含上肢包扎、下肢包扎");
            Glide.with(mContext).load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_match%2F0%2F11573457968%2F0.jpg&refer=http%3A%2F%2Finews.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1629168124&t=a819e526d6c3e82b92ece64cc6bc5887").into(holder.imageView);
        }
        return convertView;
    }
}
